package by.zakharyachnik.fitnessclub.validation;

public interface UserDataValidation {
    boolean checkRegistrationData(String login, String password, String fullName, String phoneNumber);
}
