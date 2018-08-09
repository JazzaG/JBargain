package com.somethinglurks.jbargain.api.user.exception;

/**
 * Thrown if something goes wrong attempting to authenticate a user
 */
public class AuthenticationException extends Exception {

    public AuthenticationException(String message) {
        super(message);
    }
}
