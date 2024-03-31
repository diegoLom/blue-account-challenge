package com.losolved.nasa.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class CharacterValidatorTest {

	private  CharacterValidator characterValidator = new CharacterValidator();
	
	
	@CharacterAllowedValues(values = {'N', 'W', 'E', 'S'})
	private  Character ORIENTATION;
	
	
	
	@Test
	public void testIsValid() {
		try {
			Field field = CharacterValidatorTest.class.getDeclaredField("ORIENTATION");
	        CharacterAllowedValues annotation = field.getAnnotation(CharacterAllowedValues.class);
	        characterValidator.initialize(annotation);
	        assertTrue(characterValidator.isValid('N', null));
	        assertFalse(characterValidator.isValid('T', null));
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
