package Common.Message;

import java.awt.*;
import java.util.List;

public class ConnectionData extends ChatMsg {
    private List<String> connectedUsers;

    /**
     * @param nickname nombre de usuario
     * @param msg el texto del mensaje
     * @param connectedUsers lista de usuarios conectados
     */
    public ConnectionData(String nickname, String msg, List<String> connectedUsers) {
        super(nickname, msg);
        this.connectedUsers = connectedUsers;
    }

    /**
     * @param nickname nombre de usuario
     * @param msg el texto del mensaje
     * @param color color del texto
     * @param connectedUsers  lista de usuarios conectados
     */
    public ConnectionData(String nickname, String msg, Color color, List<String> connectedUsers) {
        super(nickname, msg, color);
        this.connectedUsers = connectedUsers;
    }

    /**
     * @return lista de usuarios conectados
     */
    public List<String> getConnectedUsers() {
        return connectedUsers;
    }
}
