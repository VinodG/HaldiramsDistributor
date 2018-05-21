package com.winit.haldiram.parser;

import com.winit.common.webAccessLayer.Response;
import com.winit.haldiram.dataaccesslayer.DashboardDA;
import com.winit.haldiram.dataobject.DashboardDO;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.Vector;

/**
 * Created by Srikanth on 5/12/2017.
 */

public class AchievementParser extends BaseHandler{
    private StringBuilder currentValue ;
    private Vector<DashboardDO> vecDashboards ;
    private DashboardDO dashboardDO;


    @Override
    public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException
    {
        currentValue = new StringBuilder();
        if(localName.equalsIgnoreCase("Distributor_AchievementDcos"))
        {
            vecDashboards = new Vector<DashboardDO>();
        }
        else if(localName.equalsIgnoreCase("Distributor_AchievementDco"))
        {
            dashboardDO = new DashboardDO();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)throws SAXException
    {

        if(localName.equalsIgnoreCase("DSMName"))
        {
            dashboardDO.dsmName = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("DSMCode"))
        {
            dashboardDO.dsmCode = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("Target"))
        {
            dashboardDO.target = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("Acheived"))
        {
            dashboardDO.achieved = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("Percentage"))
        {
            dashboardDO.percentage = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("BTG"))
        {
            dashboardDO.btg = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("DailyAverage"))
        {
            dashboardDO.dailyAverage = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("Distributor_AchievementDco"))
        {
            vecDashboards.add(dashboardDO);
        }
        else if(localName.equalsIgnoreCase("Distributor_AchievementDcos"))
        {
            if(vecDashboards != null && vecDashboards.size() > 0)
                insertAchievedData(vecDashboards);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)throws SAXException
    {
        currentValue.append(new String(ch, start, length));
    }
    private void insertAchievedData(Vector<DashboardDO> vecDashboards)
    {
        new DashboardDA().insertAchievement(vecDashboards);
    }

    @Override
    public Object getData() {
        return new Response(status,message,vecDashboards);
    }
}
