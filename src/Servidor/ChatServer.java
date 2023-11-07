package Servidor;

import Common.ConnectionData;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static Map<String, Socket> connectedClients = new HashMap<>();
    private static MulticastSocket multicastSocket;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server listening on port 12345...");

            multicastSocket = new MulticastSocket(8888);
            InetAddress group = InetAddress.getByName("239.0.0.1");
            multicastSocket.joinGroup(group);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private String nickname;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

                out.writeObject("Enter your nickname: ");
                out.flush();
                nickname = (String) in.readObject();

                if (isNicknameAvailable(nickname)) {
                    connectedClients.put(nickname, clientSocket);
                    out.writeObject("Nickname accepted. Welcome to the chat!");
                    out.flush();
                    broadcast(nickname + " has joined the chat.");
                } else {
                    out.writeObject("Nickname is already in use. Please choose another one.");
                    out.flush();
                    clientSocket.close();
                    return;
                }

//                List<String> connectedUsersList = new ArrayList<>(connectedClients.keySet());
//                out.writeObject(new ConnectionData(connectedUsersList));
//                out.flush();

                String message;
                while ((message = (String) in.readObject()) != null) {
                    broadcast(nickname + ": " + message);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (nickname != null) {
                    connectedClients.remove(nickname);
                    broadcast(nickname + " has left the chat.");
                }
            }
        }
    }

    private static boolean isNicknameAvailable(String nickname) {
        return !connectedClients.containsKey(nickname);
    }

    private static void broadcast(String message) {
        try {
            InetAddress group = InetAddress.getByName("239.0.0.1");
            multicastSocket.setTimeToLive(1);
            byte[] data = message.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, group, 8888);
            multicastSocket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
