package io.github.im2back.usermicroservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.im2back.usermicroservice.repositories.WalletRepository;

@Service
public class WalletService {
	
	@SuppressWarnings("unused")
	@Autowired
	private WalletRepository repository;

}
