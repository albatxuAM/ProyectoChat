package Common;

import java.awt.*;
import java.io.Serializable;

public class ChatMsg implements Serializable {
    private String msg;
    private Color color;

    public ChatMsg(String msg) {
        this.msg = msg;
    }

    public ChatMsg(String msg, Color color) {
        this.msg = msg;
        this.color = color;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
