package com.winit.haldiram.parser;

import com.winit.common.webAccessLayer.Response;
import com.winit.haldiram.dataaccesslayer.SalesEcoDA;
import com.winit.haldiram.dataobject.SalesEcoDO;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.Vector;

/**
 * Created by Ashoka.Reddy on 5/12/2017.
 */

public class ECODetailParser  extends BaseHandler{
    private StringBuilder currentValue ;
    private Vector<SalesEcoDO> vecDashboards ;
    private SalesEcoDO salesEcoDO;


    @Override
    public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException
    {
        currentValue = new StringBuilder();
        if(localName.equalsIgnoreCase("Distributor_ECODetailDcos"))
        {
            vecDashboards = new Vector<SalesEcoDO>();
        }
        else if(localName.equalsIgnoreCase("Distributor_ECODetailDco"))
        {
            salesEcoDO = new SalesEcoDO();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)throws SAXException
    {

        if(localName.equalsIgnoreCase("DSMName"))
        {
            salesEcoDO.dsmName = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("UOB"))
        {
            salesEcoDO.uob = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("Eco"))
        {
            salesEcoDO.eco = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("ULC"))
        {
            salesEcoDO.ulc = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("ZeroSales"))
        {
            salesEcoDO.zeroSales = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("NewOutlet"))
        {
            salesEcoDO.newOutlet = currentValue.toString();
        }

        else if(localName.equalsIgnoreCase("Distributor_ECODetailDco"))
        {
            vecDashboards.add(salesEcoDO);
        }
        else if(localName.equalsIgnoreCase("Distributor_ECODetailDcos"))
        {
            if(vecDashboards != null && vecDashboards.size() > 0)
                insertECOData(vecDashboards);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)throws SAXException
    {
        currentValue.append(new String(ch, start, length));
    }
    private void insertECOData(Vector<SalesEcoDO> vecDashboards)
    {
        new SalesEcoDA().insertSalesECO(vecDashboards);
    }
    @Override
    public Object getData() {
        return new Response(status,message,vecDashboards);
    }
}
