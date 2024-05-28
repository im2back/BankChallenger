package io.github.im2back.usermicroservice.model.entities.user;

import io.github.im2back.usermicroservice.model.entities.wallet.Wallet;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name  = "tb_user")
public class User {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "full_name")
	private String fullName;
	
	@Column(name = "document")
	private String identificationDocument;
	
	private String email;
	
	private String password;
	
	@Column(name = "user_type")
	@Enumerated(EnumType.STRING)
	private UserType type;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Wallet wallet;

	public User(String fullName, String identificationDocument, String email, String password, UserType type,Wallet wallet) {
		super();
		this.fullName = fullName;
		this.identificationDocument = identificationDocument;
		this.email = email;
		this.password = password;
		this.type = type;
		this.wallet = wallet;
	}
	
	
}	

