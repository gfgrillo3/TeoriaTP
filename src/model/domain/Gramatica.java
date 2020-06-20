package model.domain;

import java.util.ArrayList;
import java.util.List;

public class Gramatica {

	char[] terminales;
	List<Variable> variables;
	Variable simboloInicial = new Variable("X_{1}") ;
	List<Produccion> producciones;
	
	public Gramatica(List<Produccion> producciones, List<Variable> variables, char[] terminales) {
		this.producciones = producciones;
		this.variables = variables;
		this.terminales = terminales;
	}

	public char[] getTerminales() {
		return terminales;
	}

	public void setTerminales(char[] terminales) {
		this.terminales = terminales;
	}

	public List<Variable> getVariables() {
		return variables;
	}

	public void setVariables(List<Variable> variables) {
		this.variables = variables;
	}

	public Variable getSimboloInicial() {
		return simboloInicial;
	}

	public void setSimboloInicial(Variable simboloInicial) {
		this.simboloInicial = simboloInicial;
	}

	public List<Produccion> getProducciones() {
		return producciones;
	}

	public void setProducciones(List<Produccion> producciones) {
		this.producciones = producciones;
	}
	
	public List<Produccion> getProduccionesVariable(String variable){
		
		List<Produccion> produccionesVariable = new ArrayList<Produccion>();
		
		for(Produccion produccion : this.producciones) {
			if(produccion.getVariable().getStringVariable().equals(variable))
				produccionesVariable.add(produccion);
		}
		
		return produccionesVariable;
	}
	
}
