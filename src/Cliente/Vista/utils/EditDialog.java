package Cliente.Vista.utils;

import Cliente.Controlador.Mangers.ConfigManager;
import Cliente.Modelo.Excepciones.Validaciones;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;;
import java.time.LocalDate;

public class EditDialog<T> extends JDialog {
    JPanel pContent;
    private final T item;
    private T newItem;
    private final ImageIcon icon;
    private final JButton saveButton;

    public EditDialog(T item) {
        this.icon = new ImageIcon(ConfigManager.getInstance().getImagePath() + "icon.png");
        this.item = item;

        // Crear un panel principal con un BorderLayout y un margen de 20 píxeles
        JPanel pPrincipal = new JPanel(new BorderLayout());
        pPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        setStyle(pPrincipal);

        // Obtén la clase del objeto T
        Class<?> clazz = item.getClass();
        Field[] fields = clazz.getDeclaredFields();

        pContent = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espacio entre componentes
        setStyle(pContent);

        // Usa un JScrollPane para hacer pContent scrollable
        JScrollPane scrollPane = new JScrollPane(pContent);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        for (Field field : fields) {
            field.setAccessible(true);

            JLabel label = new JLabel(field.getName().toUpperCase());

            gbc.gridx = 0; // Columna 0 para etiqueta
            gbc.weightx = 0.3; // Ancho del 30%
            gbc.anchor = GridBagConstraints.EAST; // Alinea la etiqueta a la derecha
            pContent.add(label, gbc);


            JTextField textField = new JTextField();
            textField.setName(field.getName().toUpperCase());
            try {
                if (field.get(item) != null)
                    textField.setText(field.get(item).toString());
                else
                    textField.setText("");

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            //pContent.add(textField);
            gbc.gridx = 1; // Columna 1 para campo de texto
            gbc.weightx = 0.7; // Ancho del 70%
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST; // Alinea el campo de texto a la izquierda
            pContent.add(textField, gbc);

        }

        JPanel pButton = new JPanel();
        pButton.setLayout(new BoxLayout(pButton, BoxLayout.Y_AXIS));
        pButton.add(Box.createRigidArea(new Dimension(0, 10)));
        saveButton = new JButton("Guardar");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newItem = getObject();
                if (validarCampos()) {
                    newItem = getObject();
//                    FicherosManager.getInstance().guardar(newItem);
                    dispose();
                }
            }
        });

        setStyle(pButton);
        setButtonStyle(saveButton);
        pButton.add(saveButton);

        //pPrincipal.add(pContent, BorderLayout.CENTER);
        //pContent.add(pButton);
        gbc.gridx = 1; // Columna 1 para campo de texto
        gbc.weightx = 0.7; // Ancho del 70%
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST; // Alinea el campo de texto a la izquierda
        pContent.add(pButton, gbc);
        pPrincipal.add(scrollPane, BorderLayout.CENTER);

        // Configuración del diálogo
        setTitle("Editar elemento");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(pPrincipal);
        setModal(true);
        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setStyle(JPanel panel) {
        // Personalizar el fondo
        panel.setBackground(new Color(233, 252, 252));
    }

    private void setButtonStyle(JButton button) {
        button.setBackground(new Color(51, 56, 225));
        button.setForeground(new Color(233, 252, 252));
        button.setFont(new Font("Cambria Math", Font.PLAIN, 16));

        // Establecer un borde al botón
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }

    public T getObject() {
        try {
            Class<?> clazz = item.getClass();
            T newItem = (T) clazz.newInstance();

            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);

                if (field.getType() == LocalDate.class) {
                    JTextField textField = findTextFieldByName(field.getName());
                    if (textField != null) {
                        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Formato de fecha
                        LocalDate date = LocalDate.parse(textField.getText());
                        field.set(newItem, date);
                    }
                } else if (field.getType() == String.class) {
                    JTextField textField = findTextFieldByName(field.getName());
                    if (textField != null) {
                        field.set(newItem, textField.getText());
                    }
                } else if (field.getType() == Integer.class || field.getType() == int.class) {
                    JTextField textField = findTextFieldByName(field.getName());
                    if (textField != null) {
                        int value = Integer.parseInt(textField.getText());
                        field.set(newItem, value);
                    }
                }
            }
            return newItem;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private JTextField findTextFieldByName(String fieldName) {
        for (Component component : pContent.getComponents()) {
            if (component instanceof JTextField) {
                if (component.getName() != null && component.getName().equalsIgnoreCase(fieldName)) {
                    return (JTextField) component;
                }
            }
        }
        return null;
    }

    private boolean validarCampos() {
        // Obtén la clase del objeto T
        Class<?> clazz = item.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(newItem);
                if (value == null) {
                    Validaciones.mostrarError("Por favor, complete todos los campos");
                    return false;
                } else if (value instanceof String && ((String) value).trim().isEmpty()) {
                    Validaciones.mostrarError("Por favor, complete todos los campos");
                    return false;
                }

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }


    public T getNewItem() {
        return newItem;
    }

}
