package io.github.im2back.PicPayChallenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.im2back.PicPayChallenger.model.entities.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
