package model.parserNRP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.domain.Gramatica;
import model.domain.Produccion;
import model.domain.Variable;

public class FirstFollow {

	public static HashMap<String, char[]> getFirst(Gramatica gramatica) {

		HashMap<String, char[]> firstHashMap = new HashMap<String, char[]>();

		// CREO LOS FIRST DE LOS TERMINALES QUE SON LOS MISMOS TERMINALES
		for (char terminal : gramatica.getTerminales()) {
			char[] terminalCharArray = new char[] { terminal };
			firstHashMap.put(new String(terminalCharArray), terminalCharArray);
		}

		// CREO LOS FIRST DE LAS VARIABLES CON LOS ALGORITMOS VISTOS
		for (Variable variable : gramatica.getVariables()) {
			firstHashMap.put(variable.getStringVariable(), getFirstVariable(variable.getStringVariable(), gramatica));
		}

		return firstHashMap;
	}

	private static char[] getFirstVariable(String variable, Gramatica gramatica) {

		String conjunto = "";

		// SI ES CHAR, LO AGREGO COMO FIRST
		if (97 <= variable.charAt(0) && variable.charAt(0) <= 122) 
			conjunto += variable.charAt(0);
		
		
		for (Produccion produccion : gramatica.getProduccionesVariable(variable)) {

				char primerCharProduccion = produccion.getCuerpo().get(0).charAt(0);
				int asciiFirstCuerpoProduccion = (int) primerCharProduccion;

				// SI ES CHAR O EPSILON, LO AGREGO COMO FIRST
				if ((97 <= asciiFirstCuerpoProduccion && asciiFirstCuerpoProduccion <= 122) || asciiFirstCuerpoProduccion == 35) 
					conjunto += (existeCharEnArray(conjunto.toCharArray(), primerCharProduccion) ? "": primerCharProduccion);

				// SI ES VARIABLE PONGO LOS FIRST DE LA VARIABLE
				else {
					int i = 0;
			
					while(produccion.getCuerpo().size()>i && (i == 0 || isAnulable(gramatica.getProduccionesVariable(produccion.getCuerpo().get(i-1))))) {
						char[] firstsVariable = getFirstVariable(produccion.getCuerpo().get(i), gramatica);
						
						for(char first : firstsVariable) {
							conjunto += (existeCharEnArray(conjunto.toCharArray(), first) ? "": first);
						}
	
						i++;
					}	
				}
		}

		return conjunto.toCharArray();
	}

	
	private static boolean isAnulable(List<Produccion> produccionesVariable) {		
		
		for(Produccion produccion : produccionesVariable) {
			if ((int)produccion.getCuerpo().get(0).charAt(0) == 35)
				return true;
		}		
		
		return false;
	}
	
	
	private static boolean existeCharEnArray(char[] charArray, char c) {
		
		for(char caracter : charArray )
			if(caracter == c)
				return true;
			
		return false;
	}
	
}
