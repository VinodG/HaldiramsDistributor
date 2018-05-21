package com.winit.haldiram.parser;

import com.winit.common.webAccessLayer.Response;
import com.winit.haldiram.dataaccesslayer.InvestmentAnalysisDA;
import com.winit.haldiram.dataobject.InvestmentAnalysisDO;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.Vector;

/**
 * Created by Ashoka.Reddy on 5/12/2017.
 */

public class InvestmentAnalysis extends BaseHandler {
    private StringBuilder currentValue ;
    private Vector<InvestmentAnalysisDO> vecDashboards ;
    private InvestmentAnalysisDO investmentAnalysisDO;


    @Override
    public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException
    {
        currentValue = new StringBuilder();
        if(localName.equalsIgnoreCase("Distributor_InvestmentAnalysisDcos"))
        {
            vecDashboards = new Vector<InvestmentAnalysisDO>();
        }
        else if(localName.equalsIgnoreCase("Distributor_InvestmentAnalysisDco"))
        {
            investmentAnalysisDO = new InvestmentAnalysisDO();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)throws SAXException
    {

        if(localName.equalsIgnoreCase("SOH"))
        {
            investmentAnalysisDO.soh = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("SIT"))
        {
            investmentAnalysisDO.sit = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("CashWithCompany"))
        {
            investmentAnalysisDO.cashwithCompany = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("SOHDays"))
        {
            investmentAnalysisDO.sohDays = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("SITDays"))
        {
            investmentAnalysisDO.sitDays = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("CashWithCompanyDays"))
        {
            investmentAnalysisDO.cashwithCompanyDays = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("MarketOutstanding"))
        {
            investmentAnalysisDO.marketOutstanding = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("ProjectSales"))
        {
            investmentAnalysisDO.projectSales = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("ROWC"))
        {
            investmentAnalysisDO.rowc = currentValue.toString();
        }
        else if(localName.equalsIgnoreCase("Distributor_InvestmentAnalysisDco"))
        {
            vecDashboards.add(investmentAnalysisDO);
        }
        else if(localName.equalsIgnoreCase("Distributor_InvestmentAnalysisDcos"))
        {
            if(vecDashboards != null && vecDashboards.size() > 0)
                insertInvestmentAnalysisData(vecDashboards);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)throws SAXException
    {
        currentValue.append(new String(ch, start, length));
    }
    private void insertInvestmentAnalysisData(Vector<InvestmentAnalysisDO> vecDashboards)
    {
        new InvestmentAnalysisDA().InvestmentAnalysis(vecDashboards);
    }

    @Override
    public Object getData() {
        return new Response(status,message,vecDashboards);
    }
}
