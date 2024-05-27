package io.github.im2back.PicPayChallenger.validation.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.im2back.PicPayChallenger.model.dto.UserRegisterRequestDto;
import io.github.im2back.PicPayChallenger.model.entities.user.User;
import io.github.im2back.PicPayChallenger.repositories.UserRepository;
import io.github.im2back.PicPayChallenger.service.exceptions.CannotBeDuplicatedException;

@Component
public class EmailCannotBeDuplicated implements UserRegistrationValidation{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void valid(UserRegisterRequestDto dto) {
		String email = dto.email();
		Optional<User> user = userRepository.findByEmail(email);
		
		if(user.isPresent()) {
			throw new CannotBeDuplicatedException("Email already registered: "+email);
		}
		
	}

}
