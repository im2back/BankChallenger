package io.github.im2back.usermicroservice.model.entities.wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.github.im2back.usermicroservice.model.entities.user.UserComum;
import io.github.im2back.usermicroservice.util.UtilsTest;

class WalletGenericTest {

	@Test
	@DisplayName("Deveria deduzir o valor recebido como parametro da carteira")
	void transfer() {
		// ARRANGE
		UserComum user = UtilsTest.userComum;
		user.getWallet().setBalance(new BigDecimal(100));	
		BigDecimal value = new BigDecimal(10);
		
		// ACT
		user.getWallet().transfer(value);
		
		// ASSERT
		assertEquals(new BigDecimal(90), user.getWallet().getBalance());
	}
	@Test
	@DisplayName("Deveria acrecentar o valor recebido como parametro da carteira")
	void receiveTransfer() {
		// ARRANGE
		UserComum user = UtilsTest.userComum;
		user.getWallet().setBalance(new BigDecimal(100));	
		BigDecimal value = new BigDecimal(10);
		
		// ACT
		user.getWallet().receiveTransfer(value);
		
		// ASSERT
		assertEquals(new BigDecimal(110), user.getWallet().getBalance());
	}

}






