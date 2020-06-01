package model.domain;

import java.util.ArrayList;
import java.util.List;

public class AFD {

	// PROBABLEMENTE LIST STRING?
	char[] alfabetoInput;
	Estado estadoInicial = new Estado(1);
	List<Estado> estados;
	List<Estado> estadosFinales;

	List<Transicion> transiciones;

	// PROBABLEMENTE SE RECIBA LA LISTA DE ESTADIS DIRECTAMENTE YA QUE SE ESTA
	// CONVIRTIENDO DE UN AFND
	// POR ESTO, NO SE RECIBIRÍA CANT ESTADOS
	public AFD(char[] alfabetoInput, int cantEstados, int[] estadosFinales, List<Transicion> transiciones) {

		this.alfabetoInput = alfabetoInput;
		this.inicializarEstados(cantEstados);
		this.inicializarEstadosFinales(estadosFinales);

		this.inicializarTransiciones(transiciones);

	}
	
	// INICIALIZAR ESTADOS DEL AFD, COMIENZA EN 1 Y SON CORRELATIVOS POR DEFINICION
		private void inicializarEstados(int cantEstados) {

			this.estados = new ArrayList<Estado>();

			int i = 1;

			while (i <= cantEstados) {
				this.estados.add(new Estado(i));
				i++;
			}
		}

		private void inicializarEstadosFinales(int[] estadosFinales) {

			this.estadosFinales = new ArrayList<Estado>();

			for (int i : estadosFinales) {
				this.estadosFinales.add(new Estado(i));
			}
		}

		private void inicializarTransiciones(List<Transicion> transiciones) {

			this.transiciones = transiciones;

			// ORDENAR LAS TRANSICIONES POR EL ESTADO INICIAL
			// HASTA AQUI LOS ESTADOS SON DE UN UNICO VALOR
			/*
			 * Collections.sort(this.transiciones, (unaTransicion, otraTransicion) ->
			 * unaTransicion.estadoInicial.getValor() <
			 * otraTransicion.estadoInicial.getValor() ? -1 :
			 * unaTransicion.estadoInicial.getValor() ==
			 * otraTransicion.estadoInicial.getValor() ? 0 : 1);
			 */
		}

		public List<Transicion> getTransiciones() {
			return this.transiciones;
		}

	
}
