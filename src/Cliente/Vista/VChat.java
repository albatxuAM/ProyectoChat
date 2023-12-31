package Cliente.Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class VChat {
    private JPanel pPrincipal;
    private JPanel pData;
    private JPanel pTitulo;
    private JTabbedPane pTabs;
    private JPanel pBotones;
    private JButton enviarButton, logOutButton;
    private JTextField tfMsg;
    private JTextArea chatArea;
    private JTable userList;
    private JLabel lNickname;

    /**
     * Constructor de la ventana que contiene el chat
     */
    public VChat() {
        // pTabs.addTab("Resultados", new VPrincipal(user).getpPrincipal());
        // chatArea.setContentType("text/html");
        chatArea.setEditable(false);
    }

    /*
    public static void main(String[] args) {
        JFrame frame = new JFrame("VentanaPrincipal");
        frame.setContentPane(new VChat().pPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setTitle("Dashboard");
        frame.setLocationRelativeTo(null);
    }
     */

    /**
     * Getter
     *
     * @return pPrincipal panel principal del chat
     */
    public JPanel getpPrincipal() {
        return pPrincipal;
    }

    /**
     * * Funcion para anadir mensajes al chatArea
     *
     * @param nickname usuario que envia el mensaje
     * @param message mensaje enviado
     */
    public void appendToChatArea(String nickname, String message) {
        chatArea.append(nickname + ": " + message + "\n");
        System.out.println(nickname + ": " + message);
    }

    /**
     * Funcion para anadir mensajes al chatArea
     *
     * @param message mensaje enviado
     */
    public void appendToChatArea(String message) {
        chatArea.append(message + "\n");
        System.out.println(message);
    }

    /**
     * Funcion para actualizar la lista de usuarios conectdos
     *
     * @param connectedUsers lista de usuarios conectados
     */
    public void updateConnectedUsersList(List<String> connectedUsers) {
        System.out.println(connectedUsers);
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Todas las celdas no son editables
                return false;
            }
        };
        model.addColumn("Usuarios");
        for (String user : connectedUsers) {
            model.addRow(new Object[]{user});
        }
        userList.setModel(model);
    }

    /**
     * Getter
     *
     * @return enviarButton boton de enviar
     */
    public JButton getEnviarButton() {
        return enviarButton;
    }

    /**
     * Getter
     *
     * @return getLogOutButton boton de cerrar sesion
     */
    public JButton getLogOutButton() {
        return logOutButton;
    }

    /**
     * Getter
     *
     * @return tfMsg campo de texto donde se encuentra el mensaje
     */
    public JTextField getTfMsg() {
        return tfMsg;
    }

    /**
     * @param nickname nombre de usuario para mostrar en pantalla
     */
    public void setNickname(String nickname) {
        lNickname.setText(nickname);
    }
}
