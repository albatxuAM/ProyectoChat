package Cliente;

import Cliente.Vista.VChat;
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

    public MessageReceiverThread(MulticastSocket multicastSocket, VChat vChat, InetAddress group) {
        this.group = group;
        this.multicastSocket = multicastSocket;
        this.vChat = vChat;
    }

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
                    SwingUtilities.invokeLater(() -> appendToChatArea(connectionData.getNickname(), connectionData.getMsg()));
                } else if (receivedObject instanceof ChatMsg) {
                    ChatMsg chatMsg = (ChatMsg) receivedObject;
                    SwingUtilities.invokeLater(() -> appendToChatArea(chatMsg));
                } else {
                    String message = new String(packet.getData(), 0, packet.getLength());
                    SwingUtilities.invokeLater(() -> appendToChatArea(message));

                    //SwingUtilities.invokeLater(() -> appendToChatArea(receivedObject.toString()));
                }

            }
        } catch (SocketException cnEx) {
            Validaciones.mostrarError("Connection ended");
            receive = false;
        } catch (IOException e) {
            Validaciones.mostrarError(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeClient() {
        System.err.println("close broadcast");
        try {
            if (multicastSocket != null) {
                multicastSocket.leaveGroup(group);
                multicastSocket.close();
            }
        } catch (IOException ex) {
            Validaciones.mostrarError(ex.getMessage());
        }
    }

    public void setReceive(boolean receive) {
        this.receive = receive;
    }

    private void appendToChatArea(Message message) {
        String formattedMessage = "<b>" + message.getNickname() + "</b> " + message.getMsg();
        vChat.appendToChatArea(message.getNickname(), message.getMsg());
    }

    private void appendToChatArea(String message) {
        vChat.appendToChatArea(message);
    }

    private void appendToChatArea(String nickname, String message) {
        vChat.appendToChatArea(nickname, message);
    }


    private void updateUsers(List<String> connectedUsers) {
        vChat.updateConnectedUsersList(connectedUsers);
    }
}

