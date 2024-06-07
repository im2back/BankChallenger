package io.github.im2back.usermicroservice.model.entities.user;

import java.math.BigDecimal;

import io.github.im2back.usermicroservice.model.entities.wallet.Wallet;
import io.github.im2back.usermicroservice.model.entities.wallet.WalletType;

public enum UserType {
	
	LOGISTA,
	COMUM;
	
    public User createWallet(User user) {
        switch (user.getType()) {
            case LOGISTA:
            	Wallet walletLogista = new Wallet(null, new BigDecimal(500), WalletType.LOGISTA, null);
            	user.setWallet(walletLogista);
            	walletLogista.setUser(user);
                return user;
                	
            case COMUM:
            	Wallet walletComum = new Wallet (null, new BigDecimal(500), WalletType.COMUM, null);
            	user.setWallet(walletComum);
            	walletComum.setUser(user);
                return user;
            default:
                throw new IllegalArgumentException("Tipo de usuário desconhecido: " + this);
        }
    }
    
}
