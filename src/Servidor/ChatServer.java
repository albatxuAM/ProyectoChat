package Servidor;

import Common.Mangers.ConfigManager;
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
    private static final int PUERTO = ConfigManager.getInstance().getServerPort();
    private static Map<String, Socket> connectedClients = new HashMap<>();
    private static MulticastSocket multicastSocket;
    private static InetAddress group;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Server listening on port " + PUERTO + "...");

            if (ConfigManager.getInstance().getDebug())
                connectedClients.put("alba", new Socket());

            multicastSocket = new MulticastSocket(ConfigManager.getInstance().getBroadcastPort());
            group = InetAddress.getByName(ConfigManager.getInstance().getBroadcastIP());
            multicastSocket.joinGroup(group);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            if (ConfigManager.getInstance().getDebug())
                System.out.println(e.getMessage());
        }
    }

    /**
     * Comprobar que el nickanme no existe
     * @param nickname
     * @return
     */
    private static boolean isNicknameAvailable(String nickname) {
        return !connectedClients.containsKey(nickname);
    }

    /**
     * Mandar un mensaje mediante multicast socket
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
            //InetAddress group = InetAddress.getByName("239.0.0.1");
            DatagramPacket packet = new DatagramPacket(serializedData, serializedData.length,
                                            group, ConfigManager.getInstance().getBroadcastPort());
            multicastSocket.send(packet);
        } catch (IOException e) {
            if (ConfigManager.getInstance().getDebug())
                System.out.println(e.getMessage());
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
         * Confimar que el nickaname esta disponible
         * y quedar a la espera de recibir mensajes del cliente que enviar al resto
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
                if (ConfigManager.getInstance().getDebug())
                    System.err.println("SocketException: " + cnEx.getMessage());
                disconectClient();
            } catch (EOFException eofEx) {
                if (ConfigManager.getInstance().getDebug())
                    System.err.println("EOFException: " + eofEx.getMessage());
                disconectClient();
            } catch (ClassNotFoundException e) {
                if (ConfigManager.getInstance().getDebug())
                    System.err.println("ClassNotFoundException " + e.getMessage());
                disconectClient();
            } catch (IOException e) {
                if (ConfigManager.getInstance().getDebug())
                    System.err.println("IOException " + e.getMessage());
                disconectClient();
            }
        }

        /**
         * Desconectar un cliente
         */
        private void disconectClient() {
            if (nickname != null) {
                System.err.println(nickname + "has left the chat.");
                connectedClients.remove(nickname);
                List<String> connectedUsersList = new ArrayList<>(connectedClients.keySet());
                broadcast(new ConnectionData(nickname, "has left the chat.", connectedUsersList));
            }
        }
    }
}
