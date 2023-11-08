package Common;

import java.awt.*;
import java.io.Serializable;

public class PrivateMsg extends ChatMsg implements Serializable {
    private String myNickname;
    private String otherNickname;

    public PrivateMsg(String msg, String myNickname, String otherNickname) {
        super(msg);
        this.myNickname = myNickname;
        this.otherNickname = otherNickname;
    }

    public PrivateMsg(String msg, String myNickname, String otherNickname, Color color) {
        super(msg, color);
        this.myNickname = myNickname;
        this.otherNickname = otherNickname;
    }
}
