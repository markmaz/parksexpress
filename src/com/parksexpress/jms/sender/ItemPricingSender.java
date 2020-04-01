package com.parksexpress.jms.sender;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;

import com.parksexpress.domain.item.Item;
import com.parksexpress.jms.sender.messages.ItemPricingMessage;

public class ItemPricingSender {
	public static final int ADD = 0;
	public static final int DELETE = 1;
	public static final int UPDATE = 2;
	private JmsTemplate jmsTemplate;
	private Queue queue;
	
	public ItemPricingSender(){}
	
	public void setConnectionFactory(ConnectionFactory cf){
		this.jmsTemplate = new JmsTemplate(cf);
		this.jmsTemplate.setDeliveryPersistent(true);
	}
	
	public void setDestinationQueue(Queue queue){
		this.queue = queue;
	}
	
	public void sendItemPricingMessage(Item item, String book, final int messageType){
		final ItemPricingMessage ipm = new ItemPricingMessage(item, book);
		this.jmsTemplate.convertAndSend(this.queue, ipm, new MessagePostProcessor(){
			public Message postProcessMessage(Message msg) throws JMSException {
				msg.setIntProperty("messageType", messageType);
				return msg;
			}
		});
	}
}
