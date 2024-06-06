package io.github.im2back.usermicroservice.model.entities.user;

import io.github.im2back.usermicroservice.model.entities.wallet.WalletLogista;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@DiscriminatorValue("logista")
public class UserLogista extends UserGeneric {

	@Column(name = "cnpj")
	private String cnpj;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private WalletLogista wallet;
	

	public UserLogista(String fullName,  String password, String cnpj, String email,WalletLogista wallet) {
		super(fullName,password , email );
		this.cnpj = cnpj;
		wallet.setUser(this);
		this.wallet = wallet;
	}


	public UserLogista(Long id, String fullName,String password, String cnpj, String email,  WalletLogista wallet) {
		super(id, fullName,  password, email);
		this.cnpj = cnpj;
		this.wallet = wallet;
	}


	
	
	
	
	
}
