package by.zakharyachnik.fitnessclub.validation.impl;


import by.zakharyachnik.fitnessclub.validation.UserDataValidation;
import org.springframework.stereotype.Component;

@Component
public class UserDataValidationImpl implements UserDataValidation {
    @Override
    public boolean checkRegistrationData(String login, String password, String fullName, String phoneNumber) {
        RegistrationValidator validator = RegistrationValidator.getValidator();
        return validator.isValidLogin(login) && validator.isValidPassword(password) && validator.isValidFullName(fullName) && validator.isValidPhoneNumber(phoneNumber);
    }
}
