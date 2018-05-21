package com.winit.haldiram.parser;

import com.winit.common.webAccessLayer.Response;
import com.winit.haldiram.dataaccesslayer.UserDA;
import com.winit.haldiram.dataobject.UserDO;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.Vector;

/**
 * Created by Srikanth on 5/12/2017.
 */

public class GetUsersParser extends BaseHandler{
    private StringBuilder currentValue ;
    private Vector<UserDO> vecUsers ;
    private UserDO userDO;


    @Override
    public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException
    {
        currentValue = new StringBuilder();
        if(localName.equalsIgnoreCase("UserDcos"))
        {
            vecUsers = new Vector<UserDO>();
        }
        else if(localName.equalsIgnoreCase("BlaseUserDco"))
        {
            userDO = new UserDO();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)throws SAXException
    {

        if(localName.equalsIgnoreCase("USERID"))
        {
            userDO.userId = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("USERNAME"))
        {
            userDO.userName = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("SALESMANCODE"))
        {
            userDO.salesmanCode = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("Token"))
        {
            userDO.token = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("EmpNo"))
        {
            userDO.empNo = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("ManagerEmpNo"))
        {
            userDO.empNo = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("Target"))
        {
            userDO.empNo = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("AchievedTarget"))
        {
            userDO.empNo = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("WorkingDays"))
        {
            userDO.empNo = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("TotalWorkingDays"))
        {
            userDO.empNo = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("intUserId"))
        {
            userDO.empNo = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("IsOnlineOnly"))
        {
            userDO.empNo = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("IsFOC"))
        {
            userDO.empNo = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("IsEOT"))
        {
            userDO.empNo = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("SiteRegion"))
        {
            userDO.empNo = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("CollectionTarget"))
        {
            userDO.empNo = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("AchievedCollectionTarget"))
        {
            userDO.empNo = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("BlaseUserDco"))
        {
            vecUsers.add(userDO);
        }
        else if(localName.equalsIgnoreCase("UserDcos"))
        {
            if(vecUsers != null && vecUsers.size() > 0)
                insertUsersData(vecUsers);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)throws SAXException
    {
        currentValue.append(new String(ch, start, length));
    }
    private void insertUsersData(Vector<UserDO> vecUsers)
    {
        new UserDA().insertUsers(vecUsers);
    }

    @Override
    public Object getData() {
        return new Response(status,message,vecUsers);
    }
}
