package com.kafein.wallet.entity;

public class UpdateWalletIdAndDto {
	private Long id;
	private double amount;
	
	public UpdateWalletIdAndDto() {
	}
	public UpdateWalletIdAndDto(Long id, double amount) {
		this.id = id;
		this.amount = amount;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
