package Cliente;

import Common.ConnectionData;
import Cliente.Vista.VChat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.Set;

public class ChatClient {
    private MulticastSocket multicastSocket;
    private InetAddress group;
    private Socket serverSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private VChat vChat;  // Referencia a la ventana VChat
    private String nickname;

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

            Object response = in.readObject();
            if (response.equals("Enter your nickname: ")) {
                nickname = JOptionPane.showInputDialog((String) response);
            }

            out.writeObject(nickname);
            out.flush();

            response = in.readObject();
            vChat.appendToChatArea((String) response);

            group = InetAddress.getByName("239.0.0.1");
            multicastSocket = new MulticastSocket(8888);
            multicastSocket.joinGroup(group);

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                multicastSocket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());

                vChat.appendToChatArea(message);

                System.out.println(message);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
                multicastSocket.leaveGroup(group);
                multicastSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
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
