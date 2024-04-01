package com.losolved.nasa.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.losolved.nasa.dto.MessageQueue;
import com.losolved.nasa.dto.ResponseDTO;
import com.losolved.nasa.services.PublishMessageService;

@RunWith(SpringRunner.class)
@WebMvcTest(NavigationController.class)
public class NavigationControllerTest {

	@MockBean
	private PublishMessageService publishMessage;
	
	@Autowired
	private MockMvc mockMvc;

	private static ObjectMapper mapper = new ObjectMapper();

	@BeforeAll
	public static void setup() {
		mapper = new ObjectMapper().registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module())
				.registerModule(new JavaTimeModule());

	}

	@Test
	public void givenAPublishing() throws Exception {
		given(publishMessage.sendMessage(ArgumentMatchers.any(), anyString()))
				.willReturn( new ResponseDTO("Published successfully", HttpStatus.OK.value() ));
		
		
		MessageQueue message = new MessageQueue("LRM", UUID.randomUUID());
		
		String json = mapper.writeValueAsString(message);
		mockMvc.perform(
				post("/api/navigation").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(json))
				. // .accept(MediaType.APPLICATION_JSON)).
				andExpect(status().isOk()).andExpect(jsonPath("$.code", Matchers.equalTo(200)))
				.andExpect(jsonPath("$.message", Matchers.equalTo("Published successfully")));
	}

}
