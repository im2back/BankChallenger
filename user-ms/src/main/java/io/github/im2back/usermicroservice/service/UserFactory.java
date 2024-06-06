package io.github.im2back.usermicroservice.service;

import java.math.BigDecimal;

import io.github.im2back.usermicroservice.model.entities.user.UserComum;
import io.github.im2back.usermicroservice.model.entities.user.UserGeneric;
import io.github.im2back.usermicroservice.model.entities.user.UserLogista;
import io.github.im2back.usermicroservice.model.entities.wallet.WalletComum;
import io.github.im2back.usermicroservice.model.entities.wallet.WalletLogista;
import io.github.im2back.usermicroservice.service.exceptions.InvalidFormatException;

public class UserFactory {

	public static UserGeneric createUser(String fullName, String email, String password, String document) {
	    if (isCpf(document)) {
	        WalletComum wallet = new WalletComum(null, new BigDecimal(100), null);
	        return new UserComum(fullName, password, document,email,  wallet);
	    }
	    if (isCnpj(document)) {
	    	WalletLogista wallet = new WalletLogista(null, new BigDecimal(100), null);
	        return new UserLogista(fullName, password, document, email, wallet);
	    } 
	    else throw new InvalidFormatException("Invalid document format");
	}


	private static boolean isCpf(String document) {
		return document.matches("\\d{11}");
	}

	private static boolean isCnpj(String document) {
		return document.matches("\\d{14}");
	}
}
