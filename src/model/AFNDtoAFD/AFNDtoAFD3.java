package model.AFNDtoAFD;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import model.domain.AFD;
import model.domain.AFND;
import model.domain.Estado;
import model.domain.Transicion;
import model.domain.TransicionIntermedia;

public class AFNDtoAFD3 {

	private List<TransicionIntermedia> transicionesIntermedias;
	private List<List<Estado>> conjuntoDeEstadosInicialesAFD;
	private AFND afnd;

	public AFD fromAFNDtoAFD(AFND afnd) {

		this.afnd = afnd;
		this.transicionesIntermedias = new ArrayList<TransicionIntermedia>();
		this.conjuntoDeEstadosInicialesAFD = new ArrayList<>();

		ArrayList<Estado> estadoInicial = new ArrayList<>();
		estadoInicial.add(new Estado(1));

		this.conjuntoDeEstadosInicialesAFD.add(estadoInicial);

		for (int i = 0; i < this.conjuntoDeEstadosInicialesAFD.size(); i++) {
			this.agregarTransicionesIntermedias(conjuntoDeEstadosInicialesAFD.get(i));
		}
		
		
		//PRINT DE TRANSICIONES INTERMEDIAS
		/*
		  for(TransicionIntermedia t : this.transicionesIntermedias) {
		  System.out.print("EI = { "); for(Estado ei : t.getEstadoInicial()) {
		  System.out.print(ei.getValor() + " , "); } System.out.print(" } ");
		  System.out.print( " , INPUT = "+t.getInput());
		  System.out.print("   -> EF = { "); for(Estado ef : t.getEstadoFinal()) {
		  System.out.print(ef.getValor() + " , "); } System.out.println(""); }
		 */
		
		  //PRINT DE ESTADOS INICIALES DEL AFD
		  /*
		  System.out.println("CHEQUEO CONJUNTOS");
		  for(List<Estado> estadosIniciales: this.conjuntoDeEstadosInicialesAFD){
			  System.out.print("EI = ");
			  for(Estado estado: estadosIniciales) {
				  System.out.print(estado.getValor()+" ");
			  }
			  System.out.println("");
		  }
		  */
		return null;
	}

	// Agrego transiciones intermedias de una lista de estados
	private void agregarTransicionesIntermedias(List<Estado> estados) {

		// esta es la lista de transicionesIntermedias del conjunto de estados pasado
		// como parametro
		List<TransicionIntermedia> transicionesIntermediasConjunto = new ArrayList<>();

		// Repito la operacion para todos los estados deel conjunto pasado como
		// parametro
		for (Estado estado : estados) {
			//System.out.println("BUSCO TRANSICIONES DEL ESTADO " + estado.getValor());

			// recorro las transiciones del automata para ver las transiciones del estado
			// que estoy recorriendo
			// y agregarlas como transiciones intermedias
			for (Transicion transicion : this.afnd.getTransiciones()) {

				// SI es una transicion de uno de los estados del parametro
				if (transicion.getEstadoInicial().getValor() == estado.getValor()) {

					// si no tengo ninguna transicion intermedia, me creo una nueva transicion, y le
					// seteo como estado inicial
					// el conjunto que recibi como parametro, luego la agrego.
					if (transicionesIntermediasConjunto.isEmpty()) {
						TransicionIntermedia transicionNueva = new TransicionIntermedia(transicion);
						transicionNueva.setEstadoInicial(estados);
						transicionesIntermediasConjunto.add(transicionNueva);
					}

					// SI TENGO UNA TRANSICION INTERMEDIA, ME FIJO SI TENGO UNA CON ESE char de
					// INPUT
					else if (this.existeTransicionConChar(transicion.getInput(), transicionesIntermediasConjunto)) {
						this.agregarEstadoFinal(transicion.getInput(), transicion.getEstadoFinal(),
								transicionesIntermediasConjunto);
					}

					// SI NO TENGO CON ESE INPUT, CREO UNA NUEVA TRANSICION INTERMEDIA
					// PARA EL SIMBOLO INICIAL CON EL INPUT. Ademas le seteo como estado inicial el
					// conjunto de estados de parametro
					else {
						TransicionIntermedia transicionIntermediaNueva = new TransicionIntermedia(transicion);
						transicionIntermediaNueva.setEstadoInicial(estados);
						transicionesIntermediasConjunto.add(transicionIntermediaNueva);
					}

				}
			}
		}

		// HASTA ACA TENGO TODAS LAS TRANSICIONES INTERMEDIAS DEL CONJUNTO DE ESTADOS
		// RECIBIDO COMO PARAMETRO
		// ESTAS ESTAN ALMACENADAS EN transicionesIntermediasConjunto

		// PRIMERO TENGO QUE ORDENAR CADA LISTA DE ESTADOS FINALES
		// AHORA HAY QUE CHEQUEAR SI LOS ESTADOS FINALES GENERADOS EN LA LISTA
		// transicionesIntermediasConjunto
		// YA LOS AGREGUE AL CONJUNTO SOBRE EL QUE ESTOY ITERANDO (ESTADOS INICIALES
		// AFD). SI YA LOS AGREGUE, NO LOS AGREGO
		// SI NO LOS AGREGUE, LOS AGREGO Y QUE SIGA ITERANDO
		
		for (TransicionIntermedia transicionIntermedia : transicionesIntermediasConjunto) {
			transicionIntermedia.setEstadoFinal(this.ordenarListaEstados(transicionIntermedia.getEstadoFinal()));
			this.transicionesIntermedias.add(transicionIntermedia);
			this.agregarEstadoFinalComoInicialDeAFD(transicionIntermedia.getEstadoFinal());
		}

	}

