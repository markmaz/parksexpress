package com.parksexpress.domain;

import java.io.Serializable;
import java.text.DecimalFormat;

public class RoundingCode implements Serializable {
	private static final long serialVersionUID = -7911179848240867245L;
	private float[] codes;
	private String code;

	public RoundingCode() {
		this.codes = new float[]{-1, -2, -3, -4, -5, 4, 3, 2, 1, 0};
	}

	public float[] getCodes() {
		return this.codes;
	}

	public void setCodes(final float[] codes) {
		this.codes = codes;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(final String code) {
		this.code = code;
	}
	
    public String getRoundedPrice(final float price){
        final DecimalFormat format = new DecimalFormat("#########.##");
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);
                
        final String tDigit = format.format(price);
        final String digit = tDigit.substring(tDigit.length() - 1);
        
        final int index = new Integer(digit).intValue();
        
        final int percentage = 100;
        final float cents = this.codes[index] / percentage;
        
        return format.format(price + cents);              
    }        
    
    public String getRoundingCode(){
    	return this.code;
    }	
}
