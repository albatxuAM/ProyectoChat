package Cliente.Controlador.Mangers;

import Cliente.Modelo.Usuario;
import Cliente.Vista.VChat;
import Cliente.Vista.VLogin;

import javax.swing.*;

public class VentanasManager {
    private static VentanasManager instance = null;
    private final ImageIcon icon;
    private JFrame VActive;

    private VentanasManager() {
        // Constructor privado para evitar instanciación externa.
        icon = new ImageIcon(ConfigManager.getInstance().getImagePath() + "icon.png");
    }

    public static VentanasManager getInstance() {
        if (instance == null) {
            instance = new VentanasManager();
        }
        return instance;
    }

    public void mostrarVentanaLogin() {
        cerrarVentana();
        VActive = new JFrame("Inicio de sesion");
        VActive.setContentPane(new VLogin().getpPrincipal());
        configurarVentana(VActive);
    }

    public void mostrarVentanaCarga(Usuario user) {
        cerrarVentana();
        VActive = new JFrame("Cargando...");
        //    VActive.setContentPane(new VCarga(user).getpPrincipal());
        configurarVentana(VActive);
    }

    public void mostrarVentanaDashboard(Usuario user) {
        cerrarVentana();
        VActive = new JFrame("LIGA TicTacToe");
        VActive.setContentPane(new VChat().getpPrincipal());
        configurarVentana(VActive);

        VActive.setSize(800, 600);
    }

    private void configurarVentana(JFrame ventana) {
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setIconImage(icon.getImage());
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    private void cerrarVentana() {
        if (VActive != null)
            VActive.dispose();
    }
}
