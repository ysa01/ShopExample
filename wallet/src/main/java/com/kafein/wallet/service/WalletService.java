package com.kafein.wallet.service;

import com.kafein.wallet.entity.Wallet;

import java.util.List;

public interface WalletService {

    Wallet getWalletById(Long id);
    List<Wallet> getWalletsByUserId(Long userId);
}
