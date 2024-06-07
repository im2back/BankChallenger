package io.github.im2back.usermicroservice.model.entities.wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.github.im2back.usermicroservice.model.entities.user.User;
import io.github.im2back.usermicroservice.util.UtilsTest;

class WalletTest {

	@Test
	@DisplayName("Deveria deduzir o valor recebido como parametro da carteira")
	void transfer() {
		// ARRANGE
		User user = UtilsTest.userComum;
		user.getWallet().setBalance(new BigDecimal(20));	
		BigDecimal value = new BigDecimal(10);
		
		// ACT
		user.getWallet().getType().transfer(user, value);
		
		// ASSERT
		assertEquals(new BigDecimal(10), user.getWallet().getBalance());
	}
	
	@Test
	@DisplayName("Deveria acrecentar o valor recebido como parametro da carteira")
	void receiveTransfer() {
		// ARRANGE
		User user = UtilsTest.userComum;
		user.getWallet().setBalance(new BigDecimal(100));	
		BigDecimal value = new BigDecimal(10);
		
		// ACT
		user.getWallet().receiveTransfer(value);
		
		// ASSERT
		assertEquals(new BigDecimal(110), user.getWallet().getBalance());
	}

}






