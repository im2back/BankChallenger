package io.github.im2back.PicPayChallenger.validation.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.im2back.PicPayChallenger.exceptions.CannotBeDuplicatedException;
import io.github.im2back.PicPayChallenger.model.dto.UserRegisterRequestDto;
import io.github.im2back.PicPayChallenger.model.entities.user.User;
import io.github.im2back.PicPayChallenger.repositories.UserRepository;

@Component
public class DocumentCannotBeDuplicated implements UserRegistrationValidation{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void valid(UserRegisterRequestDto dto) {
		String document = dto.identificationDocument();
		Optional<User> user = userRepository.findByIdentificationDocument(document);
		
		if(user.isPresent()) {
			throw new CannotBeDuplicatedException("Document already registered: "+document);
		}
		
	}

}
