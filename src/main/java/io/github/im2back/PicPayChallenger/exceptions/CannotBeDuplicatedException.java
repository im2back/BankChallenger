package io.github.im2back.PicPayChallenger.exceptions;

public class CannotBeDuplicatedException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public CannotBeDuplicatedException(String msg) {
		super(msg);
	}

}
