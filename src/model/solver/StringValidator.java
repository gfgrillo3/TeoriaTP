package model.solver;

import model.domain.AFD;

public class StringValidator {

	
	public static boolean validarString(AFD afd, String string) {
		
		if(string == null  || string == "")
			return false;
		
		return validarCaracteres(afd.getAlfabetoInput(), string);
		
	}
	
	
	private static boolean validarCaracteres(char[] alfabeto, String string) {
		
		boolean caracterValido = false;
		for(char charString : string.toCharArray()) {
			for(char charAlfabeto : alfabeto) {
				if(charString == charAlfabeto) {
					caracterValido = true;
					break;
				}
			}
			
			if(!caracterValido)
				return false;
			
			caracterValido = false;
		}
		
		return true;
		
	}
	
}
