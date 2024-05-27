package io.github.im2back.PicPayChallenger.service.exceptions;

public class UserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UserNotFoundException(Long id) {
		super("User not found for id: "+ id);
	}

}
