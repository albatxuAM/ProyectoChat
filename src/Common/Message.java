package Common;

import java.awt.Color;
import java.io.Serializable;
import java.io.Serializable;

public interface Message extends Serializable {
    String getMsg();
    Color getColor();
    String getNickname();
}
