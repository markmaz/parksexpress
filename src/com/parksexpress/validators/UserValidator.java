/**
 * 
 */
package com.parksexpress.validators;

import org.apache.commons.validator.EmailValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.parksexpress.domain.User;

/**
 * @author mark
 * 
 */
public class UserValidator implements Validator {
	public UserValidator(){}
	
	@SuppressWarnings("unchecked")
	public boolean supports(final Class clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	public void validate(final Object obj, final Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName",
				"field.firstName.required", "First name can not be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName",
				"field.lastName.required", "Last name can not be empty.");
		
		if (!EmailValidator.getInstance().isValid(
				((User)obj).getEmailAddress())) {
			errors.rejectValue("emailAddress", "field.emailAddress.required",
					"The email address you entered is invalid.");
		}

	}
}