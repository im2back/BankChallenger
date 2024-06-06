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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RequestMapping("/users")
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Operation(summary = "Retorna um DTO de usuário apartir do ID informado no path")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retorna UserRegisterResponseDto"),
			@ApiResponse(responseCode = "400", description = "Retorna uma exceção em caso de ID não encontrado UserNotFoundException"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor") })
	@GetMapping(value = "/{id}")
	ResponseEntity<UserRegisterResponseDto> findUser(@PathVariable Long id) {
		var user = userService.findByIdReturnDto(id);
		return ResponseEntity.ok(user);
	}

	@Operation(summary = "Retorna um DTO de usuário após cadastra-lo no banco de dados")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Retorna UserRegisterResponseDto"),
			@ApiResponse(responseCode = "409", description = "Retorna uma exceção em caso de DOCUMENTO duplicado CannotBeDuplicatedException"),
			@ApiResponse(responseCode = "409", description = "Retorna uma exceção em caso de EMAIL duplicado CannotBeDuplicatedException"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor") })
	@PostMapping
	ResponseEntity<UserRegisterResponseDto> saveUser(@Valid @RequestBody UserRegisterRequestDto dto,
			UriComponentsBuilder uriBuilder) {
		UserRegisterResponseDto response = userService.saveUser(dto);
		var uri = uriBuilder.path("/users/{id}").buildAndExpand(response.id()).toUri();
		return ResponseEntity.created(uri).body(response);
	}

	@Operation(summary = "Realiza uma operação de transferencia e não retorna corpo")

	@ApiResponses(value = {

			@ApiResponse(responseCode = "200", description = "Retorna status 200 em caso de operação bem sucedida"),

			@ApiResponse(responseCode = "500", description = "Erro interno no servidor") })

	@PutMapping("/transfer")
	ResponseEntity<Void> transfer(@Valid @RequestBody TransferRequestDto dto) {
		userService.transfer(dto);
		return ResponseEntity.ok().build();
	}

}
