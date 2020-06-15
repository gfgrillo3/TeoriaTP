package model.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AFND {

	// PROBABLEMENTE LIST STRING?
	char[] alfabetoInput;
	List<Estado> estadoInicial;
	List<Estado> estados;
	List<Estado> estadosFinales;
	List<Transicion> transiciones;

	public AFND(char[] alfabetoInput, int cantEstados, List<Integer> estadosFinales, List<Transicion> transiciones) {

		this.alfabetoInput = alfabetoInput;
		this.inicializarEstados(cantEstados);
		this.inicializarEstadosFinales(estadosFinales);

		this.inicializarTransiciones(transiciones);

	}

	// INICIALIZAR ESTADOS DEL AFD, COMIENZA EN 1 Y SON CORRELATIVOS POR DEFINICION
	private void inicializarEstados(int cantEstados) {

		this.estadoInicial = new ArrayList<>();
		this.estadoInicial.add(new Estado(1));
		
		this.estados = new ArrayList<Estado>();

		int i = 1;

		while (i <= cantEstados) {
			this.estados.add(new Estado(i));
			i++;
		}
	}

	private void inicializarEstadosFinales(List<Integer> estadosFinales) {

		this.estadosFinales = new ArrayList<Estado>();

		for (int i : estadosFinales) {
			this.estadosFinales.add(new Estado(i));
		}
	}

	private void inicializarTransiciones(List<Transicion> transiciones) {

		this.transiciones = transiciones;		
		
		//ORDENAR DE MENOR A MAYOR POR VALOR DE ESTADO, por char y por estado final
		Collections.sort(this.transiciones, new Comparator<Transicion>(){
			
			@Override
			public int compare(Transicion transicion, Transicion otraTransicion) {

				if(transicion.estadoInicial.get(0).getValor() > otraTransicion.getEstadoInicial().get(0).getValor())
					return 1;
				else if(transicion.estadoInicial.get(0).getValor() < otraTransicion.getEstadoInicial().get(0).getValor())
					return -1;
				else if(transicion.getInput() > otraTransicion.getInput())
					return 1;
				else if(transicion.getInput() < otraTransicion.getInput() )
					return -1;
				else if(transicion.estadoFinal.get(0).getValor() > otraTransicion.getEstadoFinal().get(0).getValor())
					return 1;
				else if(transicion.estadoFinal.get(0).getValor() < otraTransicion.getEstadoFinal().get(0).getValor())
					return -1;
				return 0;
			}
			 });
		
		
	}

	public List<Transicion> getTransiciones() {
		return this.transiciones;
	}
	
	public List<Estado> getEstadosFinales() {
		return this.estadosFinales;
	}
	
	public char[] getAlfabetoInput() {
		return this.alfabetoInput;
	}
}
