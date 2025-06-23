package com.example.moonrider.moonrider.dto;

import lombok.Data;

@Data
public class IdentityResponse {

	 private ContectResponse contact;

	 //Constructor
	 public IdentityResponse(ContectResponse contact) {
		super();
		this.contact = contact;
	}
	 
}
