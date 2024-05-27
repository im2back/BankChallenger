package io.github.im2back.PicPayChallenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.im2back.PicPayChallenger.model.entities.wallet.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

}
