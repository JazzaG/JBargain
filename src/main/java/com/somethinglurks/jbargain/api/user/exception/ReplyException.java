package com.somethinglurks.jbargain.api.user.exception;

/**
 * Thrown if something goes wrong while submitting a reply
 */
public class ReplyException extends Exception {

    public ReplyException(String message) {
        super(message);
    }

}
