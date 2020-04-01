package com.parksexpress.services.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.parksexpress.dao.EmailAddressDAO;
import com.parksexpress.domain.EmailAddress;
import com.parksexpress.domain.item.HistoryItem;
import com.parksexpress.services.EmailService;
import com.parksexpress.services.TransactionHistoryService;
import com.parksexpress.util.DateUtil;

public class EmailServiceImpl implements EmailService {
	private EmailAddressDAO emailDAO;
	private JavaMailSenderImpl mailSender;
	private SimpleMailMessage templateMessage;
	private TransactionHistoryService historyService;
	private static Log logger = LogFactory.getLog(EmailServiceImpl.class);
	
	public void setTransactionHistoryService(
			TransactionHistoryService historyService) {
		this.historyService = historyService;
	}

	public void setEmailAddressDAO(EmailAddressDAO emailDAO) {
		this.emailDAO = emailDAO;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void setSimpleMailMessage(SimpleMailMessage templateMessage) {
		this.templateMessage = templateMessage;
	}

	@Override
	public void add(String emailAddress, String chainCode) {
		this.emailDAO.add(emailAddress, chainCode);
	}

	@Override
	public void edit(int id, String emailAddress, String chainCode) {
		this.emailDAO.edit(id, emailAddress, chainCode);
	}

	@Override
	public void delete(int id) {
		this.emailDAO.delete(id);
	}

	@Override
	public List<EmailAddress> getAll() {
		return this.emailDAO.getAll();
	}

	@Override
	public EmailAddress getEmailAddress(int id) {
		return this.emailDAO.getEmailAddress(id);
	}

	@Override
	public void sendAll() throws Exception {
		List<String> chains = this.emailDAO.getChainsInEmails();

		for (String chain : chains) {
			String message = getMessageForChain(chain);
			logger.info("Checking message for: " + chain);
			
			if (StringUtils.isNotEmpty(message)) {
				List<EmailAddress> addresses = this.emailDAO.getEmailByChain(chain);

				for (EmailAddress email : addresses) {
					logger.info("Sending email to: " + email.getEmailAddress());
					MimeMessage mimeMsg = mailSender.createMimeMessage();
					MimeMessageHelper helper = new MimeMessageHelper(mimeMsg);
					helper.setTo(email.getEmailAddress());
					helper.setSubject("Retail Changes - " + DateUtil.today());
					helper.setText(message, true);
					
					mailSender.send(mimeMsg);
				}
			}
		}
	}

	public String getMessageForChain(String chain) {
		StringBuffer message = new StringBuffer();
		List<HistoryItem> items = new ArrayList<HistoryItem>();

		try {
			items = historyService.getTransactionHistory(
					DateUtil.convertToMySQLDate(DateUtil.today()), chain);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (items.size() > 0) {
			message = new StringBuffer(
					"<html><head><title>Parks Express</title></head>");
			message.append("<body><table width=\"100%\"><tr><td>The following changes were made today using <font color=\"red\" face=\"Tahoma\"><b>Parks Express</b></font>.</td>");
			message.append("<tr><tr><td>&nbsp;</td><tr><tr><td>");
			message.append("<table width=\"100%\" style=\"left: 0px; font-size: small\">");
			message.append("<tr><th width=\"20%\" align=\"left\">Customer</th><th width=\"10%\" align=\"left\">Date</th><th width=\"70%\" align=\"left\">Details</th></tr>");
			

			for (int i = 0; i < items.size(); i++) {
				HistoryItem item = items.get(i);

				if (i % 2 == 0) {
					message.append("<tr style=\"background-color: #ececec;\">");
				} else {
					message.append("<tr style=\"background-color: #ffffff;\">");
				}

				message.append("<td><span style=\"left: 0px; font-size: small\">"
						+ item.getUser().getFirstName()
						+ "&nbsp;"
						+ item.getUser().getLastName() + "</span></td>");
				message.append("<td><span style=\"left: 0px; font-size: small\">"
						+ item.getTransactionDate() + "</span></td>");
				message.append("<td><span style=\"left: 0px; font-size: small\">"
						+ item.getDetails() + "</span></td>");
				message.append("</tr>");
			}

			message.append("</table></td></tr></table></body></html>");

		}
		return message.toString();

	}

	@Override
	public List<EmailAddress> getEmailsByChain(String chainCode) {
		return this.emailDAO.getEmailByChain(chainCode);
	}
}
