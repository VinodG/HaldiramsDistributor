package com.winit.haldiram.parser;

import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.winit.common.Preference;
import com.winit.common.webAccessLayer.Response;
import com.winit.haldiram.dataobject.LoginUserInfo;

public class UserLoginParser extends BaseHandler
{
	private StringBuilder currentValue ;
	private LoginUserInfo objLoginUserInfo;
	private Vector<LoginUserInfo> vecUserInfo ;
	private boolean isAuditor = false;
	private Preference preference;

	public UserLoginParser(){
		preference = Preference.getInstance();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException
	{
		currentValue  = new StringBuilder();
		if(localName.equalsIgnoreCase("BlaseUsers"))
		{
			vecUserInfo  = new Vector<>();
		}
		else if(localName.equalsIgnoreCase("BlaseUserDco"))
		{
			objLoginUserInfo = new LoginUserInfo();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)throws SAXException
	{
		if(localName.equalsIgnoreCase("Status"))
		{
			status = currentValue.toString();
		}
		else if(localName.equalsIgnoreCase("Message"))
		{
			message =  currentValue.toString();
		}
		else if(localName.equalsIgnoreCase("USERID"))
		{
			objLoginUserInfo.strUserId =  currentValue.toString();
//			if(!isAuditor){
//				preference.saveStringInPreference(Preference.USER_ID, objLoginUserInfo.strUserId);
//			}
		}
		else if(localName.equalsIgnoreCase("USERNAME"))
		{
			objLoginUserInfo.strUserName =  currentValue.toString();
			if(isAuditor){
				preference.saveStringInPreference(Preference.USER_NAME, currentValue.toString());
			}
		}
		else if(localName.equalsIgnoreCase("EmpNo"))
		{
			objLoginUserInfo.strEmpNo =  currentValue.toString();
		}
		else if(localName.equalsIgnoreCase("BlaseUserDco"))
		{
			vecUserInfo.add(objLoginUserInfo);
		}

	}

	@Override
	public void characters(char[] ch, int start, int length)throws SAXException
	{
		currentValue.append(new String(ch, start, length));
	}

	@Override
	public Object getData() {
		return new Response(status,message,vecUserInfo);
	}

}
