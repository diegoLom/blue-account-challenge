package com.losolved.robot.model;

import java.util.UUID;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Position {
	
	@Id
	private UUID id;
	
	private String alias;
	
	//@Positive(message = "Only positive numbers are accepted")
	private Integer posX; 
	
	//@Positive(message = "Only positive numbers are accepted")
	private Integer posY;
	
	private Character orientation; 
	


}
