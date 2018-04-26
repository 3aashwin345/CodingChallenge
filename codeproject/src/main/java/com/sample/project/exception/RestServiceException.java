package com.sample.project.exception;

public class RestServiceException extends RuntimeException {
    
    /**
     * 
     */
    private static final long serialVersionUID = -4039758357142981112L;

    private final String message;

    private final int messageCode;

    /**
     *
     * @param cause
     * @param messageCode
     * @param message
     */
    public RestServiceException(Throwable cause, int messageCode, String message) {
        super(message, cause);
        this.messageCode = messageCode;
        this.message = message;
    }

    /**
     * Parameterized constructor
     *
     * @param status
     * @param errorMessage
     */
    public RestServiceException(int status, String errorMessage) {

        super(errorMessage);
        this.messageCode = status;
        this.message = errorMessage;
    }

    /**
     * @return the message
     */
    @Override
    public String getMessage() {

        return message;
    }

    /**
     * @return the messageCode
     */
    public int getMessageCode() {

        return messageCode;
    }

}
