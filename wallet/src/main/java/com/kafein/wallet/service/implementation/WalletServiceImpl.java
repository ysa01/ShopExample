package com.kafein.wallet.service.implementation;

import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import com.kafein.wallet.entity.Wallet;
import com.kafein.wallet.entity.UpdateWalletIdAndDto;
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

	@Override
	public List<Wallet> getWalletUpdate(Long userId, List<UpdateWalletIdAndDto> map) {
		if (userId == null) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		}
		try {
			List<Wallet> willUpdateWallets = walletRepository.findAllByUserId(userId);
			for (Wallet wallet : willUpdateWallets) {
				double newAmount = map.stream().filter(x -> x.getId() == wallet.getId()).findFirst().get().getAmount();

				wallet.setAmount((long) newAmount);
				walletRepository.saveAndFlush(wallet);
			}

		} catch (Exception e) {
			throw new RuntimeJsonMappingException("Hesabınız bloke olmuştur.");
		}

		return walletRepository.findAllByUserId(userId);
	}

	@Override
	public String getSimpleWalletPay(Wallet wallet) {
		
		try {
			Wallet oldWallet = walletRepository.findWalletById(wallet.getId());
			oldWallet.setAmount(wallet.getAmount());
			walletRepository.save(oldWallet);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		

		return "Success";
	}
}
