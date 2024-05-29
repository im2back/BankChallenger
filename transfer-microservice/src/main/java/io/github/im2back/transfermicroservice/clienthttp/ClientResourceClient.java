package io.github.im2back.transfermicroservice.clienthttp;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.github.im2back.transfermicroservice.dto.TransferRequestDto;
import io.github.im2back.transfermicroservice.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@FeignClient(name="user-microservice" ,url = "http://localhost:8080", path = "/users")
public interface ClientResourceClient {
	
	@Operation(summary = "Faz uma requisição ao user-ms para buscar um usário por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Retorna um UserDto, contendo o usuário correspondente ao ID informado"),
			})
	@GetMapping(value = "/{id}")
	ResponseEntity<UserDto> findUser(@PathVariable Long id);
	
	
	@Operation(summary = "Faz uma requisição para realizar uma transferencia")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Não retorna body em caso de sucesso"),
			})
	@PutMapping("/transfer")
	ResponseEntity<Void> transfer(@Valid @RequestBody TransferRequestDto dto);
}

	
