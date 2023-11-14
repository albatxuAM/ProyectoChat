package Common.Message;

import java.awt.*;

public class PrivateMsg extends ChatMsg {
    private String otherNickname;

    /**
     * @param msg el texto del mensaje
     * @param myNickname nombre de usuario que envia el mensaje
     * @param otherNickname nombre de usuario que recibe el mensaje
     */
    public PrivateMsg(String msg, String myNickname, String otherNickname) {
        super(myNickname, msg);
        this.otherNickname = otherNickname;
    }

    /**
     * @param msg el texto del mensaje
     * @param myNickname nombre de usuario que envia el mensaje
     * @param otherNickname nombre de usuario que recibe el mensaje
     * @param color color del texto
     */
    public PrivateMsg(String msg, String myNickname, String otherNickname, Color color) {
        super(myNickname, msg, color);
        this.otherNickname = otherNickname;
    }
}
