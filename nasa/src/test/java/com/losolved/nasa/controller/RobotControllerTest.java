package com.losolved.nasa.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

@SpringBootTest
public class RobotControllerTest {
	
	private RobotService robotService;
	
	@MockBean
	private MockMvc mockMvc;
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	public RobotControllerTest(RobotServiceImpl robotService) {
		super();
		this.robotService = robotService;
	}

	@BeforeAll
	public static void setup() {
		mapper = new ObjectMapper().registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module())
				.registerModule(new JavaTimeModule());

	}
	
    @Test
    public void givenLaunchOperation() throws Exception {
        given(robotService.launch(ArgumentMatchers.any())).willReturn(new ResponseDTO("Robot launched", HttpStatus.CREATED.value()));
        Robot robot = NasaMocked.CURIOSITY;
        String json = mapper.writeValueAsString(robot);
        mockMvc.perform(post("/api/robot").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(json)). //.accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isCreated()).andExpect(jsonPath("$.code", Matchers.equalTo(201))).
                andExpect(jsonPath("$.message", Matchers.equalTo("Robot launched")));
   }
    
    
    @Test
    public void givenGetPositionOperation() throws Exception {
        given(robotService.getRobotPosition(ArgumentMatchers.any())).willReturn(new PositionDTO(1, 1, 'S'));
        mockMvc.perform(post("/api/robot")
                ). //.accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).andExpect(jsonPath("$.posX", Matchers.equalTo(1))).
                andExpect(jsonPath("$.posY", Matchers.equalTo(1))).
                andExpect(jsonPath("$.orientation", Matchers.equalTo('S')));
   }



}
