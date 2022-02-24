package com.kafein.shop.service.implementation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafein.shop.dto.Wallet;
import com.kafein.shop.dto.WalletIdAndAmountList;
import com.kafein.shop.entity.Item;
import com.kafein.shop.entity.ShoppingCart;
import com.kafein.shop.repository.ShoppingCartRepository;
import com.kafein.shop.service.WalletService;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<String> getWallets(Long userId) {
		Long cartId=1l;
		ObjectMapper mapper = new ObjectMapper();
		//Walletleri liste olarak walet appten aldım
		List<Wallet> response = mapper.convertValue(this.restTemplate.getForObject("http://localhost:8005/wallet/"+userId,List.class), new TypeReference<List<Wallet>>() { });
		//usera ait sepet içeriğini çektim
		ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartById(cartId);
		List<Wallet> wallets = new ArrayList<Wallet>();
		Map<Long,Double> map = new HashMap<Long,Double>();
		Map<Long,Double> selectedWalletsMap = new HashMap<Long,Double>();
		List<Long> selectedWalletIds = new ArrayList<Long>();
		
		//Sepet içindeki Itemların tutarını topladım.
		Set<Item> list = new HashSet<Item>(shoppingCart.getItems());		
		double	totalCartAmount = list.stream().mapToDouble(x -> x.getPrice().doubleValue()).sum();
		
		//walltte app'ten gelen kullanıcıya ait wallelerin, sepet tutarından büyük olanını bulmaya çalışıyorum direk ondan çekmek için
		Wallet selectedWallet = response.stream().filter(x->x.getAmount() >= totalCartAmount).findFirst().orElse(null);
				
		if(selectedWallet == null) {
		
			double totalWalletAmount = response.stream().mapToDouble(x -> x.getAmount()).sum();
			
			if(totalWalletAmount >= totalCartAmount){
				
				List<Wallet> responsSorted = response.stream()
						.sorted(Comparator.comparingDouble(Wallet :: getAmount))
						.collect(Collectors.toList());
				
				for(Wallet wallet : responsSorted){
					map.put(wallet.getId() , wallet.getAmount());
				}	
			 		
		double selectedWalletTotalAmount = 0.0;
		
		for (Map.Entry<Long, Double> entry : map.entrySet()) {
			
			if(selectedWalletTotalAmount <= totalCartAmount){
			//Burda içinde sepet tutarından az olan hesapların bakiyesini sıfırlamak için onları tespit ediyorum.	
				selectedWalletTotalAmount = selectedWalletTotalAmount + entry.getValue();			
				selectedWalletsMap.put(entry.getKey(), entry.getValue());
				selectedWalletIds.add(entry.getKey());
			}
			}
		//Burda bakiyesi artacak hesabın key ve value değerlerini bulup güncelledim.
		  Long maxWalletKey  = Collections.max(selectedWalletsMap.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();
		  selectedWalletTotalAmount = selectedWalletsMap.entrySet().stream().mapToDouble(x -> x.getValue()).sum();
		  double remainingWalletAmount = selectedWalletTotalAmount-totalCartAmount;
		  selectedWalletsMap.put(maxWalletKey, remainingWalletAmount);
		  //Bu formda bakiyesi 0 olacak hesapların key value değerlerini buldum
		  for (Map.Entry<Long, Double> entry : selectedWalletsMap.entrySet()) {
				
				if(entry.getKey()!= maxWalletKey ){
				selectedWalletsMap.put(entry.getKey(), 0.00);
				}}
		
		}else { throw new RuntimeException("Bakiye Yetersiz");}	
			
			return postWallet(selectedWalletsMap); 
			}
			
		  double simpleWallet = selectedWallet.getAmount()- totalCartAmount;
		  selectedWallet.setAmount(simpleWallet);
		  
			return getSimpleWallet(selectedWallet);
		
		}
	
	
	public ResponseEntity<String> postWallet(Map<Long,Double> postMap) {
		int userId=1024;
		try {
			
			List<WalletIdAndAmountList> list = new ArrayList<WalletIdAndAmountList>();
			
			for (Map.Entry<Long,Double> entry : postMap.entrySet()) {
				WalletIdAndAmountList walletIdAndAmountList = new WalletIdAndAmountList();
				walletIdAndAmountList.setAmout(entry.getValue());
				walletIdAndAmountList.setId(entry.getKey());
				list.add(walletIdAndAmountList);
			}
						
			    HttpHeaders headers = new HttpHeaders();
			    
			    headers.setContentType(MediaType.APPLICATION_JSON);
			    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			    
			    HttpEntity<Object> requestEntity = new HttpEntity<Object>(list,headers);

			    ResponseEntity<List<WalletIdAndAmountList>> request = this.restTemplate.exchange("http://localhost:8005/wallet/extract/"+userId, HttpMethod.POST, requestEntity,new ParameterizedTypeReference<List<WalletIdAndAmountList>>() {});
			
		
						
		} catch (Exception e) {
			
			throw new RuntimeException(e);
		}
		
	  return ResponseEntity.ok("Ödeme Başarılı");
	}

	@Override
	public ResponseEntity<String> getSimpleWallet(Wallet wallet) {
		
		int userId=1202;
		wallet.setAmount((long)wallet.getAmount());
		try {						
			    HttpHeaders headers = new HttpHeaders();
			    
			    headers.setContentType(MediaType.APPLICATION_JSON);
			    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			    
			    HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

			    String request = this.restTemplate.postForObject("http://localhost:8005/wallet/updateWallet", wallet, String.class);
			
						
		} catch (Exception e) {
			
			throw new RuntimeException("Ödeme Başarısız");
		}
		
	  return ResponseEntity.ok("Ödeme Başarılı");
	}
	
	}


