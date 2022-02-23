package com.kafein.wallet.repository;

import com.kafein.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet findWalletById(Long id);
    List<Wallet> findAllByUserId(Long userId);
}
