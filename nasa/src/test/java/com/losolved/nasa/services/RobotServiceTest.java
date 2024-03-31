package com.losolved.nasa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;


import com.losolved.nasa.dto.ResponseDTO;

import com.losolved.nasa.dto.InDateBetweenAndRobot;
import com.losolved.nasa.dto.PositionDTO;
import com.losolved.nasa.errorhandling.NoSuchRobotException;
import com.losolved.nasa.model.Robot;
import com.losolved.nasa.services.NasaMocked;
import com.losolved.nasa.services.impl.RobotServiceImpl;
import com.losolved.nasa.repositories.RobotRepository;

@SpringBootTest
public class RobotServiceTest {
	
	
	private RobotService robotSerivce;
	
	@MockBean
	private RobotRepository robotRepository;
	
	@Autowired
	public RobotServiceTest(RobotServiceImpl robotSerivce) {
		this.robotSerivce = robotSerivce;
	}
	
	@Test
	public void testLaunch() {
		Robot mockedRobot = NasaMocked.CURIOSITY;
		
		given(robotRepository.save(ArgumentMatchers.any())).willReturn(Optional.of(mockedRobot));

		ResponseDTO response = robotSerivce.launch(mockedRobot);
		assertEquals(response.code(), HttpStatus.CREATED.value());
		assertEquals(response.message(), "Robot launched");
	}
	
	@Test
	public void testGetPositionByById() {
		Robot mockedRobot = NasaMocked.OPPORTUNITY;
		
		given(robotRepository.findById(mockedRobot.getId())).willReturn(Optional.of(mockedRobot));

		PositionDTO position = robotSerivce.getRobotPosition(any());

		assertEquals(mockedRobot.getOrientation(), position.orientation());
		assertEquals(mockedRobot.getPosX(), position.posX());
		assertEquals(mockedRobot.getPosY(), position.posY());
	}

	@Test
	public void testGetPositionByIdNotFound() {
		given(robotRepository.findById( NasaMocked.OPPORTUNITY.getId())).willReturn(Optional.empty());

		assertThrows(NoSuchRobotException.class, () -> robotSerivce.getRobotPosition(NasaMocked.OPPORTUNITY.getId()));
	}
	
	@Test
	public void testSuccessUpdateRobot() {
		
		given(robotRepository.save(NasaMocked.SOJOURNER)).willReturn(NasaMocked.SOJOURNER);
				
		PositionDTO position = new PositionDTO(0, 1, 'N');
		
		ResponseDTO responseDTO = robotSerivce.updatePosition(position, UUID.randomUUID());

		assertEquals(HttpStatus.OK.value(), responseDTO.code());
		assertEquals("Position updated", responseDTO.message());
	}
	
	@Test
	public void testFailureUpdateRobot() {
		given(robotRepository.findById( NasaMocked.OPPORTUNITY.getId())).willReturn(Optional.empty());
		
		PositionDTO position = new PositionDTO(0, 1, 'N');

		assertThrows(NoSuchRobotException.class, () -> robotSerivce.updatePosition(position, UUID.randomUUID()));
	}
	


}
