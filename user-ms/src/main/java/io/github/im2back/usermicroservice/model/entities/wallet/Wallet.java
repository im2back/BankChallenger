package io.github.im2back.usermicroservice.model.entities.wallet;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.github.im2back.usermicroservice.model.entities.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_wallet")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public  class Wallet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private BigDecimal balance;
	
	@Enumerated(EnumType.STRING)
	private WalletType type;

	@OneToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;

	public Wallet(Long id, BigDecimal balance, WalletType type, User user) {
		super();
		this.id = id;
		this.balance = balance;
		this.type = type;
		this.user = user;
	}

	public void receiveTransfer(BigDecimal amount) {
		this.balance = this.balance.add(amount);
	}

}
