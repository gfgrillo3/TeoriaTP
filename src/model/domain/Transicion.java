package model.domain;

import java.util.Comparator;

public class Transicion{
	
	Estado estadoInicial;
	char input;
	Estado estadoFinal;
	
	public Transicion(Estado estadoInicial, char input, Estado estadoFinal) {
		
		this.estadoInicial = estadoInicial;
		this.input = input;
		this.estadoFinal = estadoFinal;
	}

	

	public Estado getEstadoInicial() {
		return estadoInicial;
	}

	public char getInput() {
		return input;
	}

	public Estado getEstadoFinal() {
		return estadoFinal;
	}

 
	
	
}
