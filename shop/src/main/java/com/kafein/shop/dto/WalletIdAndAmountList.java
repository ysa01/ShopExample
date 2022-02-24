package com.kafein.shop.dto;

public class WalletIdAndAmountList {
	
	private Long id;
	private double amout;
	
	public WalletIdAndAmountList() {
	}
	public WalletIdAndAmountList(Long id, double amout) {
		this.id = id;
		this.amout = amout;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getAmout() {
		return amout;
	}
	public void setAmout(double amout) {
		this.amout = amout;
	}
	
	
	
	
}
