package Common;

import java.awt.*;
import java.io.Serializable;

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

    @Override
    public Color getColor() {
        return color;
    }

    public String getNickname() {
        return nickname;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setColor(Color color) {
        this.color = color;
    }


    public void setMyNickname(String nickname) {
        this.nickname = nickname;
    }
}
