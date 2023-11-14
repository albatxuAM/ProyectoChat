package Common.Message;

import java.awt.*;

public class ChatMsg implements Message {
    private String msg;
    private Color color;
    private String nickname;

    /**
     * @param nickname nombre de usuario
     * @param msg el texto del mensaje
     */
    public ChatMsg(String nickname, String msg) {
        this.nickname = nickname;
        this.msg = msg;
    }

    /**
     * @param nickname nombre de usuario
     * @param msg el texto del mensaje
     * @param color color del mensaje
     */
    public ChatMsg(String nickname, String msg, Color color) {
        this.nickname = nickname;
        this.msg = msg;
        this.color = color;
    }

    /**
     * @return msg
     */
    @Override
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg el texto del mensaje
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return color del texto
     */
    @Override
    public Color getColor() {
        return color;
    }

    /**
     * @param color color del texto
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return nickname nombre de usuario
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname nombre de usuario
     */
    public void setMyNickname(String nickname) {
        this.nickname = nickname;
    }
}
