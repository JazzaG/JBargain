package com.somethinglurks.jbargain.api.user.exception;

/**
 * Thrown when something goes wrong when casting a vote
 */
public class VoteException extends Exception {

    public VoteException(String message) {
        super(message);
    }

}
