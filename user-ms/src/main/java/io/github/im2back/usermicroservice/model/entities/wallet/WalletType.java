package io.github.im2back.usermicroservice.model.entities.wallet;

import java.math.BigDecimal;

import io.github.im2back.usermicroservice.model.entities.user.User;

public enum WalletType {
	COMUM {

		public void transfer(User userPayer, BigDecimal amount) {
			subtractValue(userPayer, amount);
		}
	},

	LOGISTA;

	public void transfer(User userPayer, BigDecimal amount) {
		throw new UnsupportedOperationException("Transfer operation is not supported for " + this);
	}

	private static void subtractValue(User userPayer, BigDecimal amount) {
	    BigDecimal newBalance = userPayer.getWallet().getBalance().subtract(amount);
	    userPayer.getWallet().setBalance(newBalance);
	}


}
