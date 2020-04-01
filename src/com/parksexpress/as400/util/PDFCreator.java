package com.parksexpress.as400.util;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400Text;
import com.ibm.as400.access.ProgramCall;
import com.ibm.as400.access.ProgramParameter;

public class PDFCreator extends BaseAS400 implements PDFGenerator {
	private String warehouse;
	
	public PDFCreator() {
	}

	/* (non-Javadoc)
	 * @see com.parksexpress.as400.util.PDFGenerator#createPDF(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void createPDF(String invoiceNumber, String customerNumber, String orderNumber) throws Exception {
		final AS400 sys = this.getConnection();
		final ProgramCall pc = new ProgramCall(sys, this.getProgramName(), this.buildParameterList(invoiceNumber, customerNumber,
				orderNumber));

		if (!pc.run()) {
			throw new Exception("Error running " + this.getProgramName());
		}

		connectionPool.returnConnectionToPool(sys);
	}

	private ProgramParameter[] buildParameterList(String invoiceNumber, String customerNumber, String orderNumber) {
		final ProgramParameter[] list = new ProgramParameter[6];
		final AS400Text length3 = new AS400Text(3);
		final AS400Text length7 = new AS400Text(7);
		final AS400Text length10 = new AS400Text(10);

		list[0] = new ProgramParameter(length3.toBytes(this.warehouse));
		list[1] = new ProgramParameter(length3.toBytes(this.warehouse));
		list[2] = new ProgramParameter(length3.toBytes(this.warehouse));
		list[3] = new ProgramParameter(length10.toBytes(Spacing.setCorrectSpacing(customerNumber, Spacing.CUSTOMER)));
		list[4] = new ProgramParameter(length7.toBytes(this.prefix(Spacing.INVOICE_NUMBER, invoiceNumber, "0")));
		list[5] = new ProgramParameter(length7.toBytes(this.prefix(Spacing.ORDER_NUMBER, orderNumber, "0")));

		return list;
	}
	
	public void setWarehouse(String warehouse){
		this.warehouse = warehouse;
	}
	
	private String prefix(int size, String value, String insertValue){
		final StringBuffer temp = new StringBuffer();
		
		for(int i = value.length(); i < size; i++){
			temp.append(insertValue);
		}
		
		temp.append(value.trim());
		return temp.toString();
	}
}
