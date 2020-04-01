package com.parksexpress.services;

import org.apache.commons.httpclient.auth.InvalidCredentialsException;

import com.parksexpress.domain.User;


public interface AccountService {
	User isAuthorized(User user) throws InvalidCredentialsException;
}
