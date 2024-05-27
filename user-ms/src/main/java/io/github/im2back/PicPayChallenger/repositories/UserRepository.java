package io.github.im2back.PicPayChallenger.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.im2back.PicPayChallenger.model.entities.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByIdentificationDocument(String document);

	Optional<User> findByEmail(String email);

}
