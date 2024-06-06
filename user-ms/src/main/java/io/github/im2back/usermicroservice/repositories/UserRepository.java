package io.github.im2back.usermicroservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.github.im2back.usermicroservice.model.entities.user.UserGeneric;

@Repository
public interface UserRepository extends JpaRepository<UserGeneric, Long> {

	Optional<UserGeneric> findByEmail(String email);

	 @Query("SELECT u FROM UserGeneric u WHERE u.cpf = :document OR u.cnpj = :document")
	    Optional<UserGeneric> findByCpfOrCnpj(String document);

}
