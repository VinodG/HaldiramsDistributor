package com.winit.haldiram.parser;

import com.winit.common.Preference;
import com.winit.common.util.LogUtils;
import com.winit.common.util.SyncData;
import com.winit.common.webAccessLayer.Response;
import com.winit.haldiram.dataobject.SyncLogDO;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Girish Velivel on 12/13/2016.
 */
public class AllDataSyncParser extends BaseHandler
{
	private String LOG_TAG = "AllDataSyncParser";

	private final static float  TOTAL_MAIN_MODULES=43.0f;
	private int  SYNCED_MAIN_MODULES=0;
	private Preference preference;
	private SyncData.SyncProcessListner syncProcessListner=null;
	private String modifiedDate, modifiedTime;
	private boolean isDistributorSyncData;

	private BaseHandler baseHandler;

	@Override
	public Object getData() {
		return new Response(status,message,new SyncLogDO(modifiedDate,modifiedTime));
	}


	enum ParsingTag{

		LST_USER("UserDcos"),
		ACHIEVEMENT("Distributor_AchievementDcos"),
		COLLECTION("Distributor_CollectionDcos"),
		ECO_DETAIL("Distributor_ECODetailDcos"),
		INVESTMENT_ANALYSIS("Distributor_InvestmentAnalysisDcos"),
		MODEL_STOCK("Distributor_ModelStockDcos"),
		OUT_OF_STOCK("Distributor_OutOfStockDcos"),
		OVER_STOCK("Distributor_OverStockDcos"),
//		DISTRIBUTOR_DATA_SYNC_RESPONSE("DistributorDataSyncResponse"),
		YEAR_WISE_SALE("Distributor_OverStockDcos");

		private String value;
		private static final Map<String, ParsingTag> hmParsingTags = new LinkedHashMap<>();
		static {
			for(ParsingTag parsingTag : ParsingTag.values()){
				if(LogUtils.isLogEnable && hmParsingTags.containsKey(parsingTag.value)){
//					Toast.makeText(HaldiramsDistributorApplication.mContext,"Duplicate Tag "+parsingTag.value, Toast.LENGTH_LONG).show();
				}
				hmParsingTags.put(parsingTag.value,parsingTag);
			}
		}
		ParsingTag(String value) {
			this.value = value;
		}
		String getValue() {
			return value;
		}
		public static ParsingTag getParsingTag(String value) {
			return hmParsingTags.get(value);
		}
	}

	public AllDataSyncParser(SyncData.SyncProcessListner syncProcessListner) {
		this.syncProcessListner=syncProcessListner;
		preference = Preference.getInstance();
	}

	private BaseHandler getBaseHandler(ParsingTag parsingTag){
		LogUtils.debug(LOG_TAG, "getBaseHandler - startElement " + parsingTag);
		switch (parsingTag){
			case LST_USER:
				return new GetUsersParser();
			case ACHIEVEMENT:
				return new AchievementParser();
			case COLLECTION:
				return new CollectionParser();
			case ECO_DETAIL:
				return new ECODetailParser();
//			case INVESTMENT_ANALYSIS:
//				return new UserParser();
			case MODEL_STOCK:
				return new ModelStockParser();
			case OUT_OF_STOCK:
				return new OutOfStockParser();
			case OVER_STOCK:
				return new OverStockParser();
//			case YEAR_WISE_SALE:
//				return new UserParser();
		}
		return null;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		currentValue = new StringBuilder();
		if(localName.equalsIgnoreCase("DistributorSyncDataDco"))
			isDistributorSyncData = true;
		else if(isDistributorSyncData) {
			ParsingTag parsingTag = ParsingTag.getParsingTag(localName);
			if (parsingTag != null) {
				baseHandler = getBaseHandler(parsingTag);
			}
			if (baseHandler != null)
				baseHandler.startElement(uri, localName, qName, attributes);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)throws SAXException {
		if(localName.equalsIgnoreCase("Status"))
			status = currentValue.toString();
		else if(localName.equalsIgnoreCase("Message"))
			message = currentValue.toString();
		else if(localName.equalsIgnoreCase("ModifiedDate"))
			modifiedDate = currentValue.toString();
		else if(localName.equalsIgnoreCase("ModifiedTime"))
			modifiedTime = currentValue.toString();
		else if (baseHandler != null) {
			baseHandler.endElement(uri, localName, qName);
			ParsingTag parsingTag = ParsingTag.getParsingTag(localName);
			if (parsingTag != null) {
				LogUtils.debug(LOG_TAG,"endElement "+ localName);
				baseHandler = null;
			}
		}
	}


	private void updateMessage(String localName)
	{
		int percentage=(int) ((SYNCED_MAIN_MODULES/TOTAL_MAIN_MODULES)*100);
		if(percentage<=100){
			String msg="Syncing "+localName+" "+percentage+"%";
			syncProcessListner.setProgress(msg);
		}
	}

	public void characters(char[] ch, int start, int length)
	{
		try
		{
			if (baseHandler != null)
				baseHandler.characters(ch,start,length);
			else
				currentValue.append(ch, start, length);
		}
		catch (Exception e)
		{
			LogUtils.errorLog(this.getClass().getName(), "XML ch[] appending exception:"+e.getMessage() );
		}
	}
}