package model.domain;

import java.util.List;

public class Gramatica {

	char[] terminales;
	List<Variable> variables;
	Variable simboloInicial = new Variable("X_{1}") ;
	List<Produccion> producciones;
	
	
	public Gramatica(List<Produccion> producciones) {
		this.producciones = producciones;
	}
	
}
