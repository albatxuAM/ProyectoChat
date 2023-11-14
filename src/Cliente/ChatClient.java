package Cliente;

import Cliente.Vista.VChat;
import Common.Mangers.ConfigManager;
import Common.Message.ConnectionData;
import Common.Validar.Validaciones;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.List;

public class ChatClient {
    MessageReceiverThread receiverThread;
    private MulticastSocket multicastSocket;
    private InetAddress group;
    private Socket serverSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private JFrame frame;
    private VChat vChat;
    private String nickname;

    /**
     * Constructor del cliente al que se pasan los datos del servidor
     *
     * @param serverAddress IP del servidor al que conectarse
     * @param serverPort puerto del servidor al que conectarse
     */
    public ChatClient(String serverAddress, int serverPort) {
        try {
            //abrir ventana chat
            vChat = new VChat();
            frame = new JFrame("Chat Client");
            frame.setContentPane(vChat.getpPrincipal());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            //anadir icono a la ventana
            ImageIcon icon = new ImageIcon(ConfigManager.getInstance().getIconPath());
            frame.setIconImage(icon.getImage());

            //realizar conexion TCP contra el servidor
            serverSocket = new Socket(serverAddress, serverPort);
            out = new ObjectOutputStream(serverSocket.getOutputStream());
            in = new ObjectInputStream(serverSocket.getInputStream());
            nickname = null;

            // Comprobar nickname esta disponible
            boolean connAccepted = false;
            while (!connAccepted) {

                Object response = in.readObject();

                if (response instanceof ConnectionData) {
                    connAccepted = true;
                    ConnectionData connectionData = (ConnectionData) response;
                    List<String> connectedUsers = connectionData.getConnectedUsers();
                    vChat.updateConnectedUsersList(connectedUsers);
                } else if (response.equals("Enter your nickname: ")) {
                    nickname = JOptionPane.showInputDialog((String) response);
                    // Envía el apodo al servidor
                    out.writeObject(nickname);
                    out.flush();
                } else if (response.toString().contains("Nickname accepted")) {
                    vChat.setNickname(nickname);
                    JOptionPane.showMessageDialog(null, response.toString(), "Server response", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println(response);
                } else {
                    JOptionPane.showMessageDialog(null, response.toString(), "Error from Server", JOptionPane.ERROR_MESSAGE);
                    System.out.println(response);
                }
            }

            //unirse al grupo multicast
            group = InetAddress.getByName("239.0.0.1");
            multicastSocket = new MulticastSocket(8888);
            multicastSocket.joinGroup(group);

            //mandar a un thread toda la lectura
            receiverThread = new MessageReceiverThread(multicastSocket, vChat, group);
            receiverThread.start();

            // Agregar un ActionListener al botón enviarButton
            vChat.getEnviarButton().addActionListener(e -> {
                String message = vChat.getTfMsg().getText();
                sendMessage(message);
                vChat.getTfMsg().setText("");
            });

            // Agregar un KeyListener al campo de texto tfMsg
            vChat.getTfMsg().addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        String message = vChat.getTfMsg().getText();
                        sendMessage(message);
                        vChat.getTfMsg().setText("");
                    }
                }
            });

            // Agregar un ActionListener al botón LogOutButton
            vChat.getLogOutButton().addActionListener(e -> {
                closeClient();
            });

            // cerrar conexiones al cerrar la aplicación
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    closeClient();
                }
            });
        } catch (ConnectException cnEx) {
            if (ConfigManager.getInstance().getDebug())
                System.out.println("ConnectException " + cnEx.getMessage());
            Validaciones.mostrarError("Connection refused");
            closeClient();
        } catch (IOException | ClassNotFoundException e) {
            if (ConfigManager.getInstance().getDebug())
                System.out.println(e.getMessage());
            Validaciones.mostrarError(e.getMessage());
            closeClient();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChatClient(
                ConfigManager.getInstance().getServerIP(),
                ConfigManager.getInstance().getServerPort()));
    }

    /**
     * Cerrar la conexion con el servidor
     */
    private void closeClient() {
        System.err.println("close client");
        try {
            if (receiverThread != null)
                receiverThread.setReceive(false);
            if (serverSocket != null)
                serverSocket.close();
//            if (multicastSocket != null) {
//                multicastSocket.leaveGroup(group);
//                multicastSocket.close();
//            }

            if (frame != null)
                frame.dispose();

        } catch (IOException e) {
            if (ConfigManager.getInstance().getDebug())
                System.out.println(e.getMessage());
            Validaciones.mostrarError(e.getMessage());
        }
    }

    /**
     * mandar un mensaje al servidor mediante TCP
     *
     * @param message mensaje a enviar al servidor
     */
    private void sendMessage(String message) {
        if (message != null && !message.isEmpty()) {
            try {
                out.writeObject(message);
                out.flush();
            } catch (IOException e) {
                if (ConfigManager.getInstance().getDebug())
                    System.out.println(e.getMessage());
                Validaciones.mostrarError(e.getMessage());
            }
        }
    }
}
