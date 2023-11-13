package Common.Message;

import java.awt.*;
import java.util.List;

public class ConnectionData extends ChatMsg {
    private List<String> connectedUsers;

    /**
     * @param nickname
     * @param msg
     * @param connectedUsers
     */
    public ConnectionData(String nickname, String msg, List<String> connectedUsers) {
        super(nickname, msg);
        this.connectedUsers = connectedUsers;
    }

    /**
     * @param nickname
     * @param msg
     * @param color
     * @param connectedUsers
     */
    public ConnectionData(String nickname, String msg, Color color, List<String> connectedUsers) {
        super(nickname, msg, color);
        this.connectedUsers = connectedUsers;
    }

    /**
     * @return connectedUsers
     */
    public List<String> getConnectedUsers() {
        return connectedUsers;
    }
}
