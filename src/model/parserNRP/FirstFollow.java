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
			firstHashMap.put(variable.getVariable(), getFirstVariable(variable.getVariable(), gramatica));
		}

		return firstHashMap;
	}

	private static char[] getFirstVariable(String variable, Gramatica gramatica) {

		String conjunto = "";

		for (Produccion produccion : gramatica.getProduccionesVariable(variable)) {

			for (String cuerpoProduccion : produccion.getCuerpo()) {

				char primerCharProduccion = cuerpoProduccion.charAt(0);
				int asciiFirstCuerpoProduccion = (int) primerCharProduccion;

				// SI ES CHAR, LO AGREGO COMO FIRST
				if (97 <= asciiFirstCuerpoProduccion && asciiFirstCuerpoProduccion <= 122) 
					conjunto += primerCharProduccion;
				

				// si es EPSILON LO AGREGO AL FIRST
				else if (asciiFirstCuerpoProduccion == 35) 
					conjunto += primerCharProduccion;
				
				// SI ES VARIABLE PONGO LOS FIRST DE LA VARIABLE
				else 
					conjunto += new String(getFirstVariable(cuerpoProduccion, gramatica));
				

			}
		}

		return conjunto.toCharArray();
	}

}
