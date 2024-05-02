package by.zakharyachnik.fitnessclub.validation;

import by.zakharyachnik.fitnessclub.validation.impl.UserDataValidationImpl;

public class ValidationProvider {
    private static ValidationProvider provider;

    private final UserDataValidation userDataValidation = new UserDataValidationImpl();

    private ValidationProvider() {
    }

    public static ValidationProvider getInstance(){
        if(provider == null){
            provider = new ValidationProvider();
        }
        return provider;
    }

    public UserDataValidation getUserDataValidation() {
        return userDataValidation;
    }
}
