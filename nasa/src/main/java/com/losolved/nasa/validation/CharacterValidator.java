package com.losolved.nasa.validation;

import java.util.Arrays;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class CharacterValidator implements ConstraintValidator<CharacterAllowedValues, Character> {

	private char[] allowedValues;
	
	@Override
	public void initialize(CharacterAllowedValues constraintAnnotation) {
	    this.allowedValues = constraintAnnotation.values();
	}

	@Override
	public boolean isValid(Character value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		
		for (char allowedValue : allowedValues) {
            if (value == allowedValue) {
                return true;
            }
        }
		
		 return false; 
	}
	


}
