package com.parksexpress.jms.mdp;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.parksexpress.dao.ItemDAO;
import com.parksexpress.domain.item.Item;
import com.parksexpress.jms.sender.OrderGuideSender;

public class OrderGuideReceiver implements MessageListener{
	private ItemDAO itemDAO;
	
	public OrderGuideReceiver(){}
	
	public void onMessage(Message message) {
		if(message instanceof ObjectMessage){
			try {
				final OrderGuideMessage msg = (OrderGuideMessage)((ObjectMessage)message).getObject();
				final Item item = msg.getItem();
				final String chainCode = msg.getChainCode();
				
				switch(message.getIntProperty("messageType")){
					case OrderGuideSender.ADD:
						this.itemDAO.orderGuideItemAdd(chainCode, item);
						break;
					case OrderGuideSender.DELETE:
						this.itemDAO.orderGuideItemRemove(chainCode, item);
						break;
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

	public void setItemDAO(ItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}

}
