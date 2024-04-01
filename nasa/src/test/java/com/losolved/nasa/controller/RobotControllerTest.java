package com.losolved.nasa.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.losolved.nasa.dto.PositionDTO;
import com.losolved.nasa.dto.ResponseDTO;
import com.losolved.nasa.model.Robot;
import com.losolved.nasa.services.NasaMocked;
import com.losolved.nasa.services.RobotService;
import com.losolved.nasa.services.impl.RobotServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(RobotController.class)
public class RobotControllerTest {

	@MockBean
	private RobotServiceImpl robotService;

	@Autowired
	private MockMvc mockMvc;

	private static ObjectMapper mapper = new ObjectMapper();

	@BeforeAll
	public static void setup() {
		mapper = new ObjectMapper().registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module())
				.registerModule(new JavaTimeModule());

	}

	@Test
	public void givenLaunchOperation() throws Exception {
		given(robotService.launch(ArgumentMatchers.any()))
				.willReturn(new ResponseDTO("Robot launched", HttpStatus.CREATED.value()));
		Robot robot = NasaMocked.CURIOSITY;
		String json = mapper.writeValueAsString(robot);
		mockMvc.perform(
				post("/api/robot").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(json)). // .accept(MediaType.APPLICATION_JSON)).
				andExpect(status().isCreated()).andExpect(jsonPath("$.code", Matchers.equalTo(201)))
				.andExpect(jsonPath("$.message", Matchers.equalTo("Robot launched")));
	}

	@Test
	public void givenGetPositionOperation() throws Exception {
		// Configuração do serviço de mock para retornar um robô específico
		given(robotService.getRobotPosition(NasaMocked.SOJOURNER.getId())).willReturn(new PositionDTO(
				NasaMocked.SOJOURNER.getPosX(), NasaMocked.SOJOURNER.getPosY(), NasaMocked.SOJOURNER.getOrientation()));
		// Chamada para mockMvc.perform com o método GET e o caminho da URL correto
		mockMvc.perform(get("/api/robot/".concat(NasaMocked.SOJOURNER.getId().toString()))
				.contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")).andExpect(status().isOk())
				.andExpect(jsonPath("$.posX", Matchers.equalTo(0)))
				.andExpect(jsonPath("$.posY", Matchers.equalTo(0)))
				.andExpect(jsonPath("$.orientation", Matchers.equalTo("N"))); 
	}

}
