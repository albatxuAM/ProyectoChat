package Common.Message;

import java.awt.*;
import java.io.Serializable;

public interface Message extends Serializable {
    /**
     * @return String
     */
    String getMsg();

    /**
     * @return Color
     */
    Color getColor();

    /**
     * @return String
     */
    String getNickname();
}
