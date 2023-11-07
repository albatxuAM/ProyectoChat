package Cliente;

import Cliente.Vista.VChat;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class MessageReceiverThread extends Thread {
    private MulticastSocket multicastSocket;
    private VChat vChat;

    public MessageReceiverThread(MulticastSocket multicastSocket, VChat vChat) {
        this.multicastSocket = multicastSocket;
        this.vChat = vChat;
    }

    @Override
    public void run() {
        try {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            while (true) {
                multicastSocket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());

                SwingUtilities.invokeLater(() -> appendToChatArea(message));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void appendToChatArea(String message) {
        System.out.println(message);
        vChat.appendToChatArea(message);
    }
}

