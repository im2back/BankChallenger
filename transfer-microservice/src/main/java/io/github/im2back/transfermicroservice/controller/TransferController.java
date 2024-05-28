package io.github.im2back.transfermicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.im2back.transfermicroservice.dto.TransferRequestDto;
import io.github.im2back.transfermicroservice.service.TransferService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/transfer")
public class TransferController {
	
	@Autowired
	private TransferService service;
	
	@PostMapping
	public ResponseEntity<Void> transfer(@Valid @RequestBody TransferRequestDto dto) {
		service.transfer(dto.idPayer(), dto.idPayee(), dto.value());
		return ResponseEntity.ok().build();
	}

}
