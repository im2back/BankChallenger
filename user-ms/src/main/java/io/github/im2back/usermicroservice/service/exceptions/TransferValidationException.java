package io.github.im2back.usermicroservice.service.exceptions;

public class TransferValidationException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public TransferValidationException(String msg) {
		super(msg);
	}

}
