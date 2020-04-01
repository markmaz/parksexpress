package com.parksexpress.jms.sender;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;

import com.parksexpress.domain.item.Item;
import com.parksexpress.jms.mdp.OrderGuideMessage;

public class OrderGuideSender {
	public static final int ADD = 0;
	public static final int DELETE = 1;
	public static final String ITEM = "item";
	public static final String CHAIN = "chainCode";

	private JmsTemplate jmsTemplate;
	private Queue queue;
	
	public OrderGuideSender(){}
	
	public void setConnectionFactory(ConnectionFactory cf){
		this.jmsTemplate = new JmsTemplate(cf);
		this.jmsTemplate.setDeliveryPersistent(true);
	}
	
	public void sendOrderGuideMessage(String chainCode, Item item, final int messageType){
		final OrderGuideMessage msg = new OrderGuideMessage(chainCode, item);
		
		this.jmsTemplate.convertAndSend(this.queue, msg, new MessagePostProcessor(){
			public Message postProcessMessage(Message msg) throws JMSException {
				msg.setIntProperty("messageType", messageType);
				return msg;
			}
		});
	}

	public void setQueue(Queue queue) {
		this.queue = queue;
	}
	
}
