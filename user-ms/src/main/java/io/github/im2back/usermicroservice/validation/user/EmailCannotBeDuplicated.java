package io.github.im2back.usermicroservice.validation.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.im2back.usermicroservice.model.dto.UserRegisterRequestDto;
import io.github.im2back.usermicroservice.model.entities.user.User;
import io.github.im2back.usermicroservice.repositories.UserRepository;
import io.github.im2back.usermicroservice.service.exceptions.CannotBeDuplicatedException;

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
