package Cliente.Controlador;

import Cliente.Controlador.Mangers.VentanasManager;

public class Main {
    public static void main(String[] args) {
        VentanasManager.getInstance().mostrarVentanaDashboard(null);
    }
}