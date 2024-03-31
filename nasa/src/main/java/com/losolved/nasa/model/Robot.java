package com.losolved.nasa.model;

import java.util.UUID;

import com.losolved.nasa.validation.CharacterAllowedValues;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Robot {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	private String alias;
	
	@Positive(message = "Only positive numbers are accepted")
	private Integer posX; 
	
	@Positive(message = "Only positive numbers are accepted")
	private Integer posY;
	
	private Character orientation; 
	


}
