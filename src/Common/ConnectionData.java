package Common;

import java.io.Serializable;
import java.util.List;

public class ConnectionData implements Serializable {
    private List<String> connectedUsers;

    public ConnectionData(List<String> connectedUsers) {
        this.connectedUsers = connectedUsers;
    }

    public List<String> getConnectedUsers() {
        return connectedUsers;
    }
}
