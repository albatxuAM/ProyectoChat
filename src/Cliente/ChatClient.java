package Cliente;

import Cliente.Modelo.Excepciones.Validaciones;
import Cliente.Vista.VChat;
import Common.ConnectionData;

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
    private VChat vChat;  // Referencia a la ventana VChat
    private String nickname;

    private boolean exit = false;

    public ChatClient(String serverAddress, int serverPort) {
        //try (Socket serverSocket = new Socket(serverAddress, serverPort)) {
        try {
            serverSocket = new Socket(serverAddress, serverPort);
            out = new ObjectOutputStream(serverSocket.getOutputStream());
            in = new ObjectInputStream(serverSocket.getInputStream());
            nickname = null;

            vChat = new VChat();  // Crea una instancia de VChat
            // Establece VChat como el contenido principal del JFrame
            JFrame frame = new JFrame("Chat Client");
            frame.setContentPane(vChat.getpPrincipal());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

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
                    JOptionPane.showMessageDialog(null, response.toString(), "Server response", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println(response);
                } else {
                    JOptionPane.showMessageDialog(null, response.toString(), "Error from Server", JOptionPane.ERROR_MESSAGE);
                    System.out.println(response);
                }
            }

            group = InetAddress.getByName("239.0.0.1");
            multicastSocket = new MulticastSocket(8888);
            multicastSocket.joinGroup(group);

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

            // cerrar conexiones al cerrar la aplicación
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    closeClient();
                }
            });
        } catch (ConnectException cnEx) {
            Validaciones.mostrarError("Connection refused");
            closeClient();
        } catch (IOException | ClassNotFoundException e) {
            Validaciones.mostrarError(e.getMessage());
            closeClient();
        }
    }

    public static void main(String[] args) {
        String serverAddress = "127.0.0.1";
        int serverPort = 12345;

        SwingUtilities.invokeLater(() -> new ChatClient(serverAddress, serverPort));
    }

    private void closeClient() {
        System.err.println("close client");
        try {
            receiverThread.setReceive(false);
            if (serverSocket != null)
                serverSocket.close();
//            if (multicastSocket != null) {
//                multicastSocket.leaveGroup(group);
//                multicastSocket.close();
//            }
        } catch (IOException ex) {
            Validaciones.mostrarError(ex.getMessage());
        }
    }

    private void sendMessage(String message) {
        if (message != null && !message.isEmpty()) {
            try {
                out.writeObject(message);
                out.flush();
            } catch (IOException e) {
                Validaciones.mostrarError(e.getMessage());
            }
        }
    }
}
