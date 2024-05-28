package io.github.im2back.usermicroservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.im2back.usermicroservice.model.entities.wallet.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

}
