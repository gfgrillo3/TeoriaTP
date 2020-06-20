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
			
					//SI ES LA PRIMER VARIABLE DEL LADO DERECHO DE LA PRODUCCION
					//O SI ES UNA VARIABLE ANULABLE, ENTRO PARA VER LOS FIRST DEL SIMBOLO QUE SIGUE
					//SIEMPREY CUANDO, TENGA ALGÚN SÍMBOLO SIGUIENTE
					while(produccion.getCuerpo().size()>i && (i == 0 || isAnulable(gramatica.getProduccionesVariable(produccion.getCuerpo().get(i-1))))) {
						
						//ME GUARDO LOS FIRSTS DE LA VARIABLE EN LA QUE ESTOY
						char[] firstsVariable = getFirstVariable(produccion.getCuerpo().get(i), gramatica);
						
						//UNA VEZ QUE TENGO LOS FIRST, ME FIJO SI NO LOS HABÍA AGREGADO
						//SI LOS AGREGUE, NO LOS AGREGO, SINO SI
						for(char first : firstsVariable) {
							conjunto += (existeCharEnArray(conjunto.toCharArray(), first) ? "": first);
						}
	
						//REVISO EL SIGUIENTE SIMBOLO
						i++;
					}	
				}
		}

		return conjunto.toCharArray();
	}

	
	//SI HAY UNA PRODUCCION QUE DERIVA A EPSILON, EL SIMBOLO ES ANULABLE
	//FUNCION QUE RECIBE LAS PRODUCCIONES DE UNA VARIABLE
	//Y DETERMINA SI ES ANULABLE
	private static boolean isAnulable(List<Produccion> produccionesVariable) {		
		
		for(Produccion produccion : produccionesVariable) {
			if ((int)produccion.getCuerpo().get(0).charAt(0) == 35)
				return true;
		}		
		
		return false;
	}
	
	//FUNCION QUE RECIBE UN CHAR Y UN CHAR ARRAY
	//Y DEVUELVE SI EL CHAR YA EXISTIA EN EL CHAR ARRAY
	private static boolean existeCharEnArray(char[] charArray, char c) {
		
		for(char caracter : charArray )
			if(caracter == c)
				return true;
			
		return false;
	}
	
}
