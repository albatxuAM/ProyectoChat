package Cliente;

import Cliente.Modelo.Excepciones.Validaciones;
import Cliente.Vista.VChat;

import javax.swing.*;
import java.io.IOException;
import java.net.ConnectException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.SocketException;

public class MessageReceiverThread extends Thread {
    private MulticastSocket multicastSocket;
    private VChat vChat;

    private boolean receive = true;

    public MessageReceiverThread(MulticastSocket multicastSocket, VChat vChat) {
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
                String message = new String(packet.getData(), 0, packet.getLength());

                SwingUtilities.invokeLater(() -> appendToChatArea(message));
            }
        } catch (SocketException cnEx) {
            Validaciones.mostrarError("Connection refused");
            receive = false;
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setReceive(boolean receive) {
        this.receive = receive;
    }

    private void appendToChatArea(String message) {
        System.out.println(message);
        vChat.appendToChatArea(message);
    }
}

