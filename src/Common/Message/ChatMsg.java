package Common.Message;

import java.awt.*;

public class ChatMsg implements Message {
    private String msg;
    private Color color;
    private String nickname;

    /**
     * @param nickname
     * @param msg
     */
    public ChatMsg(String nickname, String msg) {
        this.nickname = nickname;
        this.msg = msg;
    }

    /**
     * @param nickname
     * @param msg
     * @param color
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
     * @param msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return color
     */
    @Override
    public Color getColor() {
        return color;
    }

    /**
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname
     */
    public void setMyNickname(String nickname) {
        this.nickname = nickname;
    }
}
