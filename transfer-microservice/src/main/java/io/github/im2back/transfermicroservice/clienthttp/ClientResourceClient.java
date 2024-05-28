package io.github.im2back.transfermicroservice.clienthttp;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.github.im2back.transfermicroservice.dto.TransferRequestDto;
import io.github.im2back.transfermicroservice.dto.UserDto;
import jakarta.validation.Valid;

@FeignClient(name="user-microservice" ,url = "http://localhost:8080", path = "/users")
public interface ClientResourceClient {

	@GetMapping(value = "/{id}")
	ResponseEntity<UserDto> findUser(@PathVariable Long id);
	
	@PutMapping("/transfer")
	ResponseEntity<Void> transfer(@Valid @RequestBody TransferRequestDto dto);
}

	
