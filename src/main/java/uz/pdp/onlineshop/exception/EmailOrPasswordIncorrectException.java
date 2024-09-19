package uz.pdp.onlineshop.exception;

public class EmailOrPasswordIncorrectException extends RuntimeException{
    public EmailOrPasswordIncorrectException() {
        super("Email or password is incorrect");
    }
}
