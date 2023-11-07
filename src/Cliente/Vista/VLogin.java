package Cliente.Vista;

import Cliente.Controlador.Mangers.ConfigManager;
import Cliente.Controlador.Mangers.LoginManager;
import Cliente.Controlador.Mangers.VentanasManager;
import Cliente.Modelo.Usuario;
import Cliente.Modelo.Excepciones.Validaciones;
import Cliente.Vista.utils.EditDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VLogin {
    ImageIcon logoImg;
    private JPanel pPrincipal;
    private JTextField tfCorreo;
    private JButton loginButton;
    private JPasswordField pfClave;
    private JCheckBox ckVer;
    private JLabel logo;
    private JLabel linkRegistrar;

    public VLogin() {
        logoImg = new ImageIcon(ConfigManager.getInstance().getImagePath() + "logo.png");
        Image img1 = logoImg.getImage();
        Image newimg1 = img1.getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH);
        logoImg = new ImageIcon(newimg1);

        logo.setIcon(logoImg);

        pfClave.setEchoChar('*');

        ckVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ckVer.isSelected()) {
                    pfClave.setEchoChar((char) 0);

                } else {
                    pfClave.setEchoChar('*');
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        linkRegistrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openRegisterWindow();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                linkRegistrar.setText("<html><u>¿No tienes cuenta? Registrate</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                linkRegistrar.setText("¿No tienes cuenta? Registrate");
            }
        });

        linkRegistrar.setVisible(false);
    }

    private void openRegisterWindow() {
        EditDialog<Usuario> editDialog = new EditDialog<Usuario>(new Usuario());
    }

    private void login() {
        JPasswordField psswrd = pfClave;
        psswrd.setEchoChar((char) 0);

        if (Validaciones.validarEmail(tfCorreo) && Validaciones.validarTexto(psswrd)) {
            Usuario user = LoginManager.getInstance().login(tfCorreo.getText(), psswrd.getPassword());
            if (user != null)
                VentanasManager.getInstance().mostrarVentanaCarga(user);
        }
    }

    public JPanel getpPrincipal() {
        return pPrincipal;
    }
}


