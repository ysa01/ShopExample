package com.kafein.shop.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.kafein.shop.dto.Wallet;

public interface WalletService {
	
	ResponseEntity<String> getWallets(Long userId);
	
	ResponseEntity<String> getSimpleWallet(Wallet wallet);

}
