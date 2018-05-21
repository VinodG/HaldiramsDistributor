package com.winit.haldiram.dataobject;

import java.io.Serializable;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class MessageDO implements Serializable{
    public String fromId = "";
    public String toId = "";
    public String messageDate = "";
    public String message = "";
    public String messageId = "";
    public boolean isRead;
    public String title = "";
    public String parentMessageId = "";
}