	// Metodo para saber si tengo una transicion intermedia con un input
	// para el símbolo inicial
	private boolean existeTransicionConChar(char input, List<TransicionIntermedia> transicionesIntermedias) {

		for (TransicionIntermedia transicionIntermedia : transicionesIntermedias) {
			if (transicionIntermedia.getInput() == input)
				return true;
		}

		return false;
	}

	// Metodo para agregar un estado final a una transicion intermedia
	private void agregarEstadoFinal(char input, Estado estadoFinal,
			List<TransicionIntermedia> transicionesIntermedias) {

		for (TransicionIntermedia transicionIntermedia : transicionesIntermedias) {
			if (transicionIntermedia.getInput() == input) {
				transicionIntermedia.addEstadoFinal(estadoFinal);
				return;
			}

		}
	}

	// Metodo para Ordenar UNA LISTA DE ESTADOS
	private List<Estado> ordenarListaEstados(List<Estado> listaDesordenada) {

		// ORDENAR DE MENOR A MAYOR POR VALOR DE ESTADO
		Collections.sort(listaDesordenada, new Comparator<Estado>() {
			@Override
			public int compare(Estado estado, Estado otroEstado) {
				if (estado.getValor() > otroEstado.getValor())
					return 1;
				if (estado.getValor() < otroEstado.getValor())
					return -1;
				return 0;
			}
		});
		return listaDesordenada;
	}

	// METODO para agregar un conjunto de estados finales del AFND como inicial del
	// AFD
	private void agregarEstadoFinalComoInicialDeAFD(List<Estado> estadosFinalesComoInicial) {
		
		if (!existeEstadoComoInicial(estadosFinalesComoInicial)) {
			this.conjuntoDeEstadosInicialesAFD.add(estadosFinalesComoInicial);
		}
		
		
	}

	// METODO PARA SABER SI YA AGREGUE UN SUBCONJUNTO DE ESTADOS FINAL DEL AFND COMO
	// INICIAL DEL AFD
	private boolean existeEstadoComoInicial(List<Estado> estadosFinalesComoInicial) {

		for (List<Estado> estadoInicialAFD : this.conjuntoDeEstadosInicialesAFD) {
			if (this.tienenLosMismosEstados(estadoInicialAFD, estadosFinalesComoInicial)) {				
				return true;
			}
		}

		return false;
	}

	// VEO SI DOS CONJUNTOS DE ESTADOS TIENEN LOS MISMOS ESTADOS
	private boolean tienenLosMismosEstados(List<Estado> listaEstados, List<Estado> otraListaEstados) {

		// SI NO TIENEN EL MISMO TAMAÑO, SON DISTINTAS LISTAS
		if (listaEstados.size() != otraListaEstados.size())
			return false;

		// RECORRO LOS ESTADOS Y VEO SI SON LOS MISMOS
		for (int i = 0; i < listaEstados.size(); i++) {
			if (listaEstados.get(i).getValor() != otraListaEstados.get(i).getValor())
				return false;
		}
		// SI ERAN TODOS IGUALES Y TIENEN LA MISMA LONGITUD, SON LA MISMA LISTA
		return true;
	}

}
