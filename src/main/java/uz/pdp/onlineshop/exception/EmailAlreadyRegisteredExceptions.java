package uz.pdp.onlineshop.exception;

public class EmailAlreadyRegisteredExceptions extends RuntimeException {
    public EmailAlreadyRegisteredExceptions(String email) {
        super("Email " + email +" already registered");
    }
}
