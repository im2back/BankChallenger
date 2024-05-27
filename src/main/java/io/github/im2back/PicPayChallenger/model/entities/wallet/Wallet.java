package io.github.im2back.PicPayChallenger.model.entities.wallet;

import java.math.BigDecimal;

import io.github.im2back.PicPayChallenger.model.entities.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Wallet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private BigDecimal balance = new BigDecimal(0);
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	public void transfer(BigDecimal amount) {
		 this.balance = this.balance.subtract(amount);
	}
	
	public void receiveTransfer(BigDecimal amount) {
		this.balance.add(amount);
	}
}
