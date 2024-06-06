package io.github.im2back.usermicroservice.model.entities.user;

import io.github.im2back.usermicroservice.model.entities.wallet.WalletComum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("comum")
@Entity
public class UserComum extends UserGeneric {

	@Column(name = "cpf")
	private String cpf;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private WalletComum wallet;

	public UserComum(Long id, String fullName, String password,  String cpf, String email,  WalletComum wallet) {
		super(id, fullName, password, email);
		this.cpf = cpf;
		this.wallet = wallet;
	}

	public UserComum(String fullName, String password, String cpf, String email,   WalletComum wallet) {
		super(fullName, password ,email);
		this.cpf = cpf;
		wallet.setUser(this);
		this.wallet = wallet;
	}

}