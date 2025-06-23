package com.example.moonrider.moonrider.dto;

import java.util.List;
import java.util.Set;

import lombok.Data;

@Data
public class ContectResponse {
	
     private Long primaryContactId;
	 private Set<String> emails;
	 private Set<String> phoneNumbers;
	 private List<Long> secondaryContactIds;

//Constructor
public ContectResponse(Long primaryContactId, Set<String> emails, Set<String> phoneNumbers,
		List<Long> secondaryContactIds) {
	super();
	this.primaryContactId = primaryContactId;
	this.emails = emails;
	this.phoneNumbers = phoneNumbers;
	this.secondaryContactIds = secondaryContactIds;
    
    }

}
