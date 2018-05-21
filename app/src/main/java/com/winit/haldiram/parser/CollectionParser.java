package com.winit.haldiram.parser;

import com.winit.common.webAccessLayer.Response;
import com.winit.haldiram.dataaccesslayer.CollectionDA;
import com.winit.haldiram.dataobject.CollectionsDO;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.Vector;

/**
 * Created by Ashoka.Reddy on 5/12/2017.
 */

public class CollectionParser extends BaseHandler {
    private StringBuilder currentValue ;
    private Vector<CollectionsDO> vecDashboards ;
    private CollectionsDO collectionsDO;


    @Override
    public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException
    {
        currentValue = new StringBuilder();
        if(localName.equalsIgnoreCase("Distributor_CollectionDcos"))
        {
            vecDashboards = new Vector<CollectionsDO>();
        }
        else if(localName.equalsIgnoreCase("Distributor_CollectionDco"))
        {
            collectionsDO = new CollectionsDO();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)throws SAXException
    {

        if(localName.equalsIgnoreCase("DSMName"))
        {
            collectionsDO.dsmName = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("DSMCode"))
        {
            collectionsDO.dsmCode = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("CustomerCode"))
        {
            collectionsDO.customerCode = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("CustomerName"))
        {
            collectionsDO.customerName = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("TotalOutstanding"))
        {
            collectionsDO.totalOutStanding = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("NoOfBillsOverdue"))
        {
            collectionsDO.noOfBillsOverdue = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("NoOfBillsOutstanding"))
        {
            collectionsDO.noOfBillsOutstanding = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("Distributor_CollectionDco"))
        {
            vecDashboards.add(collectionsDO);
        }
        else if(localName.equalsIgnoreCase("Distributor_CollectionDcos"))
        {
            if(vecDashboards != null && vecDashboards.size() > 0)
                insertCollectionData(vecDashboards);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)throws SAXException
    {
        currentValue.append(new String(ch, start, length));
    }
    private void insertCollectionData(Vector<CollectionsDO> vecDashboards)
    {
        new CollectionDA().insertCollection(vecDashboards);
    }

    @Override
    public Object getData() {
        return new Response(status,message,vecDashboards);
    }
}
