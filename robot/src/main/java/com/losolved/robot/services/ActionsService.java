package com.losolved.robot.services;

import com.losolved.robot.model.Position;

public interface ActionsService {
	
	public Position executeMovement(String comando);
	public void updatePosition(Position position);
	public byte[] takePicture();

}
