package com.losolved.nasa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.losolved.nasa.dto.ResponseDTO;
import com.losolved.nasa.dto.InDateBetweenAndRobot;

import com.losolved.nasa.errorhandling.NoSuchRegisterException;
import com.losolved.nasa.model.Register;

import com.losolved.nasa.services.impl.RegisterServiceImpl;
import com.losolved.nasa.repositories.RegisterRepository;

@SpringBootTest
public class RegisterServiceTest {
	
	
	private RegisterService registerSerivce;
	
	@MockBean
	private RegisterRepository registerRepository;
	
	@Autowired
	public RegisterServiceTest(RegisterServiceImpl registerSerivce) {
		this.registerSerivce = registerSerivce;
	}
	
	@Test
	public void testWriteDown() {
		Register mockedRegister = NasaMocked.getMockedRegister();
		
		given(registerRepository.save(ArgumentMatchers.any())).willReturn(Optional.of(mockedRegister));

		ResponseDTO response = registerSerivce.writeDown(mockedRegister);
		assertEquals(response.code(), HttpStatus.CREATED.value());
		assertEquals(response.message(), "Register saved");
	}
	
	
	@Test
	public void testGetRegisterFromDateBetween() {
		InDateBetweenAndRobot  searchFilter = new InDateBetweenAndRobot( NasaMocked.REGISTER_MADE_IN_PAST_THREE,  NasaMocked.REGISTER_MADE_IN_PAST_FOUR, NasaMocked.OPPORTUNITY);
		List<Register> registerFromDB = NasaMocked.getMockedRegister(searchFilter);
		
		given(registerRepository.findByRegisterDateBetweenAndRobot(searchFilter.startRegister(), searchFilter.endRegister(), searchFilter.robot()))
		.willReturn(registerFromDB);
		
		List<Register>  registersFromService = registerSerivce.getRegisterFromDateBetween(searchFilter);
		
		assertEquals(registerFromDB.size(), registersFromService.size());
	}
	
	@Test 
	public void testGetRegisterFromDateBetweenNotFound() {
		
		InDateBetweenAndRobot search = new InDateBetweenAndRobot(NasaMocked.REGISTER_MADE_IN_PAST_FOUR, NasaMocked.REGISTER_MADE_IN_PAST_FIVE, NasaMocked.OPPORTUNITY);
		given(registerRepository.findByRegisterDateBetweenAndRobot(search.startRegister(), search.endRegister(), search.robot()) )
		.willReturn(Collections.emptyList());
		
		assertThrows(NoSuchRegisterException.class, () -> registerSerivce.getRegisterFromDateBetween(search));
	}
	
	@Test 
	public void testGetRegisterFromRobo() {
		List<Register> registerFromDB = NasaMocked.getMockedRegisterOf(NasaMocked.SOJOURNER);
		
		given(registerRepository.findByRobot(any()) )
		.willReturn(registerFromDB);
		
		List<Register>  registersFromService = registerSerivce.getRegisterFromRobo(NasaMocked.SOJOURNER.getId());
		
		assertEquals(registerFromDB.size(), registersFromService.size());
	}
	
	@Test 
	public void testGetRegisterFromRoboNotFound() {
		given(registerRepository.findByRobot(any()) )
		.willReturn(Collections.emptyList());
		
		assertThrows(NoSuchRegisterException.class, () -> registerSerivce.getRegisterFromRobo(NasaMocked.SOJOURNER.getId()));
	}




}
