package Common.Message;

import java.awt.*;
import java.io.Serializable;

public interface Message extends Serializable {
    String getMsg();

    Color getColor();

    String getNickname();
}
