/*
 * Created on Jul 22, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.parksexpress.as400.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400Message;
import com.ibm.as400.access.AS400PackedDecimal;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.AS400Text;
import com.ibm.as400.access.ErrorCompletingRequestException;
import com.ibm.as400.access.ObjectDoesNotExistException;
import com.ibm.as400.access.ProgramCall;
import com.ibm.as400.access.ProgramParameter;

/**
 * @author markmaz
 *
 */
public class RpgSrp extends BaseAS400{
	public static final int WAREHOUSE = 0;
	public static final int DEPT = 1;
	public static final int STORE = 2;
	public static final int FILL =3;
	public static final int CUSTOMER = 4;
	public static final int ITEM = 5;
	public static final int DATE = 6;
	public static final int PRICE = 7;
	public static final int SRP = 8;
	public static final int FAMILY = 9;
	public static final String PRICE_NAME = "price";
	public static final String SRP_NAME = "srp";
	
	private String warehouse = "  1";
	private Logger log = Logger.getRootLogger();

	public RpgSrp(){}
	
	@SuppressWarnings("unchecked")
	public Map getSrpData(final String customer, final String item) throws Exception{
		final AS400 sys = this.getConnection();
		final ProgramParameter[] parmList = this.buildParameterList(customer, item);
		final ProgramCall pc = new ProgramCall(sys, this.getProgramName(), parmList);
		
		final Map map = this.createMapFromResults(parmList, pc);
		
		connectionPool.returnConnectionToPool(sys);
		return map;
	}

	
	@SuppressWarnings("unchecked")
	public Map getSrpData(final String customer, final String item, final String shipDate) throws Exception{
		final AS400 sys = this.getConnection();
		final ProgramParameter[] parmList = this.buildParameterList(customer, item, shipDate);
		final ProgramCall pc = new ProgramCall(sys, this.getProgramName(), parmList);
		
		final Map map = this.createMapFromResults(parmList, pc);
		
		connectionPool.returnConnectionToPool(sys);
		return map;
	}

	@SuppressWarnings("unchecked")
	private Map createMapFromResults(final ProgramParameter[] parmList, final ProgramCall pc) throws AS400SecurityException,
			ErrorCompletingRequestException, IOException, InterruptedException, ObjectDoesNotExistException {
		final Map map = new HashMap();
		final int priceDigits = 9;
		final int priceDecimal = 4;
		final int srpDigits = 7;
		final int srpDecimal = 2;
		
		if(pc.run()){
			final BigDecimal price = (BigDecimal) new AS400PackedDecimal(priceDigits, priceDecimal).
				toObject(parmList[RpgSrp.PRICE].getOutputData(), 0);
			map.put(RpgSrp.PRICE_NAME, price);
			
			final BigDecimal srp = (BigDecimal) new AS400PackedDecimal(srpDigits, srpDecimal).
				toObject(parmList[RpgSrp.SRP].getOutputData(), 0);
			map.put(RpgSrp.SRP_NAME, srp);
			
			final AS400Message[] msgs = pc.getMessageList();
			
			for(int i = 0; i < msgs.length; i++){
				this.log.debug(msgs[i].getText());
			}
			
		}else{
			this.log.fatal("Error with AS400 program");
		}
		return map;
	}
	
	private ProgramParameter[] buildParameterList(final String customer, final String item, final String shipDate) throws Exception{
		final int parmSize = 10;
		final ProgramParameter[] list = new ProgramParameter[parmSize];
		final AS400Text length3 = new AS400Text(3);
		final AS400Text length10 = new AS400Text(10);
		final AS400Text length5 = new AS400Text(5);
		final AS400PackedDecimal price = new AS400PackedDecimal(9, 4);
		final AS400PackedDecimal srp = new AS400PackedDecimal(7, 2);
		final AS400PackedDecimal date = new AS400PackedDecimal(8,0);
		
		String dateStart;
		
		try{
    		final SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
    		final SimpleDateFormat sdf2 = new SimpleDateFormat("MMddyyyy");
    		
    		dateStart = sdf2.format(sdf1.parse(shipDate));
		}catch(ParseException pe){
			dateStart = shipDate;
		}
		
		list[RpgSrp.WAREHOUSE] = new ProgramParameter(length3.toBytes(this.warehouse));
		list[RpgSrp.DEPT] = new ProgramParameter(length3.toBytes(this.warehouse));
		list[RpgSrp.STORE] = new ProgramParameter(length3.toBytes(this.warehouse));
		list[RpgSrp.FILL] = new ProgramParameter(length3.toBytes(this.warehouse));
		list[RpgSrp.CUSTOMER] = new ProgramParameter(length10.toBytes(Spacing.setCorrectSpacing(customer, Spacing.CUSTOMER)));
		list[RpgSrp.ITEM] = new ProgramParameter(length10.toBytes(Spacing.setCorrectSpacing(item, Spacing.ITEM)));
		list[RpgSrp.DATE] = new ProgramParameter(date.toBytes(new Double(dateStart).doubleValue()));
		list[RpgSrp.PRICE] = new ProgramParameter(price.getByteLength());
		list[RpgSrp.SRP] = new ProgramParameter(srp.getByteLength());
		list[RpgSrp.FAMILY] = new ProgramParameter(length5.getByteLength());
		
		return list;
	}

	private ProgramParameter[] buildParameterList(final String customer, final String item) throws Exception {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		final String today = sdf.format(Calendar.getInstance().getTime());
		
		return this.buildParameterList(customer, item, today);
	}

	public String getWarehouse() {
		return this.warehouse;
	}

	public void setWarehouse(final String warehouse) {
		this.warehouse = warehouse;
	}
}
