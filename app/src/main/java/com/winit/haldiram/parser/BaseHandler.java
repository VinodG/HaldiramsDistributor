package com.winit.haldiram.parser;

import com.winit.common.webAccessLayer.ServiceUrls;

import org.xml.sax.helpers.DefaultHandler;

public abstract class BaseHandler extends DefaultHandler
{
	public StringBuilder currentValue;
	protected String status;
	protected String message;
	public final static String apostrophe = "'";
	public final static String sep = ",";

	public abstract Object getData();

	public static BaseHandler getHandler(ServiceUrls.ServiceAction serviceAction){
		switch (serviceAction){
			case LOGIN:
				return new UserLoginParser();
			case MASTER_TABLE:
				return new GetMasterDataParser();
			case INSERT_MESSAGE:
				return new GetMasterDataParser();
			case GET_MESSAGE:
				return new GetMessagesParser();
		}
		return null;
	}

}
