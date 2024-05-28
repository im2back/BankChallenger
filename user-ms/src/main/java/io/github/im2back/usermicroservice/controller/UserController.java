package io.github.im2back.usermicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.im2back.usermicroservice.model.dto.TransferRequestDto;
import io.github.im2back.usermicroservice.model.dto.UserRegisterRequestDto;
import io.github.im2back.usermicroservice.model.dto.UserRegisterResponseDto;
import io.github.im2back.usermicroservice.service.UserService;
import jakarta.validation.Valid;

@RequestMapping("/users")
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/{id}")
	ResponseEntity<UserRegisterResponseDto> findUser(@PathVariable Long id) {
		var user = userService.findById(id);
		return ResponseEntity
				.ok(new UserRegisterResponseDto(user.getId(), user.getFullName(), user.getIdentificationDocument(),
						user.getEmail(), user.getPassword(), user.getType(), user.getWallet().getBalance()));
	}

	@PostMapping
	ResponseEntity<UserRegisterResponseDto> saveUser(@Valid @RequestBody UserRegisterRequestDto dto,
			UriComponentsBuilder uriBuilder) {
		var response = userService.saveUser(dto);
		var uri = uriBuilder.path("/users/{id}").buildAndExpand(response.id()).toUri();
		return ResponseEntity.created(uri).body(response);
	}

	@PutMapping("/transfer")
	ResponseEntity<Void> transfer(@Valid @RequestBody TransferRequestDto dto) {

		userService.transfer(dto.idPayer(), dto.idPayee(), dto.value());
		return ResponseEntity.ok().build();
	}

}
