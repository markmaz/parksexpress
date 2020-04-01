package com.parksexpress.jms.mdp;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

import com.parksexpress.dao.PriceBookDAO;
import com.parksexpress.domain.PriceBookFamily;
import com.parksexpress.jms.sender.FamilyPricingSender;
import com.parksexpress.jms.sender.messages.FamilyPricingMessage;

public class FamilyPricingReceiver implements MessageListener {
	private PriceBookDAO priceBookDAO;
	private Logger log = Logger.getRootLogger();

	public FamilyPricingReceiver() {
	}

	public void setPriceBookDAO(PriceBookDAO priceBookDAO) {
		this.priceBookDAO = priceBookDAO;
	}

	public void onMessage(Message message) {
		if (message instanceof ObjectMessage) {
			try {
				final FamilyPricingMessage msg = (FamilyPricingMessage) ((ObjectMessage) message).getObject();
				final PriceBookFamily family = msg.getFamily();
				final String srpBook = msg.getSrpBook();
				final int messageType = message.getIntProperty("messageType");
				final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				final String today = sdf.format(Calendar.getInstance().getTime());

				switch (messageType) {
				case FamilyPricingSender.ADD:
					if (today.equalsIgnoreCase(family.getPricing().getEffectiveDate())) {
						this.log.debug("Normal pricing update");
						this.priceBookDAO.addFamilyPricing(family, srpBook);
					} else {
						this.log.debug("Future pricing update");
						this.priceBookDAO.updateFutureFamilyPricing(family, srpBook);
					}

					break;
				case FamilyPricingSender.DELETE:
					this.priceBookDAO.deleteClassFamilyPricing(family.getFamilyCode(), srpBook);
					break;
				case FamilyPricingSender.UPDATE:
					if (today.equalsIgnoreCase(family.getPricing().getEffectiveDate())) {
						this.priceBookDAO.addFamilyPricing(family, srpBook);
					} else {
						this.priceBookDAO.updateFutureFamilyPricing(family, srpBook);
					}
					break;
				default:
					throw new JMSException("Unknown Type");
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
}