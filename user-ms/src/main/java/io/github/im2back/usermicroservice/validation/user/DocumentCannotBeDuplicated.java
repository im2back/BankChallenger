package io.github.im2back.usermicroservice.validation.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.im2back.usermicroservice.model.dto.UserRegisterRequestDto;
import io.github.im2back.usermicroservice.model.entities.user.UserGeneric;
import io.github.im2back.usermicroservice.repositories.UserRepository;
import io.github.im2back.usermicroservice.service.exceptions.CannotBeDuplicatedException;

@Component
public class DocumentCannotBeDuplicated implements UserRegistrationValidation {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void valid(UserRegisterRequestDto dto) {
		String document = dto.identificationDocument();
		Optional<UserGeneric> user = userRepository.findByCpfOrCnpj(document);

		if (user.isPresent()) {
			throw new CannotBeDuplicatedException("Document already registered: " + document);
		}

	}

}
