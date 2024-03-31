package com.losolved.nasa.controller;

import com.losolved.nasa.dto.PositionDTO;
import com.losolved.nasa.dto.ResponseDTO;
import com.losolved.nasa.model.Robot;
import com.losolved.nasa.services.RobotService;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RobotController {

    private final RobotService robotService;

    @Autowired
    public RobotController(RobotService robotService) {
        this.robotService = robotService;
    }

    @PostMapping("/api/robot")
    public ResponseEntity<ResponseDTO> launchRobot(@RequestBody Robot robot) {
        ResponseDTO responseDTO = robotService.launch(robot);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PostMapping("/api/robot/position")
    public ResponseEntity<PositionDTO> getRobotPosition(@RequestBody UUID robotId) {
        PositionDTO positionDTO = robotService.getRobotPosition(robotId);
        return ResponseEntity.ok(positionDTO);
    }
}