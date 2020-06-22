package model.domain;

import java.util.ArrayList;
import java.util.List;

public class Gramatica {

	char[] terminales;
	List<Variable> variables;
	Variable simboloInicial = new Variable("X_{1}") ;
	List<Produccion> producciones;
	
	public Gramatica(List<Produccion> producciones) {
		this.producciones = producciones;
		crearVariables(this.producciones);
		crearTerminales(this.producciones);
		System.out.println(this.toString());
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
	
	private void crearVariables(List<Produccion> producciones){
		
		List<Variable> variables = new ArrayList<Variable>();
		
		for(Produccion produccion: producciones) {
			for(String simbolo : produccion.cuerpo)
				if(simbolo.length()>1 && !existeVariable(simbolo, variables))
					variables.add(new Variable(simbolo));
			if(!existeVariable(produccion.getVariable().getStringVariable(), variables))
				variables.add(produccion.getVariable());
		}
		
		this.variables = variables;
	}
	
	private boolean existeVariable(String variable, List<Variable> variables) {
		for(Variable v : variables)
			if(v.getStringVariable().equals(variable))
				return true;
		return false;
	}
	
	private void crearTerminales(List<Produccion> producciones){
		String conjuntoTerminales = "";
		
		for(Produccion produccion: producciones)
			for(String simbolo: produccion.cuerpo)
				if(simbolo.length()==1 && !conjuntoTerminales.contains(simbolo))
					conjuntoTerminales += simbolo;
		
		this.terminales = conjuntoTerminales.toCharArray();
	}

	public List<Produccion> getProduccionesVariable(String variable){
		
		List<Produccion> produccionesVariable = new ArrayList<Produccion>();
		
		for(Produccion produccion : this.producciones) {
			if(produccion.getVariable().getStringVariable().equals(variable))
				produccionesVariable.add(produccion);
		}
		
		return produccionesVariable;
	}
	
	public List<Produccion> getProduccionesFollow(String variable){
		
		List<Produccion> produccionesFollow = new ArrayList<Produccion>();
		
		for(Produccion produccion : this.producciones) {
			for(String var : produccion.getCuerpo())
				if(var.equals(variable))
					produccionesFollow.add(produccion);
		}
		
		return produccionesFollow;
	}
	
	
	@Override
	public String toString() {
		String ret = "";
		
		ret += "SIMBOLO INICIAL = "+this.simboloInicial.getStringVariable()+"\n";
		
		ret +="TERMINALES = [ ";
		
		for(char c : this.terminales)
			ret += c+",";
		
		ret+= "]\n";
		
		ret+= "VARIABLES = ";
		
		for(Variable var : this.variables)
			ret +=var.getStringVariable()+", ";
		
		ret +="\nPRODUCCIONES------------------------------";
		
		for(Produccion prod : this.producciones) {
			ret +="\n";
			ret += prod.getVariable().getStringVariable()+" -> ";
			for(String cuerpo : prod.getCuerpo())
				ret +=cuerpo;
		}
		ret +="\n";
		
		return ret;
	}
	
}
