package com.losolved.nasa.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.losolved.nasa.dto.InDateBetweenAndRobot;
import com.losolved.nasa.model.Register;
import com.losolved.nasa.model.Robot;

public class NasaMocked {
	
	 // Sojourner, Spirit, Opportunity, Curiosity e Perseverance
	
	   public static Register getMockedRegister() {
	        Robot robot = Robot.builder().id(UUID.randomUUID()).alias("Spirit").build();
	        
	         return Register.builder()
	                .id(1L)
	                .register(new byte[]{0x00, 0x01, 0x02}) 
	                .registerDate(LocalDateTime.now())
	                .robot(robot)
	                .build();
	    }
	   
	   public static Register createRegisterOf(Robot robot, LocalDateTime in) {
	              
	         return Register.builder()
	                .id(1L)
	                .register(new byte[]{0x00, 0x01, 0x02})
	                .registerDate(in)
	                .robot(robot)
	                .build();
	    }
	 
	   public static final Robot SOJOURNER =  Robot.builder().id(UUID.randomUUID()).alias("Sojourner").posX(0).posY(0).orientation('N').build();
	   public static final Robot OPPORTUNITY =  Robot.builder().id(UUID.randomUUID()).alias("Opportunity").posX(0).posY(0).orientation('N').build();
	   public static final Robot CURIOSITY =  Robot.builder().id(UUID.randomUUID()).alias("Curiosity").posX(1).posY(1).orientation('N').build(); 
	   
	   public static final LocalDateTime REGISTER_MADE_IN_PAST_FIVE = LocalDateTime.now().minusDays(5);
	   public static final LocalDateTime REGISTER_MADE_IN_PAST_FOUR = LocalDateTime.now().minusDays(4);
	   public static final	LocalDateTime REGISTER_MADE_IN_PAST_THREE = LocalDateTime.now().minusDays(3);

	   
	   
	   public static List<Robot> createMockedRobots(){
		   return Stream.of(SOJOURNER, OPPORTUNITY, CURIOSITY)
					.collect(Collectors.toList());
		   
	   }
	   
	   public static List<Register> createMockedRegisters(){
		   
			
			List<Register> registers = new ArrayList<Register>();
			
			registers.add(createRegisterOf(SOJOURNER, REGISTER_MADE_IN_PAST_FIVE ));
			registers.add(createRegisterOf(SOJOURNER, REGISTER_MADE_IN_PAST_FOUR ));
			registers.add(createRegisterOf(SOJOURNER, REGISTER_MADE_IN_PAST_THREE ));
			
			registers.add(createRegisterOf(OPPORTUNITY, REGISTER_MADE_IN_PAST_FIVE ));
			registers.add(createRegisterOf(OPPORTUNITY, REGISTER_MADE_IN_PAST_FOUR ));
			registers.add(createRegisterOf(OPPORTUNITY, REGISTER_MADE_IN_PAST_THREE ));
			
			registers.add(createRegisterOf(CURIOSITY, REGISTER_MADE_IN_PAST_FIVE ));
			registers.add(createRegisterOf(CURIOSITY, REGISTER_MADE_IN_PAST_FOUR ));
			registers.add(createRegisterOf(CURIOSITY, REGISTER_MADE_IN_PAST_THREE ));
		   
		   return registers; 
		   
	   }
	   
	   
	   public static List<Register> getMockedRegisterOf(Robot robot){
		   
		   List<Register> registers =   createMockedRegisters();
		   
		   List<Register> registersFiltered =  registers.stream().filter( x -> x.getRobot().getAlias().equals(robot.getAlias())).collect(Collectors.toList());
		   
		   return registersFiltered;
		   
	   }
	   
       public static List<Register> getMockedRegister(InDateBetweenAndRobot inDateBetweenAndRobot){
		   
		   List<Register> registers =   createMockedRegisters();
		   
		   List<Register> registersFiltered =  registers.stream().filter(x -> x.getRobot().getAlias().equals(inDateBetweenAndRobot.robot().getAlias())
				   && x.getRegisterDate().compareTo(inDateBetweenAndRobot.startRegister()) >=0 && x.getRegisterDate().compareTo(inDateBetweenAndRobot.endRegister()) <=0
				   ).
				   collect(Collectors.toList()); 
		   
		   return registersFiltered;
		   
	   }
}
