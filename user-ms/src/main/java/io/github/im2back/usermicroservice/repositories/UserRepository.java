package io.github.im2back.usermicroservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.im2back.usermicroservice.model.entities.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByIdentificationDocument(String document);

	Optional<User> findByEmail(String email);

}
