package com.winit.haldiram.parser;

import com.winit.common.webAccessLayer.Response;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class GetMasterDataParser extends BaseHandler
{
	String strUrl = "";

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		currentValue = new StringBuilder();
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		if(localName.equalsIgnoreCase("Status")){
			status = currentValue.toString();
		}else if(localName.equalsIgnoreCase("Message")){
			message =  currentValue.toString();
		}else if(localName.equalsIgnoreCase("sqliteFileName")){
			strUrl = currentValue.toString();
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException
	{
		currentValue.append(new String(ch, start, length));
	}

	@Override
	public Object getData() {
		return new Response(status,message,strUrl);
	}
}
