package com.losolved.nasa.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.losolved.nasa.dto.InDateBetweenAndRobot;
import com.losolved.nasa.model.Register;
import com.losolved.nasa.services.NasaMocked;
import com.losolved.nasa.services.RegisterService;
import com.losolved.nasa.services.impl.RegisterServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(RegisterControllerTest.class)
public class RegisterControllerTest {

	private RegisterService registerService;

	@Autowired
	public RegisterControllerTest(RegisterServiceImpl registerService) {
		super();
		this.registerService = registerService;
	}

	@MockBean
	private MockMvc mockMvc;

	private static ObjectMapper mapper = new ObjectMapper();

	@BeforeAll
	public static void setup() {
		mapper = new ObjectMapper().registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module())
				.registerModule(new JavaTimeModule());
	}

	@Test
	public void givenARetrieveRegisterObject() throws Exception {
		List<Register> registerFiltered = NasaMocked.getMockedRegisterOf(NasaMocked.OPPORTUNITY);
		Register register = NasaMocked.getMockedRegister();
		given(registerService.getRegisterFromRobo(NasaMocked.OPPORTUNITY.getId())).willReturn(registerFiltered);

		mockMvc.perform(get("/api/register/".concat(register.getId().toString()))).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(registerFiltered.size())));
	}
	

    @Test
    public void givenRegisterByDate() throws Exception {
	
		InDateBetweenAndRobot search = new InDateBetweenAndRobot(NasaMocked.REGISTER_MADE_IN_PAST_FOUR, NasaMocked.REGISTER_MADE_IN_PAST_FIVE, NasaMocked.OPPORTUNITY);
		List<Register> registerFiltered = NasaMocked.getMockedRegister(search);
		  given(registerService.getRegisterFromDateBetween(search)).willReturn(registerFiltered);
    	  String json = mapper.writeValueAsString(search);
      	  
    	  mockMvc.perform(post("/api/register/").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                  .content(json)). //.accept(MediaType.APPLICATION_JSON)).
                  andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(registerFiltered.size())))
                  ;
    }

}
