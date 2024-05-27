package io.github.im2back.PicPayChallenger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.im2back.PicPayChallenger.repositories.WalletRepository;

@Service
public class WalletService {
	
	@Autowired
	private WalletRepository repository;

}
