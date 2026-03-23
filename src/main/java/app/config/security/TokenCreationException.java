package app.config.security;

public class TokenCreationException extends Exception {
    public TokenCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}