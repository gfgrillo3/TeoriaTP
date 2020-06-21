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
					boolean esAnulable = true;
			
					//SI ES LA PRIMER VARIABLE DEL LADO DERECHO DE LA PRODUCCION
					//O SI ES UNA VARIABLE ANULABLE, ENTRO PARA VER LOS FIRST DEL SIMBOLO QUE SIGUE
					//SIEMPREY CUANDO, TENGA ALGÚN SÍMBOLO SIGUIENTE
					while(produccion.getCuerpo().size()>i && (i == 0 || isAnulable(gramatica.getProduccionesVariable(produccion.getCuerpo().get(i-1))))) {
						
						esAnulable = isAnulable(gramatica.getProduccionesVariable(produccion.getCuerpo().get(i)));
						
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
					
					//si no eran todos anulables, no agrego el epsilon
					if(!esAnulable)
						conjunto = conjunto.replaceAll("[#]", "");
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
	
	//---------------------------------FOLLOW-----------------------------------//
	
	public static HashMap<String, char[]> getFollow(Gramatica gramatica){
		
		//OBTENGO LOS FIRST DE LA GRAMATICA
		HashMap<String, char[]> firstHashMap = new HashMap<String, char[]>();
		firstHashMap = getFirst(gramatica);
		
		HashMap<String, char[]> followHashMap = new HashMap<String, char[]>();
		
		//RECORRO CADA VARIABLE Y BUSCO SUS FOLLOWS PARA AGREGAR AL HASHMAP
		for(Variable variable: gramatica.getVariables()) {
			followHashMap.put(variable.getStringVariable(), getFollowVariable(variable.getStringVariable(),gramatica, firstHashMap));
		}
		
		return followHashMap;
	}

	private static char[] getFollowVariable(String variable, Gramatica gramatica,
			HashMap<String, char[]> firstHashMap) {
		
		String conjunto = "";
		
		if(variable.equals(gramatica.getSimboloInicial().getStringVariable()))
			conjunto += "$";
		
		//RECORRO CADA PRODUCCION DONDE APARECE LA variable
		for(Produccion produccion : gramatica.getProduccionesFollow(variable)) {
			//DE CADA PRODUCCION RECORRO TODA SU PARTE DERECHA BUSCANDO LA variable
			for(int i = 0; produccion.getCuerpo().size()>i; i++) {
				//SI Yi ES UN TERMINAL NO HAGO NADA
				if(!esTerminal(produccion.getCuerpo().get(i))) {
					
					//flag para ver si era anulable, lo uso para agregar los follow de variable
					boolean esAnulable = false;
					
					//SI Yi ES UNA VARIABLE PRIMERO CHEQUEO SI ES IGUAL A variable
					//LUEGO TOMO LOS FIRST DE LA DERECHA (SI EXISTE) 
					if(produccion.getCuerpo().get(i).equals(variable) && i+1<produccion.getCuerpo().size()) {
						
						//agrego los first del simbolo que sigue
						conjunto += new String(getFirstVariable(produccion.getCuerpo().get(i+1), gramatica));
						
						//indice para recorrer lo que sigue al simbolo en el que estoy
						int j = i+1;
						esAnulable = isAnulable(gramatica.getProduccionesVariable(produccion.getCuerpo().get(i+1)));
						
						//mientras tenga un simbolo siguiente y ademas sea anulable
						//voy a fijarme los first de lo que le sigue al simbolo ese
						while(j+1<produccion.getCuerpo().size() && esAnulable) {
							
							//si no existia agrego los char
							conjunto += new String(getFirstVariable(produccion.getCuerpo().get(j+1), gramatica));
							
							
							esAnulable =  isAnulable(gramatica.getProduccionesVariable(produccion.getCuerpo().get(j+1)));
							j++;
						}
						
						
						
						//ELIMINO EL EPSILON
						conjunto = conjunto.replaceAll("[#]", "");
					}
					
					//SI ERA TODO ANULABLE, AGREGO LOS FOLLOWS DE VARIABLE
					if(esAnulable)
						conjunto += new String(getFollowVariable(produccion.getVariable().getStringVariable(),
								gramatica, firstHashMap));
					/*
					//SI A LA DERECHA NO HAY MAS NADA O SI ES ANULABLE AGREGO LOS FOLLOWS DE variable
					if(produccion.getCuerpo().get(i).equals(variable) && (i+1 == produccion.getCuerpo().size() || 
							isAnulable(gramatica.getProduccionesVariable(produccion.getCuerpo().get(i+1))))) {
						conjunto += new String(getFollowVariable(produccion.getVariable().getStringVariable(),
								gramatica, firstHashMap));
					}
					*/
				}
			}
			
		}
		
		return conjunto.toCharArray();
	}
	
	private static boolean esTerminal(String string) {
		char primerCharProduccion = string.charAt(0);
		int asciiFirstCuerpoProduccion = (int) primerCharProduccion;
		
		return ((97 <= asciiFirstCuerpoProduccion && asciiFirstCuerpoProduccion <= 122)
				|| asciiFirstCuerpoProduccion == 35);
	}
		
	
}
