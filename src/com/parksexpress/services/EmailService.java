package com.parksexpress.services;

import java.util.List;

import com.parksexpress.domain.EmailAddress;

public interface EmailService {
	public void add(String emailAddress, String chainCode);
	public void edit(int id, String emailAddress, String chainCode);
	public void delete(int id);
	public List<EmailAddress> getAll();
	public EmailAddress getEmailAddress(int id);
	public void sendAll() throws Exception;
	public List<EmailAddress> getEmailsByChain(String chainCode);
}
