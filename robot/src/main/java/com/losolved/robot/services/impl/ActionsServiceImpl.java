package com.losolved.robot.services.impl;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.losolved.robot.RobotApplication;
import com.losolved.robot.configuration.ByteArraySerializer;
import com.losolved.robot.dto.MessageReceiveDTO;
//import com.losolved.robot.dto.PublishMessageDTO;
import com.losolved.robot.model.Position;
import com.losolved.robot.repositories.PositionRepository;
import com.losolved.robot.services.ActionsService;
import com.losolved.robot.services.MathEquations;
import com.losolved.robot.services.OrderingCommandsAndOrientations;
import com.losolved.robot.services.PublishMessageService;

@Service
public class ActionsServiceImpl implements ActionsService {

	@Value("${robot.uuid.parameter}")
	private String ID_APPLICATION;

	@Autowired
	private PositionRepository positionRepository;

	@Autowired
	private PublishMessageService publishMessageService;

	public void process(String comando) {
		Position position = executeMovement(comando);
		byte[] register = takePicture();
		updatePosition(position);
		publishMessageService.sendMessage(prepareMessage(position, register));
	}

	public Position executeMovement(String comando) {
		// TODO Auto-generated method stub
		Position position = getRoboPosition();

		Character cOrientation = position.getOrientation();
		int currentOrientation = OrderingCommandsAndOrientations.getCurrentPosition(cOrientation);
		char[] charArray = comando.toCharArray();

		for (char c : charArray) {
			if (c != '"') {
				Integer commandRotation = OrderingCommandsAndOrientations.getCommandRotation(c);
				if (commandRotation != 0) {
					currentOrientation = rotate(commandRotation, currentOrientation);

					System.out.println(OrderingCommandsAndOrientations.getCurrentPosition(currentOrientation));
				} else {
					position = move(position, currentOrientation);

				}
			}
		}

		position.setOrientation(OrderingCommandsAndOrientations.getCurrentPosition(currentOrientation));

		return position;

	}

	public static int rotate(int commandRotation, int currentOrientation) {
		int newOrientation = 0;
		if (commandRotation > 0) {
			newOrientation = MathEquations.increase(currentOrientation);
		} else {
			newOrientation = MathEquations.decrease(currentOrientation);
		}

		return newOrientation;
	}

	public static int back_orientation_x = 3;
	public static int back_orientation_y = 2;

	public static Position move(Position position, int currentOrientation) {
		if (currentOrientation % 2 == 0) {
			position.setPosY(
					back_orientation_y == currentOrientation ? position.getPosY() - 1 : position.getPosY() + 1);
		} else {
			position.setPosY(
					back_orientation_x == currentOrientation ? position.getPosX() - 1 : position.getPosX() + 1);

		}

		return position;

	}

	public Position getRoboPosition() {

		UUID id = UUID.fromString(ID_APPLICATION);
		java.util.Optional<Position> oPosition = positionRepository.findById(id);

		Position saved = null;
		if (!oPosition.isPresent()) {
			saved = getInitialPosition();
		} else {
			saved = oPosition.get();
		}

		return saved;
	}

	public Position getInitialPosition() {
		Position position = Position.builder().id(UUID.fromString(ID_APPLICATION)).posX(0).posY(0).orientation('N')
				.build();
		Position saved = positionRepository.save(position);
		return saved;
	}

	@Override
	public byte[] takePicture() {
		// TODO: I'm Waiting Nasa send robot instructions to connect with the camera and
		// take a real photo :D
		Random rd = new Random();
		byte[] arr = new byte[7];
		rd.nextBytes(arr);
		return arr;

	}

	@Override
	public void updatePosition(Position position) {
		// TODO Auto-generated method stub
		positionRepository.save(position);
	}

	private String prepareMessage(Position position, byte[] register) {

		MessageReceiveDTO messageDTO = new MessageReceiveDTO(ID_APPLICATION, LocalDateTime.now(),
				register, position.getPosX(), position.getPosY(), position.getOrientation());
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer(byte[].class, new ByteArraySerializer());
		objectMapper.registerModule(module);

		objectMapper.registerModule(new ParameterNamesModule()).registerModule(new JavaTimeModule());

		String jsonMessage = "";
		try {
			jsonMessage = objectMapper.writeValueAsString(messageDTO);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonMessage;
	}

}
