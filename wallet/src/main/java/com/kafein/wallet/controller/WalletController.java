package com.kafein.wallet.controller;

import com.kafein.wallet.entity.Wallet;
import com.kafein.wallet.entity.UpdateWalletIdAndDto;
import com.kafein.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/wallet")
public class WalletController {

	@Autowired
	WalletService walletService;

	@GetMapping("/{userId}")
	@ResponseBody
	public ResponseEntity<List<Wallet>> getWallets(@PathVariable Long userId) {
		try {
			return new ResponseEntity<>(walletService.getWalletsByUserId(userId), HttpStatus.OK);
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<>(e.getStatusCode());
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/extract/{userId}")
	public ResponseEntity<List<Wallet>> getAllWallets(@PathVariable Long userId,
			@RequestBody List<UpdateWalletIdAndDto> map) {
		try {
			return new ResponseEntity<>(walletService.getWalletUpdate(userId, map), HttpStatus.OK);
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<>(e.getStatusCode());
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/updateWallet", consumes = "application/json", produces = "application/json")
	public String getSimpleWalletPay(@RequestBody Wallet wallet) {

		return walletService.getSimpleWalletPay(wallet);

	}

}
