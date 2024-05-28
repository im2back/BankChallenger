package io.github.im2back.usermicroservice.model.entities.wallet;

import java.math.BigDecimal;

import io.github.im2back.usermicroservice.model.entities.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Table(name = "tb_wallet")
public class Wallet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private BigDecimal balance;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	public void transfer(BigDecimal amount) {
		this.balance = this.balance.subtract(amount);
	}

	public void receiveTransfer(BigDecimal amount) {
		this.balance = this.balance.add(amount);
	}
}
