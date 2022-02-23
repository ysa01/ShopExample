package com.kafein.shop.service.implementation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafein.shop.dto.Wallet;
import com.kafein.shop.entity.Item;
import com.kafein.shop.entity.ShoppingCart;
import com.kafein.shop.repository.ShoppingCartRepository;
import com.kafein.shop.service.WalletService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.RuntimeErrorException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WalletServiceImpl implements WalletService {

	private final RestTemplate restTemplate;

	private final ShoppingCartRepository shoppingCartRepository;

	public WalletServiceImpl(RestTemplate restTemplate, ShoppingCartRepository shoppingCartRepository) {
		this.restTemplate = restTemplate;
		this.shoppingCartRepository = shoppingCartRepository;

	}

	@Override
	public List<Wallet> getWallets(Long userId) {
		
		ObjectMapper mapper = new ObjectMapper();
		//Walletleri liste olarak walet appten aldım
		List<Wallet> response = mapper.convertValue(this.restTemplate.getForObject("http://localhost:8005/wallet/"+userId,List.class), new TypeReference<List<Wallet>>() { });
		//usera ait sepet içeriğini çektim
		ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartById(userId);
		List<Wallet> wallets = new ArrayList<Wallet>();
		Map<Long,Double> map = new HashMap<Long,Double>();
		List<Long> selectedWalletIds = new ArrayList<>();
		
		//Sepet içindeki Itemların tutarını topladım.
		Set<Item> list = new HashSet<Item>(shoppingCart.getItems());		
		double	totalCartAmount = list.stream().mapToDouble(x -> x.getPrice().doubleValue()).sum();
		
		//walltten gelen kullanıcıya ait wallelerin, sepet tutarından büyük olanını bulmaya çalışıyorum direk ondan çekmek için
		Wallet selectedWallet = response.stream().filter(x->x.getAmount() >= totalCartAmount).findAny().orElse(null);
				
		if(selectedWallet == null) {
		
			double totalWalletAmount = response.stream().mapToDouble(x -> x.getAmount()).sum();
		
			if(totalWalletAmount >= totalCartAmount){
		
				for(Wallet wallet : response){
					map.put(wallet.getId() , wallet.getAmount());
				}	
			 		
		double selectedWalletTotalAmount = 0.0;
		
		for (Map.Entry<Long, Double> entry : map.entrySet()) {
			
			if(selectedWalletTotalAmount <= totalCartAmount){
			//Burda içinde sepet tutarından az olan hesapların bakiyesini sıfırlamak için onları tespit ediyorum.	
			selectedWalletTotalAmount = selectedWalletTotalAmount + entry.getValue();			
			selectedWalletIds.add(entry.getKey());
			}
			}
		
		}else {throw new RuntimeException("Bakiye Yetersiz");} 
		}
		//Bu methodla sıfırlayacağımız hesaplar ve sepet tutarını wallet app'e post ederek , 
		//hesapları sıfıladıktan sonra (güncelleyerek) , kalan tutarı içinde para olan bir hesaptan düşürmeyi amaçladım.
		postWalletExtract(selectedWalletIds);
		wallets.add(selectedWallet);
		return wallets;
		
		}
	
	public void postWalletExtract (List<Long> walletIds) {
		
		
	}
		
	
	
		}


