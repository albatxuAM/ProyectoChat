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

    public VChat(/*Usuario user*/) {
        // pTabs.addTab("Resultados", new VPrincipal(user).getpPrincipal());

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

    public JTextArea getChatArea() {
        return chatArea;
    }

    public void appendToChatArea(String message) {
        chatArea.append(message + "\n");
        System.out.println(message);
    }

    public void updateConnectedUsersList(List<String> connectedUsers) {
        System.out.println(connectedUsers);
        DefaultTableModel model = new DefaultTableModel();
        // Agrega una columna llamada "Usuarios Conectados" a la tabla
        model.addColumn("Usuarios");
        // Agrega los usuarios conectados al modelo de la tabla
        for (String user : connectedUsers) {
            model.addRow(new Object[]{user});
        }
        // Asigna el modelo de tabla actualizado al JTable
        userList.setModel(model);
    }

    public JButton getEnviarButton() {
        return enviarButton;
    }

    public JTextField getTfMsg() {
        return tfMsg;
    }
}
