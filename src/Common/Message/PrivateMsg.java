package Common.Message;

import java.awt.*;

public class PrivateMsg extends ChatMsg {
    private String otherNickname;

    /**
     * @param msg
     * @param myNickname
     * @param otherNickname
     */
    public PrivateMsg(String msg, String myNickname, String otherNickname) {
        super(myNickname, msg);
        this.otherNickname = otherNickname;
    }

    /**
     * @param msg
     * @param myNickname
     * @param otherNickname
     * @param color
     */
    public PrivateMsg(String msg, String myNickname, String otherNickname, Color color) {
        super(myNickname, msg, color);
        this.otherNickname = otherNickname;
    }
}
