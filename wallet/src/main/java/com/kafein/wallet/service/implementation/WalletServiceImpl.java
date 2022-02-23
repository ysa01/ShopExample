package com.kafein.wallet.service.implementation;

import com.kafein.wallet.entity.Wallet;
import com.kafein.wallet.repository.WalletRepository;
import com.kafein.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    WalletRepository walletRepository;

    @Override
    public Wallet getWalletById(Long id) {
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
        return walletRepository.findWalletById(id);
    }

    @Override
    public List<Wallet> getWalletsByUserId(Long userId) {
        if (userId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
        return walletRepository.findAllByUserId(userId);
    }
}
