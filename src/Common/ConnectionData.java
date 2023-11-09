package Common;

import java.awt.*;
import java.io.Serializable;
import java.util.List;

public class ConnectionData extends ChatMsg {
    private List<String> connectedUsers;

    public ConnectionData(String nickname, String msg, List<String> connectedUsers) {
        super(nickname, msg);
        this.connectedUsers = connectedUsers;
    }

    public ConnectionData(String nickname, String msg, Color color, List<String> connectedUsers) {
        super(nickname, msg, color);
        this.connectedUsers = connectedUsers;
    }

    public List<String> getConnectedUsers() {
        return connectedUsers;
    }
}
