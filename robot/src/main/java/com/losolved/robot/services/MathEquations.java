package com.losolved.robot.services;

public class MathEquations {
	
	public static int increase(int numero) {
        numero = (numero + 1) % 4; 
        return numero;
    }

    public static int decrease(int numero ) {
        numero = (numero - 1 + 4) % 4; 
        return numero;
    }

}
