package net.le.tourism.websocket.entity.vo;

import lombok.Getter;
import lombok.Setter;
import net.le.tourism.websocket.enums.ResultType;
import net.le.tourism.websocket.enums.SocketMsgType;

/**
 * @author hanle
 * @version v1.0
 * @date 2019/9/30
 * @modify
 * @copyright zhishoubao
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Setter
@Getter
public class SocketMessageVO {

    /**
     * socket 消息类型
     */
    private String msgType;

    /**
     * socket 消息数据
     */
    private String data;

    public SocketMessageVO() {
        this.msgType = SocketMsgType.RESULT.name();
        this.data = ResultType.SUCCESS.name();

    }

    public SocketMessageVO(String MsgType, String data) {
        this.msgType = MsgType;
        this.data = data;
    }
}
