/*
 * RPGProgramException.java
 *
 * Created on May 31, 2002, 11:38 AM
 */

package com.parksexpress.as400.util;

/**
 *
 * @author  Administrator
 * @version 
 */
public class RPGProgramException extends Exception{
	private static final long serialVersionUID = 7204553767711902719L;
	private String message;
    
    /** Creates new RPGProgramException */
    public RPGProgramException() {
    }
    
    public RPGProgramException(String message){
        this.message = message;
    }
    
    public String getMessage(){
        return this.message;
    }

}