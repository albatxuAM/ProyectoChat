package Cliente;

import Cliente.Vista.VChat;
import Common.ConnectionData;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.List;

public class ChatClient {
    private MulticastSocket multicastSocket;
    private InetAddress group;
    private Socket serverSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private VChat vChat;  // Referencia a la ventana VChat
    private String nickname;

    private boolean exit = false;

    public ChatClient(String serverAddress, int serverPort) {
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

            //   Object response = in.readObject();


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
                } else if (response.equals("Nickname accepted")) {
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

            MessageReceiverThread receiverThread = new MessageReceiverThread(multicastSocket, vChat);
            receiverThread.start();

            sendMessage("hola");
            while (!exit) {
                //leer del la consola y mandar
                //sendMessage("hola");
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
                multicastSocket.leaveGroup(group);
                multicastSocket.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void sendMessage(String message) {
        if (message != null && !message.isEmpty()) {
            try {
                out.writeObject(message);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String serverAddress = "127.0.0.1";  // Cambia a la dirección IP o nombre de host de tu servidor
        int serverPort = 12345;  // Cambia al puerto en el que escucha tu servidor

        SwingUtilities.invokeLater(() -> new ChatClient(serverAddress, serverPort));
    }
}
