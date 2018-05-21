package com.winit.haldiram.parser;

import com.winit.common.webAccessLayer.Response;
import com.winit.haldiram.dataaccesslayer.StocksDA;
import com.winit.haldiram.dataobject.MessageDO;
import com.winit.haldiram.dataobject.StockDO;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.Vector;

/**
 * Created by Srikanth on 5/12/2017.
 */

public class InsertMessageParser extends BaseHandler{
    private StringBuilder currentValue ;
    private Vector<MessageDO> vecMessages ;
    private MessageDO messageDO;


    @Override
    public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException
    {
        currentValue = new StringBuilder();
        if(localName.equalsIgnoreCase("Distributor_ModelStockDcos"))
        {
            vecMessages = new Vector<MessageDO>();
        }
        else if(localName.equalsIgnoreCase("Distributor_ModelStockDco"))
        {
            messageDO = new MessageDO();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)throws SAXException
    {

//        if(localName.equalsIgnoreCase("ItemCode"))
//        {
//            stockDO.itemCode = currentValue.toString();
//        }
//        else if(localName.equalsIgnoreCase("ItemName"))
//        {
//            stockDO.itemName = currentValue.toString();
//        }
//        else if(localName.equalsIgnoreCase("ProductNorm"))
//        {
//            stockDO.productNorm = currentValue.toString();
//        }
//        else if(localName.equalsIgnoreCase("StockQty"))
//        {
//            stockDO.stockQty = currentValue.toString();
//        }
//        else if(localName.equalsIgnoreCase("NoOfDays"))
//        {
//            stockDO.noOfDays = currentValue.toString();
//        }
//        else if(localName.equalsIgnoreCase("Distributor_ModelStockDco"))
//        {
//            vecStocks.add(stockDO);
//        }
//        else if(localName.equalsIgnoreCase("Distributor_ModelStockDcos"))
//        {
//            if(vecStocks != null && vecStocks.size() > 0)
//                insertStocks(vecStocks);
//        }
    }

    @Override
    public void characters(char[] ch, int start, int length)throws SAXException
    {
        currentValue.append(new String(ch, start, length));
    }
    private void insertStocks(Vector<StockDO> vecStocks)
    {
        new StocksDA().insertModelStock(vecStocks);
    }

    @Override
    public Object getData() {
        return new Response(status,message,vecMessages);
    }
}
