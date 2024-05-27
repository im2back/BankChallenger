package io.github.im2back.PicPayChallenger.model.entities.user;

import io.github.im2back.PicPayChallenger.model.entities.wallet.Wallet;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
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
	private UserType type;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Wallet wallet = new Wallet();
}	

