package model.solver;

import model.domain.AFD;

public class AFDSolver {
	
	
	public static String resolver(AFD afd, String string) {
		
		if(!StringValidator.validarString(afd, string))
			return "STRING INVALIDO";
		
		if(afd.procesarString(string)) 
			return "ACEPTADO";
			
		return "RECHAZADO";

	}

}
