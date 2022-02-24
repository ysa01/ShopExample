package com.kafein.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.kafein.shop.dto.Wallet;
import com.kafein.shop.service.WalletService;

@RestController
@RequestMapping("/shop/wallet")
public class WalletController {
	
	@Autowired
	private WalletService walletService;
	
	public WalletController(WalletService walletService) {
		this.walletService = walletService;
	}
	@GetMapping("/{id}")
	public ResponseEntity<Wallet> getAllShoppingEntity (@PathVariable Long id){
		walletService.getWallets(id);
		return null;
	}
	
//	public String getAllWalletExtract () {
//		walletService.postWalletExtract(null)
//		return null;
//	}
}
