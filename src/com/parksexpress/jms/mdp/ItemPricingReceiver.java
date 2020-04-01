package com.parksexpress.jms.mdp;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ibm.as400.access.AS400DateTimeConverter;
import com.parksexpress.as400.util.DateChanger;
import com.parksexpress.as400.util.FutureItemPricing;
import com.parksexpress.as400.util.ItemPricing;
import com.parksexpress.domain.item.Item;
import com.parksexpress.jms.sender.ItemPricingSender;
import com.parksexpress.jms.sender.messages.ItemPricingMessage;

public class ItemPricingReceiver implements MessageListener{
	private ItemPricing itemPricing;
	private FutureItemPricing futureItemPricing;
	
	private Logger log = Logger.getRootLogger();

	public ItemPricingReceiver(){}
	
	public void setFutureItemPricing(FutureItemPricing futureItemPricing) {
		this.futureItemPricing = futureItemPricing;
	}

	public void setItemPricing(ItemPricing itemPricing){
		this.itemPricing = itemPricing;
	}
	
	public void onMessage(Message message) {
		if(message instanceof ObjectMessage){
			ItemPricingMessage ipm;
			try {
				ipm = (ItemPricingMessage) ((ObjectMessage)message).getObject();
				final Item item = ipm.getItem();
				
				switch(message.getIntProperty("messageType")){
					case ItemPricingSender.ADD:
						this.log.debug("Calling rpg program ADD");
						
						if(StringUtils.isEmpty(item.getPricing().getEffectiveDate())){
							this.itemPricing.add(ipm.getPriceBook(), ipm.getItem());
						}else{
							this.futureItemPricing.add(ipm.getPriceBook(), item, 
									DateChanger.convertDateToAS400(item.getPricing().getEffectiveDate()));
						}
						
						break;
					case ItemPricingSender.DELETE:
						this.log.debug("Calling rpg program DELETE");
						if(item.getPricing() == null || StringUtils.isEmpty(item.getPricing().getEffectiveDate())){
							this.itemPricing.delete(ipm.getPriceBook(), ipm.getItem());
						}else{
							this.futureItemPricing.delete(ipm.getPriceBook(), item, 
									DateChanger.convertDateToAS400(item.getPricing().getEffectiveDate()));
						}
						
						break;
					case ItemPricingSender.UPDATE:
						this.log.debug("Calling rpg program UPDATE");
						if(StringUtils.isEmpty(item.getPricing().getEffectiveDate())){
							this.itemPricing.update(ipm.getPriceBook(), ipm.getItem());
						}else{
							this.futureItemPricing.update(ipm.getPriceBook(), item, 
									DateChanger.convertDateToAS400(item.getPricing().getEffectiveDate()));
						}
						
						break;
					default:
						throw new JMSException("Unknown Type");
				}
			} catch (JMSException e) {
				this.log.fatal("JMS Exception - " + e.getMessage());
			} catch (Exception e) {
				this.log.fatal("Normal Exception - " + e.getMessage());
			}
		}
	}
}
