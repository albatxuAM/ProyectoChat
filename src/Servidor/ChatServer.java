package Servidor;

import Common.Message.ChatMsg;
import Common.Message.ConnectionData;
import Common.Message.Message;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatServer {
    private static final int PUERTO = 12345;
    private static Map<String, Socket> connectedClients = new HashMap<>();
    private static MulticastSocket multicastSocket;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Server listening on port " + PUERTO + "...");

            connectedClients.put("alba", new Socket());

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

    /**
     * @param nickname
     * @return
     */
    private static boolean isNicknameAvailable(String nickname) {
        return !connectedClients.containsKey(nickname);
    }

    /**
     * @param message
     */
    private static void broadcast(Message message) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();

            // Obtener el array de bytes serializado
            byte[] serializedData = byteArrayOutputStream.toByteArray();

            // Crear el DatagramPacket y enviar los datos
            InetAddress group = InetAddress.getByName("239.0.0.1");
            DatagramPacket packet = new DatagramPacket(serializedData, serializedData.length, group, 8888);
            multicastSocket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private String nickname;

        /**
         * @param socket
         */
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        /**
         *
         */
        @Override
        public void run() {
            try {
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

                boolean nicknameAccepted = false;
                while (!nicknameAccepted) {
                    out.writeObject("Enter your nickname: ");
                    out.flush();
                    nickname = (String) in.readObject();

                    if (isNicknameAvailable(nickname)) {
                        connectedClients.put(nickname, clientSocket);
                        nicknameAccepted = true;
                        out.writeObject("Nickname accepted. Welcome to the chat!");
                        out.flush();
                        List<String> connectedUsersList = new ArrayList<>(connectedClients.keySet());
                        broadcast(new ConnectionData(nickname, "has joined the chat.", connectedUsersList));
                    } else {
                        out.writeObject("Nickname is already in use. Please choose another one.");
                        out.flush();
                    }
                }
                List<String> connectedUsersList = new ArrayList<>(connectedClients.keySet());
                out.writeObject(new ConnectionData(nickname, "", connectedUsersList));
                out.flush();

                while (true) {
                    Object message = in.readObject();
                    broadcast(new ChatMsg(nickname, message.toString()));
                }
            } catch (SocketException cnEx) {
                System.err.println("SocketException: " + cnEx.getMessage());
                disconectClient();
            } catch (EOFException eofEx) {
                System.err.println("EOFException: " + eofEx.getMessage());
                disconectClient();
            } catch (ClassNotFoundException e) {
                System.err.println("ClassNotFoundException " + e.getMessage());
                disconectClient();
            } catch (IOException e) {
                System.err.println("IOException " + e.getMessage());
                disconectClient();
            }
        }

        /**
         *
         */
        private void disconectClient() {
            if (nickname != null) {
                System.out.println(nickname + "has left the chat.");
                connectedClients.remove(nickname);
                List<String> connectedUsersList = new ArrayList<>(connectedClients.keySet());
                broadcast(new ConnectionData(nickname, "has left the chat.", connectedUsersList));
            }
        }
    }
}
