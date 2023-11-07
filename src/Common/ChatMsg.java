package Common;

import java.io.Serializable;

public class ChatMsg implements Serializable {
    private ChatMsgType type;
    private int payloadSize;
    private byte[] payload;

    public ChatMsg(ChatMsgType type, byte[] payload) {
        this.type = type;
        this.payload = payload;
        this.payloadSize = payload.length;
    }

    public ChatMsgType getType() {
        return type;
    }

    public int getPayloadSize() {
        return payloadSize;
    }

    public byte[] getPayload() {
        return payload;
    }
}
