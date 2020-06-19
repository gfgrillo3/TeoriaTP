package model.parserNRP;

import java.util.HashMap;
import java.util.List;

import model.domain.Gramatica;
import model.domain.Produccion;
import model.domain.Variable;

public class FirstFollow {

	
	public HashMap<Variable, char[]> getFirst(Gramatica gramatica) {
		
		HashMap<Variable, char[]> a;
		
		String terminales = "";
		
		for(Variable v : gramatica.getVariables()) {
			
		}
		
		return null; 
	}

	public char[] getFollow(Gramatica gramatica) {
		
		String terminales = "";
		
		for(Variable v : gramatica.getVariables()) {
			
		}
		
		return terminales.toCharArray(); 
	}
	
	private char[] getFirstVariable(Variable variable, List<Produccion> producciones) {
		
		String conjunto = "";

		for(Produccion p : producciones) {			
			p.getCuerpo().get(0);
			//si es char agregamos de 1a
			//si es /epsilon lo agregamos
			//si es variable getFirstVariable;
				//conjunto += new String(getFirstVariable(Variable, ))
				//ret 
	
		}
		
		return conjunto.toCharArray();
	}
	
//	private List<Produccion> getProduccionesVar(Gramatica gramatica){
		
//	}
	
}
