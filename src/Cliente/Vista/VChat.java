package Cliente.Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
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
    //private JTextPane chatArea;
    private JTable userList;

    public VChat(/*Usuario user*/) {
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

//    public void appendToChatArea(String nickname, String message) {
//        try {
//            StyledDocument doc = chatArea.getStyledDocument();
//            SimpleAttributeSet keyWord = new SimpleAttributeSet();
//            StyleConstants.setBold(keyWord, true);
//
//            SimpleAttributeSet keyWordMsg = new SimpleAttributeSet();
//             StyleConstants.setForeground(keyWordMsg, Color.RED);
//
//            doc.insertString(doc.getLength(), nickname + ": ", keyWord);
//            doc.insertString(doc.getLength(), message + "\n", keyWordMsg);
//        } catch (BadLocationException e) {
//            throw new RuntimeException(e);
//        }
//
//        /*
//          // Crear estilos para diferentes colores
//        Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
//        Style redStyle = doc.addStyle("Red", defaultStyle);
//        StyleConstants.setForeground(redStyle, Color.RED);
//        Style blueStyle = doc.addStyle("Blue", defaultStyle);
//        StyleConstants.setForeground(blueStyle, Color.BLUE);
//
//        try {
//            // Agregar texto con diferentes colores
//            doc.insertString(doc.getLength(), "This is red. ", redStyle);
//            doc.insertString(doc.getLength(), "This is blue. ", blueStyle);
//            doc.insertString(doc.getLength(), "This is normal text.", defaultStyle);
//        } catch (BadLocationException e) {
//            e.printStackTrace();
//        }
//         */
//    }

    public void updateConnectedUsersList(List<String> connectedUsers) {
        System.out.println(connectedUsers);
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Todas las celdas no son editables
                return false;
            }
        };

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
