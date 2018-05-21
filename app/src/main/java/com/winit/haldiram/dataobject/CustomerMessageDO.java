package com.winit.haldiram.dataobject;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by Awaneesh on 5/12/2017.
 */

public class CustomerMessageDO implements Serializable{
    public String strStatus = "";
    public String strMessageID="";
    public String strFROMID="";
    public String strTOID="";
    public String strMessage = "";
    public String strMessageDate = "";
    public Vector<CustomerMessageDO> vecChildMessage;
    public String Conversation_Id="";
    public String ISREAD="";
    public String RouteCode="";
    public String Title="";
    public String parentMessageId="";

    public String strUserID = "";
    public String strTo = "";
    public String strFrom = "";
    public String strMessageBody = "";

    public final static String MESSAGE_TITLE = "SFA Message";
    public final static String MESSAGE_BODY = "message";
}
