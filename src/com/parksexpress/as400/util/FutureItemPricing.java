package com.parksexpress.as400.util;

import java.io.IOException;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400PackedDecimal;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.AS400Text;
import com.ibm.as400.access.ErrorCompletingRequestException;
import com.ibm.as400.access.ObjectDoesNotExistException;
import com.ibm.as400.access.ProgramCall;
import com.ibm.as400.access.ProgramParameter;
import com.parksexpress.domain.item.Item;

public class FutureItemPricing extends BaseAS400 {
	public static final int PARAMETERS = 9;

	public static final int USER = 0;

	public static final int SRP = 1;

	public static final int ITEM = 2;

	public static final int PERCENT = 3;

	public static final int PRICE = 4;

	public static final int START_DATE = 5;

	public static final int END_DATE = 6;

	public static final int CHANGE = 7;

	public static final int STATUS = 8;
	
	private String endDate;

	public FutureItemPricing() {
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public boolean update(final String priceBookNumber, final Item item, 
			final String effectiveDate) throws Exception {
		return this.execute(this.getUsername(), priceBookNumber, item.getNumber(),
				item.getPricing().getPercent().toString(), item.getPricing().getPrice().toString(), "U", effectiveDate);
	}

	public boolean delete(String priceBookNumber, Item item, String effectiveDate) throws Exception {
		return this.execute(this.getUsername(), priceBookNumber, item.getNumber(),
				item.getPricing().getPercent().toString(), item.getPricing().getPrice().toString(), "D", effectiveDate);
	}

	public boolean add(String priceBookNumber, Item item, String effectiveDate) throws Exception {
		return this.execute(this.getUsername(), priceBookNumber, item.getNumber(),
				item.getPricing().getPercent().toString(), item.getPricing().getPrice().toString(), "A", effectiveDate);
	}

	public boolean execute(final String userid, final String srpid, final String itemNumber, 
			final String grossPercent, final String price, final String flag, 
			final String effectiveDate) throws Exception {
		final AS400 sys = this.getConnection();

		try {
			final AS400Text user = new AS400Text(25);
			final AS400Text srp = new AS400Text(5);
			final AS400Text item = new AS400Text(10);
			final AS400Text change = new AS400Text(1);
			final AS400Text status = new AS400Text(1);
			final AS400PackedDecimal percent = new AS400PackedDecimal(7, 4);
			final AS400PackedDecimal srpPrice = new AS400PackedDecimal(7, 2);
			final AS400Text startDate = new AS400Text(8);
			final AS400Text end = new AS400Text(8);

			final Float gp = new Float(grossPercent);
			final Float p = new Float(price);

			final ProgramParameter[] parms = new ProgramParameter[FutureItemPricing.PARAMETERS];
			parms[FutureItemPricing.USER] = new ProgramParameter(user.toBytes(userid));
			parms[FutureItemPricing.SRP] = new ProgramParameter(srp.toBytes(Spacing.setCorrectSpacing(srpid, Spacing.FAMILY)));
			parms[FutureItemPricing.ITEM] = new ProgramParameter(item.toBytes(Spacing.setCorrectSpacing(itemNumber, Spacing.ITEM)));
			parms[FutureItemPricing.PERCENT] = new ProgramParameter(percent.toBytes(gp.floatValue()));
			parms[FutureItemPricing.PRICE] = new ProgramParameter(srpPrice.toBytes(p.floatValue()));
			parms[FutureItemPricing.START_DATE] = new ProgramParameter(startDate.toBytes(effectiveDate));
			parms[FutureItemPricing.END_DATE] = new ProgramParameter(end.toBytes(this.endDate));

			parms[FutureItemPricing.CHANGE] = new ProgramParameter(change.toBytes(flag));
			parms[FutureItemPricing.STATUS] = new ProgramParameter(status.getByteLength());
 
			final ProgramCall pc = new ProgramCall(sys, this.getProgramName(), parms);

			if (pc.run()) {
				final byte[] data = parms[FutureItemPricing.STATUS].getOutputData();
				String retVal = (String) status.toObject(data);
 
				if (retVal.equals("Y")) {
					return true;
				}else{
					if(flag.equalsIgnoreCase("U")){
						parms[FutureItemPricing.CHANGE] = new ProgramParameter(change.toBytes("A"));
						final ProgramCall pc2 = new ProgramCall(sys, this.getProgramName(), parms);
						if(pc2.run()){
							retVal = (String) status.toObject(data);
						}
						
						if(retVal.equalsIgnoreCase("Y")){
							return true;
						}else{
							return false;
						}
				}
			}
			
			} 
			
			return false;
		} catch (AS400SecurityException ase) {
			throw new RPGProgramException(ase.getMessage());
		} catch (ErrorCompletingRequestException ecre) {
			throw new RPGProgramException(ecre.getMessage());
		} catch (InterruptedException ie) {
			throw new RPGProgramException(ie.getMessage());
		} catch (IOException ioe) {
			throw new RPGProgramException(ioe.getMessage());
		} catch (ObjectDoesNotExistException odne) {
			throw new RPGProgramException(odne.getMessage());
		} finally {
			connectionPool.returnConnectionToPool(sys);
		}
	}
}
