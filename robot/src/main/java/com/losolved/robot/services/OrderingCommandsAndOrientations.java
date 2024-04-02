package com.losolved.robot.services;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class OrderingCommandsAndOrientations {
	
	public static BiMap<Integer, Character> orientations = HashBiMap.create();
	
	public static BiMap<Integer, Character> commands = HashBiMap.create();
	
	static {
		
		orientations.put(0, 'N');
		orientations.put(1, 'E' );
		orientations.put(2, 'S' );
		orientations.put(3, 'W' );
		
		commands.put(-1, 'L');
		commands.put(0, 'M');
		commands.put(1, 'R');
		
	}
	
	public static int getCurrentPosition(Character x) {
		return orientations.inverse().get(x);
	}
	
	public static Character getCurrentPosition(Integer x) {
		return orientations.get(x);
	}
	
	public static int getCommandRotation(Character x) {
		return commands.inverse().get(x);
	}
	
	public static Character getCommandRotation(Integer x) {
		return commands.get(x);
	}
	
	

}
