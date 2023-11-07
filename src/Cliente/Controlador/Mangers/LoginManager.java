package Cliente.Controlador.Mangers;

import Cliente.Modelo.Usuario;
import Cliente.Modelo.Excepciones.Validaciones;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LoginManager {

    private static LoginManager instance;

    public static LoginManager getInstance() {
        if (instance == null) {
            instance = new LoginManager();
        }
        return instance;
    }

    public Usuario login(String email, char[] password) {
        try {
            //    List<Usuario> usuarios = FicherosManager.getInstance().leerUsuarios();
            List<Usuario> usuarios = new LinkedList<Usuario>();

            // Busca un usuario con el email proporcionado
            for (Usuario usuario : usuarios) {
                if (usuario.getEmail().equalsIgnoreCase(email) &&
                        Arrays.equals(usuario.getClave().toCharArray(), password)) {
                    return usuario;
                }
            }
            Validaciones.mostrarError("Credencial incorrecta. Pruebe de nuevo");
            return null;
        } catch (Exception e) {
            Validaciones.mostrarError("Error de E/S o error al leer el objeto.");
            return null;
        }
    }
}