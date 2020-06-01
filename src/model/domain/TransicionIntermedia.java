package model.domain;

import java.util.ArrayList;
import java.util.List;

public class TransicionIntermedia {

	List<Estado> estadoInicial;
	char input;
	List<Estado> estadoFinal;
	
	



	public TransicionIntermedia(Transicion transicion) {
		this.estadoInicial = new ArrayList<Estado>();
		this.estadoInicial.add(transicion.estadoInicial);
		this.input = transicion.input;
		this.estadoFinal = new ArrayList<Estado>();
		this.estadoFinal.add(transicion.estadoFinal);
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
}
