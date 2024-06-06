package io.github.im2back.usermicroservice.model.entities.wallet;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.github.im2back.usermicroservice.model.entities.user.UserLogista;
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
@DiscriminatorValue("logista")
public class WalletLogista extends WalletGeneric {

	@OneToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private UserLogista user;
	

	public WalletLogista() {

	}

	public WalletLogista(Long id, BigDecimal balance, UserLogista user) {
		super(id, balance);
		this.user = user;
	}

}
