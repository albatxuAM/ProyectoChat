package Cliente.Vista;

import Common.Mangers.ConfigManager;

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
     * @return pPrincipal
     */
    public JPanel getpPrincipal() {
        return pPrincipal;
    }

    /**
     * * Funcion para anadir mensajes a el chatArea
     * @param nickname
     * @param message
     */
    public void appendToChatArea(String nickname, String message) {
        chatArea.append(nickname + ": " + message + "\n");
        System.out.println(nickname + ": " + message);
    }

    /**
     * Funcion para anadir mensajes a el chatArea
     * @param message
     */
    public void appendToChatArea(String message) {
        chatArea.append(message + "\n");
        System.out.println(message);
    }

    /**
     * Funcion para actualizar la lista de usuarios conectdos
     * @param connectedUsers
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
     * @return enviarButton
     */
    public JButton getEnviarButton() {
        return enviarButton;
    }

    /**
     * Getter
     * @return getLogOutButton
     */
    public JButton getLogOutButton() {
        return logOutButton;
    }

    /**
     * Getter
     * @return tfMsg
     */
    public JTextField getTfMsg() {
        return tfMsg;
    }
}
