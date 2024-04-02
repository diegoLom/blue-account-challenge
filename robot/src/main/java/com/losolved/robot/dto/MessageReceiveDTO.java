package com.losolved.robot.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record MessageReceiveDTO(String id, LocalDateTime date, byte[] register, Integer posX, Integer posY, Character orientation)  {

}
