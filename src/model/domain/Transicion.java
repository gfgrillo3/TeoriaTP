package model.domain;

import java.util.ArrayList;
import java.util.List;

import model.logica.StringGenerator;

public class Transicion {

	List<Estado> estadoInicial;
	char input;
	List<Estado> estadoFinal;
	
	
	public Transicion(Estado estadoInicial, char input, Estado estadoFinal) {
		
		this.estadoInicial = new ArrayList<Estado>();
		this.estadoInicial.add(estadoInicial);
		this.input = input;
		this.estadoFinal = new ArrayList<Estado>();
		this.estadoFinal.add(estadoFinal);
	}

	
	public Transicion(Transicion transicion) {
		this.estadoInicial = new ArrayList<Estado>();
		this.estadoInicial = transicion.estadoInicial;
		this.input = transicion.input;
		this.estadoFinal = new ArrayList<Estado>();
		this.estadoFinal = transicion.estadoFinal;
	}


	public Transicion(List<Estado> estadoInicial, char input, List<Estado> estadoFinal) {
		this.estadoInicial  =estadoInicial;
		this.input = input;
		this.estadoFinal = estadoFinal;		
	}



	public List<Estado> getEstadoInicial() {
		return estadoInicial;
	}


	public void setEstadoInicial(List<Estado> estadoInicial) {
		this.estadoInicial = estadoInicial;
	}


	public char getInput() {
		return input;
	}


	public void setInput(char input) {
		this.input = input;
	}


	public List<Estado> getEstadoFinal() {
		return estadoFinal;
	}
	

	public void setEstadoFinal(List<Estado> estadoFinal) {
		this.estadoFinal = estadoFinal;
	}
	
	public void addEstadoFinal(Estado estado) {
		this.estadoFinal.add(estado);
	}
	
	@Override
	public String toString() {
		
		
		return StringGenerator.toStringListEstados(this.estadoInicial) +
				" , "+this.input+
				" ---> "+StringGenerator.toStringListEstados(estadoFinal);
		
		
	}
}
