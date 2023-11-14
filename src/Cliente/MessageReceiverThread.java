package Cliente;

import Cliente.Vista.VChat;
import Common.Mangers.ConfigManager;
import Common.Message.ChatMsg;
import Common.Message.ConnectionData;
import Common.Message.Message;
import Common.Validar.Validaciones;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.List;

public class MessageReceiverThread extends Thread {
    InetAddress group;
    private MulticastSocket multicastSocket;
    private VChat vChat;
    private boolean receive = true;

    /**
     * @param multicastSocket
     * @param vChat
     * @param group
     */
    public MessageReceiverThread(MulticastSocket multicastSocket, VChat vChat, InetAddress group) {
        this.group = group;
        this.multicastSocket = multicastSocket;
        this.vChat = vChat;
    }

    /**
     * Recibir mensajes mediante el multicast socket.
     * Y actualizar la ventana acorde: área de chat y lista de usuarios conectados
     */
    @Override
    public void run() {
        try {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            while (receive) {
                multicastSocket.receive(packet);

                // Convertir los datos recibidos en un objeto
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(packet.getData());
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Object receivedObject = objectInputStream.readObject();

                if (receivedObject instanceof ConnectionData) {
                    ConnectionData connectionData = (ConnectionData) receivedObject;
                    List<String> connectedUsers = connectionData.getConnectedUsers();
                    SwingUtilities.invokeLater(() -> updateUsers(connectedUsers));
                    SwingUtilities.invokeLater(() -> appendToChatArea(connectionData.getNickname() + " " + connectionData.getMsg()));
                } else if (receivedObject instanceof ChatMsg) {
                    ChatMsg chatMsg = (ChatMsg) receivedObject;
                    SwingUtilities.invokeLater(() -> appendToChatArea(chatMsg));
                } else {
                    String message = new String(packet.getData(), 0, packet.getLength());
                    SwingUtilities.invokeLater(() -> appendToChatArea(message));
                }

            }
        } catch (SocketException cnEx) {
            if(ConfigManager.getInstance().getDebug())
                System.out.println(cnEx.getMessage());
            Validaciones.mostrarError("Connection ended");
            receive = false;
        } catch (IOException e) {
            if(ConfigManager.getInstance().getDebug())
                System.out.println(e.getMessage());
            Validaciones.mostrarError(e.getMessage());
        } catch (ClassNotFoundException e) {
            if(ConfigManager.getInstance().getDebug())
                System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Cerrar connexion multisocket
     */
    private void closeClient() {
        System.err.println("close broadcast");
        try {
            if (multicastSocket != null) {
                multicastSocket.leaveGroup(group);
                multicastSocket.close();
            }
        } catch (IOException e) {
            if(ConfigManager.getInstance().getDebug())
                System.out.println(e.getMessage());
            Validaciones.mostrarError(e.getMessage());
        }
    }

    /**
     * Setter
     * @param receive
     */
    public void setReceive(boolean receive) {
        this.receive = receive;
    }

    /**
     * @param message
     */
    private void appendToChatArea(Message message) {
        String formattedMessage = "<b>" + message.getNickname() + "</b> " + message.getMsg();
        vChat.appendToChatArea(message.getNickname(), message.getMsg());
    }

    /**
     * Imprimir mensaje en el área de chat
     * @param message
     */
    private void appendToChatArea(String message) {
        vChat.appendToChatArea(message);
    }

    /**
     * Imprimir mensaje en el área de chat
     * @param nickname
     * @param message
     */
    private void appendToChatArea(String nickname, String message) {
        vChat.appendToChatArea(nickname, message);
    }


    /**
     * Actualizar lista de usuarios
     * @param connectedUsers
     */
    private void updateUsers(List<String> connectedUsers) {
        vChat.updateConnectedUsersList(connectedUsers);
    }
}

