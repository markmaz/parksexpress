package com.parksexpress.services.impl;

import org.apache.commons.httpclient.auth.InvalidCredentialsException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.parksexpress.dao.UserDAO;
import com.parksexpress.domain.User;
import com.parksexpress.services.AccountService;

public class AccountServiceImpl implements AccountService {
	private static Log logger = LogFactory.getLog(AccountServiceImpl.class);
    private UserDAO userDAO;

    public AccountServiceImpl(){}
    
    public AccountServiceImpl(final UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User isAuthorized(User user) throws InvalidCredentialsException {
        AccountServiceImpl.logger.info("Validating user: " + user.toString());

        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
        	AccountServiceImpl.logger.error("Forgot to set the username or password somewhere");
            throw new InvalidCredentialsException("Username or Password is invalid.");
        }

        return this.userDAO.isAuthorized(user.getUsername(), user.getPassword());
    }
}
