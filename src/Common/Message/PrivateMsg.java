package Common.Message;

import java.awt.*;

public class PrivateMsg extends ChatMsg {
    private String otherNickname;

    public PrivateMsg(String msg, String myNickname, String otherNickname) {
        super(myNickname, msg);
        this.otherNickname = otherNickname;
    }

    public PrivateMsg(String msg, String myNickname, String otherNickname, Color color) {
        super(myNickname, msg, color);
        this.otherNickname = otherNickname;
    }
}
