package Common.Message;

import java.awt.*;
import java.io.Serializable;

public interface Message extends Serializable {
    /**
     * @return String obtener el texto del mensaje
     */
    String getMsg();

    /**
     * @return Color obtener el color del texto
     */
    Color getColor();

    /**
     * @return String obtener el nickname del usuario que manda del mensaje
     */
    String getNickname();
}
