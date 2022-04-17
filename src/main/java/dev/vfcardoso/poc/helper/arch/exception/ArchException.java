package dev.vfcardoso.poc.helper.arch.exception;

/**
 * Project: arch
 * User: Vinicius Cardoso
 * Date: 28/03/14
 * Time: 15:07
 */
public abstract class ArchException extends RuntimeException {
    protected ArchException(String message) {
        super(message);
    }

    protected ArchException(String message, Throwable cause) {
        super(message, cause);
    }
}