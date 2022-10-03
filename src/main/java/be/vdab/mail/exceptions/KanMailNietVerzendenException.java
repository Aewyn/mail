package be.vdab.mail.exceptions;

public class KanMailNietVerzendenException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public KanMailNietVerzendenException(Exception message) {
        super(message);
    }
}
