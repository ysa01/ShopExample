package com.kafein.wallet.service;

import com.kafein.wallet.entity.Wallet;
import com.kafein.wallet.entity.UpdateWalletIdAndDto;

import java.util.List;
import java.util.Map;

public interface WalletService {

    Wallet getWalletById(Long id);
    List<Wallet> getWalletsByUserId(Long userId);
	List<Wallet> getWalletUpdate(Long userId, List<UpdateWalletIdAndDto> map);
}
