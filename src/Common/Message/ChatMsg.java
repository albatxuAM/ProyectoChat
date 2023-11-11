package Common.Message;

import java.awt.*;

public class ChatMsg implements Message {
    private String msg;
    private Color color;
    private String nickname;

    public ChatMsg(String nickname, String msg) {
        this.nickname = nickname;
        this.msg = msg;
    }

    public ChatMsg(String nickname, String msg, Color color) {
        this.nickname = nickname;
        this.msg = msg;
        this.color = color;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getNickname() {
        return nickname;
    }

    public void setMyNickname(String nickname) {
        this.nickname = nickname;
    }
}
