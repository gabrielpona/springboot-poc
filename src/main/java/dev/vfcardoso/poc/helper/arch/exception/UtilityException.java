package dev.vfcardoso.poc.helper.arch.exception;

/**
 * Project: arch
 * User: Vinicius Cardoso
 * Date: 28/03/14
 * Time: 16:11
 */
public class UtilityException extends ArchException {
    public UtilityException(String message) {
        super(message);
    }

    public UtilityException(String message, Throwable cause) {
        super(message, cause);
    }
}
