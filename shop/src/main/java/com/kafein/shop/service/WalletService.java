package com.kafein.shop.service;

import java.util.List;

import com.kafein.shop.dto.Wallet;

public interface WalletService {
	
	List<Wallet> getWallets(Long userId);
	

}
