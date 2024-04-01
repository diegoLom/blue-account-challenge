package com.losolved.nasa.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record MessageReceiveDTO(UUID id, LocalDateTime date, byte[] register, Integer posX, Integer posY, Character orientation) {

}
