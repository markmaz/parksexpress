package com.parksexpress.as400.util;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400PackedDecimal;
import com.ibm.as400.access.AS400Text;
import com.ibm.as400.access.ProgramCall;
import com.ibm.as400.access.ProgramParameter;
import com.parksexpress.domain.ShelfTag;

public class RpgShelfLableRequest extends BaseAS400 {
	public RpgShelfLableRequest(){}
	
	public void requestShelfLabel(final ShelfTag shelfTag) throws Exception{
		final AS400 sys = this.getConnection();
		final ProgramParameter[] parmList = this.buildParameterList(shelfTag);
		final ProgramCall pc = new ProgramCall(sys, this.getProgramName(), parmList);
		
		if(!pc.run()){
			throw new Exception("Program had an error - " + pc.getMessageList().toString());
		}
		
		connectionPool.returnConnectionToPool(sys);
	}

	private ProgramParameter[] buildParameterList(ShelfTag shelfTag) {
		final ProgramParameter[] parmList = new ProgramParameter[15];
		final AS400Text flag = new AS400Text(1);
		final AS400Text item = new AS400Text(10);
		final AS400PackedDecimal srpPack = new AS400PackedDecimal(4, 0);
		final AS400PackedDecimal srpDollar = new AS400PackedDecimal(7, 2);
		final AS400PackedDecimal srpPercent = new AS400PackedDecimal(7, 4);
		final AS400PackedDecimal effDate = new AS400PackedDecimal(8, 0);
		final AS400PackedDecimal endDateSRP = new AS400PackedDecimal(8, 0);
		
        final Float fPack = new Float(shelfTag.getPack());
        final Float fPrice = new Float(shelfTag.getPrice());
        final Float fPercent = new Float(shelfTag.getPercent());
        final Integer iEffDate = new Integer(shelfTag.getEffectiveDate());
        final Integer iEndDate = new Integer(shelfTag.getEndDate());
        
		parmList[0] = new ProgramParameter(flag.toBytes(shelfTag.getItemType()));
		parmList[1] = new ProgramParameter(item.toBytes(Spacing.setCorrectSpacing(shelfTag.getItemNumber(), Spacing.ITEM)));
		parmList[2] = new ProgramParameter(flag.toBytes(shelfTag.getCustomerType()));
		parmList[3] = new ProgramParameter(item.toBytes(Spacing.setCorrectSpacing(shelfTag.getStoreOrChainNumber(), Spacing.CUSTOMER)));
        parmList[4] = new ProgramParameter(srpPack.toBytes(fPack.floatValue()));
        parmList[5] = new ProgramParameter(srpDollar.toBytes(fPrice.floatValue()));
        parmList[6] = new ProgramParameter(srpPercent.toBytes(fPercent.floatValue()));
        parmList[7] = new ProgramParameter(flag.toBytes(shelfTag.getmUpt()));
        parmList[8] = new ProgramParameter(flag.toBytes(shelfTag.getmCode()));
        parmList[9] = new ProgramParameter(flag.toBytes(shelfTag.getRoundingCode()));
        parmList[10]= new ProgramParameter(effDate.toBytes(iEffDate.intValue()));
        parmList[11]= new ProgramParameter(endDateSRP.toBytes(iEndDate.intValue()));
        parmList[12]= new ProgramParameter(flag.toBytes(shelfTag.getLabelType()));
        parmList[13]= new ProgramParameter(flag.toBytes(shelfTag.getWeb()));
        parmList[14]= new ProgramParameter(item.toBytes(shelfTag.getUser()));
        
        return parmList;
	}
}
