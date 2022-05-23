package cn.ean.emp.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author ean
 * @FileName ChatMessageDTO
 * @Date 2022/5/23 17:54
 **/
@Data
@Accessors(chain = true)
public class ChatMessageDTO {

    /**
     * 从哪接受消息
     */
    private String from;

    /**
     * 将消息发到哪
     */
    private String to;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 时间
     */
    private LocalDateTime date;

    /**
     * 昵称
     */
    private String formNickName;
}
