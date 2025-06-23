package com.example.moonrider.moonrider.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "contact")
public class Contact {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String phoneNumber;
	    private String email;

	    private Long linkedId; // refers to primary contact's id

	    @Enumerated(EnumType.STRING)
	    private LinkPrecedence linkPrecedence;

	    private LocalDateTime createdAt = LocalDateTime.now();
	    private LocalDateTime updatedAt = LocalDateTime.now();

	    private LocalDateTime deletedAt;

	    @PreUpdate
	    public void onUpdate() {
	        this.updatedAt = LocalDateTime.now();
	    }

	}



