package by.zakharyachnik.fitnessclub.validation.impl;

public class RegistrationValidator  {
    private static RegistrationValidator validator;
    //логин должен состоять только из букв и цифр и иметь длину от 3 до 20 символов
    private static final String LOGIN_REGEX = "^[a-zA-Z0-9]{3,20}$";
    //пароль содержит хотябы одну букву и цифру и длиной больше 4
    private static final String PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*\\d).{5,}$";
    //состоит из 3 и более слов, начинается с заглавной и состоит только из букв
    private static final String FULL_NAME_REGEX = "^[А-ЯЁA-Z][а-яёa-z]+( [А-ЯЁA-Z][а-яёa-z]+){2,}$";
    private static final String PHONE_NUMBER_REGEX = "^(\\+375|375)(29|25|44|33)[0-9]{7}$";



    private RegistrationValidator() {
    }

    public static RegistrationValidator getValidator(){
        if(validator == null){
            validator = new RegistrationValidator();
        }
        return validator;
    }

    public boolean isValidLogin(String login) {
        return login.matches(LOGIN_REGEX);
    }

    public boolean isValidPassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    public boolean isValidFullName(String fullName) {
        return fullName.matches(FULL_NAME_REGEX);
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches(PHONE_NUMBER_REGEX);
    }
}
