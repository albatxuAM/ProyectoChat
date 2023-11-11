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

    public VChat() {
        // pTabs.addTab("Resultados", new VPrincipal(user).getpPrincipal());
        // chatArea.setContentType("text/html");
        chatArea.setEditable(false);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("VentanaPrincipal");
        frame.setContentPane(new VChat().pPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setTitle("Dashboard");
        frame.setLocationRelativeTo(null);
    }

    public JPanel getpPrincipal() {
        return pPrincipal;
    }

    public void appendToChatArea(String nickname, String message) {
        chatArea.append(nickname + ": " + message + "\n");
        System.out.println(nickname + ": " + message);
    }

    public void appendToChatArea(String message) {
        chatArea.append(message + "\n");
        System.out.println(message);
    }

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

    public JButton getEnviarButton() {
        return enviarButton;
    }

    public JButton getLogOutButton() {
        return logOutButton;
    }

    public JTextField getTfMsg() {
        return tfMsg;
    }
}
