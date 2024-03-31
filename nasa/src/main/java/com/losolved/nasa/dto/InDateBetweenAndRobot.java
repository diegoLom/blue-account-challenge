package com.losolved.nasa.dto;

import java.time.LocalDateTime;

import com.losolved.nasa.model.Robot;

public record InDateBetweenAndRobot(LocalDateTime startRegister, LocalDateTime endRegister, Robot robot) {

}


