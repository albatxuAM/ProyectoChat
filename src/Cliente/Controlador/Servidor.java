package Cliente.Controlador;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    private static final int PUERTO = 4999;
    private static final int MAX_CONN = 5;
    private static int clienteCounter = 0;

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(PUERTO, MAX_CONN);

        while (clienteCounter < MAX_CONN) {
            System.out.println("Esperando conexión...");

            Socket cs = ss.accept();
            clienteCounter++;

            System.out.println("Cliente.Controlador.Cliente " + clienteCounter + " en línea");

            Thread clienteThread = new Thread(new ClienteHandler(cs, clienteCounter));
            clienteThread.start();
        }
    }

    static class ClienteHandler implements Runnable {
        private final Socket clienteSocket;
        private final int numCli;

        public ClienteHandler(Socket clienteSocket, int numCli) {
            this.clienteSocket = clienteSocket;
            this.numCli = numCli;
        }

        @Override
        public void run() {
            try {
                InputStream auxIn = clienteSocket.getInputStream();
                DataInputStream flujoIn = new DataInputStream(auxIn);

                OutputStream auxOut = clienteSocket.getOutputStream();
                DataOutputStream flujoOut = new DataOutputStream(auxOut);

                String msg;
                while ((msg = flujoIn.readUTF()) != null) {
//                    if (msg.equalsIgnoreCase("stop")) {
//                        System.out.println("Cliente.Controlador.Cliente " + numCli + " ha enviado la señal de fin. Cerrando la conexión.");
//                        break;
//                    }

                    System.out.println("Cliente.Controlador.Cliente " + numCli + " : " + msg);

                    // Convertir el mensaje a mayúsculas y enviarlo de vuelta al cliente
                    flujoOut.writeUTF(msg.toUpperCase());
                }

                auxIn.close();
                flujoOut.close();

                clienteSocket.close();
                System.out.println("Cliente.Controlador.Cliente " + numCli + " desconectado");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}




