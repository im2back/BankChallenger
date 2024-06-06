package io.github.im2back.usermicroservice.model.entities.wallet;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.github.im2back.usermicroservice.model.entities.user.UserComum;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("comum")
public class WalletComum extends WalletGeneric {


	@OneToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private UserComum user;

	public WalletComum() {
	}

	public WalletComum(Long id, BigDecimal balance, UserComum user) {
		super(id, balance);
		this.user = user;
	}

	public void transfer(BigDecimal amount) {
		this.balance = this.balance.subtract(amount);
	}

}
