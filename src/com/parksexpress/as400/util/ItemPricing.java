package com.parksexpress.as400.util;

import java.io.IOException;

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
import com.parksexpress.domain.item.Item;

public class ItemPricing extends BaseAS400 {
	private Logger log = Logger.getRootLogger();
	
	public ItemPricing() {
	}

	public boolean update(final String priceBookNumber, final Item item) throws Exception {
		return this.execute(this.getUsername(), priceBookNumber, item.getNumber(), 
				item.getPricing().getPercent().toString(), item.getPricing().getPrice().toString(), "U");
	}

	public boolean delete(final String priceBookNumber, final Item item) throws Exception {
		return this.execute(this.getUsername(), priceBookNumber, item.getNumber(), 
				item.getPricing().getPercent().toString(), item.getPricing().getPrice().toString(), "D");
	}

	public boolean add(final String priceBookNumber, final Item item) throws Exception {
		return this.execute(this.getUsername(), priceBookNumber, item.getNumber(), 
				item.getPricing().getPercent().toString(), item.getPricing().getPrice().toString(), "A");
	}

	public boolean execute(final String userid, final String srpid, final String itemNumber, 
			final String grossPercent, String price, String flag) throws Exception {
		final AS400 sys = this.getConnection();

		final String srpBook = Spacing.setCorrectSpacing(srpid, Spacing.FAMILY);
		final String correctItemNumber = Spacing.setCorrectSpacing(itemNumber, Spacing.ITEM);

		try {
			final AS400Text user = new AS400Text(25);
			final AS400Text srp = new AS400Text(5);
			final AS400Text item = new AS400Text(10);
			final AS400Text change = new AS400Text(1);
			final AS400Text status = new AS400Text(1);
			final AS400PackedDecimal percent = new AS400PackedDecimal(7, 4);
			final AS400PackedDecimal srpPrice = new AS400PackedDecimal(7, 2);

			final Float gp = new Float(grossPercent);
			final Float p = new Float(price);

			final int numberOfParameters = 7;
			final int parmUser = 0;
			final int parmSRP = 1;
			final int parmItem = 2;
			final int parmPercent = 3;
			final int parmPrice = 4;
			final int parmChange = 5;
			final int parmStatus = 6;
			
			final ProgramParameter[] parms = new ProgramParameter[numberOfParameters];
			parms[parmUser] = new ProgramParameter(user.toBytes(userid));
			parms[parmSRP] = new ProgramParameter(srp.toBytes(srpBook));
			parms[parmItem] = new ProgramParameter(item.toBytes(correctItemNumber));
			parms[parmPercent] = new ProgramParameter(percent.toBytes(gp.floatValue()));
			parms[parmPrice] = new ProgramParameter(srpPrice.toBytes(p.floatValue()));
			parms[parmChange] = new ProgramParameter(change.toBytes(flag));
			parms[parmStatus] = new ProgramParameter(status.getByteLength());

			final ProgramCall pc = new ProgramCall(sys, this.getProgramName(), parms);

			if (pc.run()) {
				final byte[] data = parms[parmStatus].getOutputData();
				String retVal = (String) status.toObject(data);
				
				final AS400Message[] msg = pc.getMessageList();
				
				for(int i = 0; i < msg.length; i++){
					this.log.debug(msg[i].getText());
				}
				
				if (retVal.equals("Y")) {
					return true;
				}else{
					if(flag.equalsIgnoreCase("A")){
						parms[parmChange] = new ProgramParameter(change.toBytes("U"));
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
