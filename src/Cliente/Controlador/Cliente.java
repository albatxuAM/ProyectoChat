package Cliente.Controlador;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    private static final int PUERTO = 4999;
    private static final String HOST = "localhost";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        Socket skCliente = new Socket(HOST, PUERTO);
        OutputStream auxOut = skCliente.getOutputStream();
        DataOutputStream flujoOut = new DataOutputStream(auxOut);
        InputStream auxIn = skCliente.getInputStream();
        DataInputStream flujoIn = new DataInputStream(auxIn);

        String mensaje;
        do {
            System.out.print("Ingrese un mensaje para el servidor (o 'stop' para detener): ");
            mensaje = scanner.nextLine();

            if (!mensaje.equalsIgnoreCase("stop")) {

                flujoOut.writeUTF(mensaje);

                String respuesta;
                // while ((respuesta = flujoIn.readUTF()) != null) {
                System.out.println("Server: " + flujoIn.readUTF());
//                    if (respuesta.equalsIgnoreCase("fin")) {
//                        break;
//                    }
//                }


            }
        } while (!mensaje.equalsIgnoreCase("stop"));

        flujoOut.close();
        flujoIn.close();

        auxOut.close();

        skCliente.close();

        scanner.close();
        System.out.println("Cliente.Controlador.Cliente detenido.");
    }
}