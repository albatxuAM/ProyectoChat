package Common;

import java.awt.*;
import java.io.Serializable;

import java.awt.Color;

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
