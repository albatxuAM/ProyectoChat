package Cliente.Modelo;

import java.io.Serializable;

public class Usuario implements Serializable, Identificable {
    private int ID;
    private String email;
    private String clave;

    public Usuario() {
    }

    public Usuario(int ID, String email, String clave) {
        this.ID = ID;
        this.email = email;
        this.clave = clave;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public String toString() {
        return email;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void setID(int id) {
        ID = id;
    }
}
