package Common.Modelo;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validaciones {

    public static boolean validarTexto(JTextField textField) {
        try {
            String nombre = textField.getText();
            if (nombre.isEmpty())
                throw new Exception("El " + textField.getName() + " es un dato obligatorio");

            // Pattern patron = Pattern.compile("^[A-Z][a-z]+([ ][a-z]+)*$");
            // Matcher m = patron.matcher(nombre);
            // if (!m.matches())
            //     throw new Exception(textField.getName() + " no válido");

            //textField.setEditable(false);
            return true;
        } catch (Exception e) {
            mostrarError(e.getMessage());
            textField.setSelectionStart(0);
            textField.setSelectionEnd(textField.getText().length());
            textField.requestFocus();
            return false;
        }
    }

    public static boolean validarInt(JTextField textField) {
        try {
            String num_s = textField.getText();
            if (num_s.isEmpty())
                throw new Exception("El " + textField.getName() + " es un dato obligatorio");

            int num = Integer.parseInt(num_s);
            if (num < 0)
                throw new Exception(textField.getName() + " no válido");

            //textField.setEditable(false);
            return true;

        } catch (Exception e) {
            mostrarError(e.getMessage());
            textField.setSelectionStart(0);
            textField.setSelectionEnd(textField.getText().length());
            textField.requestFocus();
            return false;
        }
    }

    public static boolean validarFloat(JTextField textField) {
        try {
            String num_s = textField.getText();
            if (num_s.isEmpty())
                throw new Exception("El " + textField.getName() + " es un dato obligatorio");

            float num = Float.parseFloat(num_s);
            if (num < 0)
                throw new Exception(textField.getName() + " no válido");

            //textField.setEditable(false);
            return true;

        } catch (Exception e) {
            mostrarError(e.getMessage());
            textField.setSelectionStart(0);
            textField.setSelectionEnd(textField.getText().length());
            textField.requestFocus();
            return false;
        }
    }

    public static boolean validarFecha(JTextField textField) {
        try {
            String date_s = textField.getText();
            if (date_s.isEmpty())
                throw new Exception("El " + textField.getName() + " es un dato obligatorio");

            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fecha = LocalDate.parse(textField.getText(), formato);

            LocalDate hoy = LocalDate.now();

            if (fecha.isAfter(hoy)) {
                mostrarError("La fecha no puede ser posterior a hoy");
                return false;
            }

            return true;

        } catch (Exception e) {
            mostrarError("Formato de fecha incorrecto. Mantenga el formato dd/MM/yyyy");
            textField.setSelectionStart(0);
            textField.setSelectionEnd(textField.getText().length());
            textField.requestFocus();
            return false;
        }
    }

    public static boolean validarFechaPosterior(JTextField textField) {
        try {
            String date_s = textField.getText();
            if (date_s.isEmpty())
                throw new Exception("El " + textField.getName() + " es un dato obligatorio");

            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fecha = LocalDate.parse(textField.getText(), formato);

            LocalDate hoy = LocalDate.now();

            if (fecha.isBefore(hoy)) {
                mostrarError("La fecha no puede ser anterior a hoy");
                return false;
            }

            return true;

        } catch (Exception e) {
            mostrarError("Formato de fecha incorrecto. Mantenga el formato dd/MM/yyyy");
            textField.setSelectionStart(0);
            textField.setSelectionEnd(textField.getText().length());
            textField.requestFocus();
            return false;
        }
    }

    public static boolean validarHora(JTextField hora) {
        try {
            String hora_s = hora.getText();
            if (hora_s.isEmpty())
                throw new Exception("La hora es un dato obligatorio");

            LocalTime horaI = LocalTime.parse(hora_s);

            return true;
        } catch (Exception e) {
            mostrarError(e.getMessage());
            hora.setSelectionStart(0);
            hora.setSelectionEnd(hora.getText().length());
            hora.requestFocus();
            return false;
        }
    }

    public static boolean validarEmail(JTextField textField) {
        try {
            String email = textField.getText();
            if (email.isEmpty())
                throw new Exception("El " + textField.getName() + " es un dato obligatorio");

            Pattern patron = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher m = patron.matcher(email);
            if (!m.matches())
                throw new Exception("Email: " + email + " no válido");

            //textField.setEditable(false);
            return true;
        } catch (Exception e) {
            mostrarError(e.getMessage());
            textField.setSelectionStart(0);
            textField.setSelectionEnd(textField.getText().length());
            textField.requestFocus();
            return false;
        }
    }

    public static void mostrarError(String mensaje) {
        String[] botones = {"Aceptar"};
        JOptionPane.showOptionDialog(null, mensaje, "ERROR",
                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, botones, botones[0]);
    }


}