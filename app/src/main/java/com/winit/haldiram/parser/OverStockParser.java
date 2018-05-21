package com.winit.haldiram.parser;

import com.winit.common.webAccessLayer.Response;
import com.winit.haldiram.dataaccesslayer.StocksDA;
import com.winit.haldiram.dataobject.StockDO;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.Vector;

/**
 * Created by Srikanth on 5/12/2017.
 */

public class OverStockParser extends BaseHandler{
    private StringBuilder currentValue ;
    private Vector<StockDO> vecStocks ;
    private StockDO stockDO;


    @Override
    public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException
    {
        currentValue = new StringBuilder();
        if(localName.equalsIgnoreCase("Distributor_OverStockDcos"))
        {
            vecStocks = new Vector<StockDO>();
        }
        else if(localName.equalsIgnoreCase("Distributor_OverStockDco"))
        {
            stockDO = new StockDO();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)throws SAXException
    {

        if(localName.equalsIgnoreCase("ItemCode"))
        {
            stockDO.itemCode = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("ItemName"))
        {
            stockDO.itemName = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("ProductNorm"))
        {
            stockDO.productNorm = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("StockQty"))
        {
            stockDO.stockQty = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("NoOfDays"))
        {
            stockDO.noOfDays = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("Distributor_OverStockDco"))
        {
            vecStocks.add(stockDO);
        }
        else if(localName.equalsIgnoreCase("Distributor_OverStockDcos"))
        {
            if(vecStocks != null && vecStocks.size() > 0)
                insertAchievedData(vecStocks);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)throws SAXException
    {
        currentValue.append(new String(ch, start, length));
    }
    private void insertAchievedData(Vector<StockDO> vecStocks)
    {
        new StocksDA().insertOverStock(vecStocks);
    }

    @Override
    public Object getData() {
        return new Response(status,message,vecStocks);
    }
}
