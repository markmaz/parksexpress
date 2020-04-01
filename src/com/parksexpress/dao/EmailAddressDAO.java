package com.parksexpress.dao;

import java.util.List;

import com.parksexpress.domain.EmailAddress;

public interface EmailAddressDAO {
	public EmailAddress getEmailAddress(int id);
	public List<EmailAddress> getAll();
	public void delete(int id);
	public void edit(int id, String emailAddress, String chainCode);
	public void add(String emailAddress, String chainCode);
	public List<String> getChainsInEmails();
	public List<EmailAddress> getEmailByChain(String chain);
}
