package com.losolved.nasa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.losolved.nasa.dto.InDateBetweenAndRobot;
import com.losolved.nasa.model.Register;
import com.losolved.nasa.services.RegisterService;
import com.losolved.nasa.services.impl.RegisterServiceImpl;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class RegisterController {

    private final RegisterService registerService;
  
    @Autowired
    public RegisterController(RegisterServiceImpl registerService) {
        this.registerService = registerService;
    }
    
     @GetMapping("/api/register/{id}")
    public List<Register> getRegister(@PathVariable UUID id) {
        return registerService.getRegisterFromRobo(id);
    }

    @PostMapping("/api/register/")
    public List<Register> getRegisterFromDateBetween(@RequestBody InDateBetweenAndRobot search) throws Exception {
        return registerService.getRegisterFromDateBetween(search);
    }
    

}