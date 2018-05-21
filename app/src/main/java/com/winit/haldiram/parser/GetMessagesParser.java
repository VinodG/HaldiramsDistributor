package com.winit.haldiram.parser;

import com.winit.common.Preference;
import com.winit.common.util.LogUtils;
import com.winit.common.webAccessLayer.Response;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.haldiram.dataaccesslayer.MessagesDA;
import com.winit.haldiram.dataaccesslayer.SyncLogDA;
import com.winit.haldiram.dataobject.CustomerMessageDO;
import com.winit.haldiram.dataobject.SyncLogDO;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.Vector;

public class GetMessagesParser extends BaseHandler
{
	private StringBuilder currentValue ;
	private CustomerMessageDO objCustomerMessage,objChildMessages;
	private Vector<CustomerMessageDO> vecCustomerMessages;
	private boolean isAuditor = false;
	private Preference preference;
	private int count =0;
	private String currentTime="";

	public GetMessagesParser(){
		preference = Preference.getInstance();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException
	{
		currentValue =new StringBuilder();
		if(localName.equalsIgnoreCase("UserMessages"))
		{
			vecCustomerMessages = new Vector<CustomerMessageDO>();
		}
		else if(localName.equalsIgnoreCase("MessagesDco") && count ==0)
		{
			objCustomerMessage = new CustomerMessageDO();
		}
		else if(localName.equalsIgnoreCase("ChildMessages"))
		{
			count =1;
			objCustomerMessage.vecChildMessage = new Vector<CustomerMessageDO>();
		}
		else if(localName.equalsIgnoreCase("ChildMessagesDco"))
		{
			objChildMessages = new CustomerMessageDO();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)throws SAXException
	{

		if(localName.equalsIgnoreCase("CurrentTime"))
		{
			currentTime = currentValue.toString();
			LogUtils.errorLog("CurrentTime", "Message - "+currentValue.toString());
		}
		else if(localName.equalsIgnoreCase("MESSAGEID"))
		{
			if(currentValue!=null)
			{
//				if(count ==0)
				objCustomerMessage.strMessageID = currentValue.toString();
//				else
//					objChildMessages.strMessageID = currentValue.toString();
			}
		}
		else if(localName.equalsIgnoreCase("FROMID"))
		{
			if(currentValue!=null)
			{
//				if(count ==0)
				objCustomerMessage.strFROMID = ""+currentValue;
//				else
//					objChildMessages.strFROMID = ""+currentValue;
			}
		}
		else if(localName.equalsIgnoreCase("TOID"))
		{
			if(currentValue!=null)
			{
//				if(count ==0)
				objCustomerMessage.strTOID = ""+currentValue;
//				else
//					objChildMessages.strTOID = ""+currentValue;
			}
		}
		else if(localName.equalsIgnoreCase("MESSAGE") && objCustomerMessage!=null)
		{
			if(currentValue!=null)
			{
//				if(count ==0)
				objCustomerMessage.strMessage = ""+currentValue;
//				else if(objChildMessages!=null)
//					objChildMessages.strMessage = ""+currentValue;
			}
		}
		else if(localName.equalsIgnoreCase("MESSAGEDATE"))
		{
			if(currentValue!=null)
			{
//				if(count ==0)
				objCustomerMessage.strMessageDate = currentValue.toString();
//				else
//					objChildMessages.strMessageDate = currentValue.toString();
			}
		}
		else if(localName.equalsIgnoreCase("ISREAD"))
		{
			if(currentValue!=null)
			{
//				if(count ==0)
				objCustomerMessage.ISREAD = currentValue.toString();
//				else
//					objChildMessages.ISREAD = currentValue.toString();
			}
		}
		else if(localName.equalsIgnoreCase("PARENTMESSAGEID"))
		{
			if(currentValue!=null)
			{
//				if(count ==0)
				objCustomerMessage.parentMessageId = currentValue.toString();
//				else
//					objChildMessages.ISREAD = currentValue.toString();
			}
		}
		else if(localName.equalsIgnoreCase("ChildMessagesDco"))
		{
			vecCustomerMessages.add(objChildMessages);
		}
		else if(localName.equalsIgnoreCase("MessagesDco"))
		{
			count=0;
			if(objCustomerMessage!=null)
				vecCustomerMessages.add(objCustomerMessage);
		}
		else if(localName.equalsIgnoreCase("UserMessages"))
		{
			if(vecCustomerMessages.size()>0)
			{
				if(new MessagesDA().insertIntoMessage(vecCustomerMessages)){
					SyncLogDO syncLogDO = new SyncLogDO();
					syncLogDO.action="Success";
					syncLogDO.entity=ServiceUrls.GET_MESSAGE;
					if(currentTime.trim().contains(" "))
						currentTime = currentTime.trim().replace(" ","T");
					syncLogDO.timeStamp = currentTime;
					new SyncLogDA().insertSyncLog(syncLogDO);
					preference.saveStringInPreference(ServiceUrls.ServiceAction.GET_MESSAGE+preference.getStringFromPreference(Preference.EMP_NO,
							"")+Preference.LAST_SYNC_TIME, currentTime);

				}
			}
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)throws SAXException
	{
		currentValue.append(new String(ch, start, length));
	}

	public Vector<CustomerMessageDO> getCustomerMessages()
	{
		return vecCustomerMessages;
	}

	@Override
	public Object getData() {
		return new Response(status,message,vecCustomerMessages);
	}

}
