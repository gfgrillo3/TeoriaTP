package model.AFNDtoAFD;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.domain.AFD;
import model.domain.AFND;
import model.domain.Estado;
import model.domain.Transicion;
import model.domain.Transicion;

public class AFNDtoAFD {

	private List<Transicion> transicionesIntermedias;
	private List<List<Estado>> conjuntoDeEstadosInicialesAFD;
	private List<List<Estado>> conjuntoDeEstadosFinalesAFD;
	private AFND afnd;
	private Estado estadoBasura;

	public AFD fromAFNDtoAFD(AFND afnd) {

		this.afnd = afnd;
		this.transicionesIntermedias = new ArrayList<Transicion>();
		this.conjuntoDeEstadosInicialesAFD = new ArrayList<>();
		this.conjuntoDeEstadosFinalesAFD = new ArrayList<>();
		
		ArrayList<Estado> estadoInicial = new ArrayList<>();
		estadoInicial.add(new Estado(1));

		this.conjuntoDeEstadosInicialesAFD.add(estadoInicial);
		
		for (int i = 0; i < this.conjuntoDeEstadosInicialesAFD.size(); i++) {
			this.agregarTransicionesIntermedias(conjuntoDeEstadosInicialesAFD.get(i));
		}
		
		//completo las transiciones con los input que faltan
		this.completarTransicionesIntermedias();
		this.crearEstadosFinales();
		
		  
		  
		return new AFD(this.afnd.getAlfabetoInput(), this.conjuntoDeEstadosInicialesAFD, this.conjuntoDeEstadosFinalesAFD, this.transicionesIntermedias);
	}

	// Agrego transiciones intermedias de una lista de estados
	private void agregarTransicionesIntermedias(List<Estado> estados) {

		
		
		// esta es la lista de transicionesIntermedias del conjunto de estados pasado
		// como parametro
		List<Transicion> transicionesIntermediasConjunto = new ArrayList<>();

		// Repito la operacion para todos los estados del conjunto pasado como
		// parametro
		for (Estado estado : estados) {
			

			// recorro las transiciones del automata para ver las transiciones del estado
			// que estoy recorriendo
			// y agregarlas como transiciones intermedias
			for (Transicion transicion : this.afnd.getTransiciones()) {
				
				// SI es una transicion de uno de los estados del parametro
				if (transicion.getEstadoInicial().get(0).getValor() == estado.getValor()) {
					System.out.println(transicion.toString()+" ES UNA TRANSICION DEL ESTADO {"+estado.getValor()+"}");
					// si no tengo ninguna transicion intermedia, me creo una nueva transicion, y le
					// seteo como estado inicial
					// el conjunto que recibi como parametro, luego la agrego.
					if (transicionesIntermediasConjunto.isEmpty()) {
						Transicion transicionNueva = new Transicion(transicion.getEstadoInicial().get(0), transicion.getInput(), transicion.getEstadoFinal().get(0));
						transicionNueva.setEstadoInicial(estados);
						transicionesIntermediasConjunto.add(transicionNueva);
					}

					// SI TENGO UNA TRANSICION INTERMEDIA, ME FIJO SI TENGO UNA CON ESE char de
					// INPUT
					else if (this.existeTransicionConChar(transicion.getInput(), transicionesIntermediasConjunto)) {
						this.agregarEstadoFinal(transicion.getInput(), transicion.getEstadoFinal().get(0),
								transicionesIntermediasConjunto);
					}

					// SI NO TENGO CON ESE INPUT, CREO UNA NUEVA TRANSICION INTERMEDIA
					// PARA EL SIMBOLO INICIAL CON EL INPUT. Ademas le seteo como estado inicial el
					// conjunto de estados de parametro
					else {
						Transicion transicionIntermediaNueva = new Transicion(transicion.getEstadoInicial().get(0), transicion.getInput(), transicion.getEstadoFinal().get(0));
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
		
		for (Transicion transicionIntermedia : transicionesIntermediasConjunto) {
			System.out.println("CREE LA TRANSICION "+transicionIntermedia.toString());
			transicionIntermedia.setEstadoFinal(this.ordenarListaEstados(transicionIntermedia.getEstadoFinal()));
			this.transicionesIntermedias.add(transicionIntermedia);
			this.agregarEstadoFinalComoInicialDeAFD(transicionIntermedia.getEstadoFinal());
		}
		
		System.out.println(" HASTA ACA TENGO LAS TRANSICIONES :");
		for(Transicion t : this.transicionesIntermedias)
			System.out.println(t.toString());
		System.out.println("--------------------------------------------------------------------------------------------------------------------");
		System.out.println("--------------------------------------------------------------------------------------------------------------------");
		System.out.println("--------------------------------------------------------------------------------------------------------------------");
		System.out.println("--------------------------------------------------------------------------------------------------------------------");
		System.out.println("--------------------------------------------------------------------------------------------------------------------");
	}

	// Metodo para saber si tengo una transicion intermedia con un input
	// para el símbolo inicial
	private boolean existeTransicionConChar(char input, List<Transicion> transicionesIntermedias) {

		for (Transicion transicionIntermedia : transicionesIntermedias) {
			if (transicionIntermedia.getInput() == input)
				return true;
		}

		return false;
	}

	// Metodo para agregar un estado final a una transicion intermedia
	private void agregarEstadoFinal(char input, Estado estadoFinal,
		List<Transicion> transicionesIntermedias) {

		for (Transicion transicionIntermedia : transicionesIntermedias) {
			if (transicionIntermedia.getInput() == input) {
				
				//Si ya lo tengo agregado al estado final, corto la llamada al metodo sin agregarlo
				for(Estado estado : transicionIntermedia.getEstadoFinal()) {
					if(estado.getValor() == estadoFinal.getValor())
						return;
				}
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
		System.out.print("{ ");
		for(Estado e : estadosFinalesComoInicial)
			System.out.print(e.getValor()+",");
		System.out.print(" }");
		
		for (List<Estado> estadoInicialAFD : this.conjuntoDeEstadosInicialesAFD) {
			if (this.tienenLosMismosEstados(estadoInicialAFD, estadosFinalesComoInicial)) {
				System.out.println(" YA LO TENIA COMO ESTADO");
				return true;
			}
		}
		System.out.println(" NO LO TENIA COMO ESTADO");
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


	//METODO PARA COMPLETAR LAS TRANSICIONES DEL AFD (UNA PARA CADA CHAR)
	private void completarTransicionesIntermedias() {
		
		//SI NO FALTAN TRANSICIONES, NO COMPLETO NADA
		//SI FALTAN CREO EL ESTADO BASURA PARA LLEVARLAS AHI
		if(!this.faltanTransiciones())
			return;
		else
			this.crearEstadoBasura();
		//RECORRO TODOS LOS ESTADOS INICIALES DEL AFD
		for(List<Estado> estadoInicialAFD : conjuntoDeEstadosInicialesAFD) {
			
			//OBTENGO LAS TRANSICIONES INTERMEDIAS DE EL ESTADO INICIAL
			List<Transicion> transicionesEstadoInicialAFD = this.obtenerTransicionesEstadoAFD(estadoInicialAFD);
			
			//VEO SI EXISTEN TRANSICION CON TODOS LOS CARACTERES DEL ALFABETO
			for(char charInput : this.afnd.getAlfabetoInput()) {
				
				//SI NO EXISTE TRANSICION CON ESE CARACTER CREO UNA NUEVA llevandola al estado basura
				if(!this.existeTransicionConChar(charInput, transicionesEstadoInicialAFD)) {

					List<Estado> estadoFinalFaltante = new ArrayList<Estado>();
					estadoFinalFaltante.add(this.estadoBasura);
					Transicion transicionIntermediaFaltante = new Transicion(estadoInicialAFD, charInput, estadoFinalFaltante);
					this.transicionesIntermedias.add(transicionIntermediaFaltante);
				}
			}
			
		}
		
	}

	
	//METODO PARA OBTENER LAS TRANSICIONES INTERMEDIAS DE UN ESTADO INICIAL DEL AFD
	private List<Transicion> obtenerTransicionesEstadoAFD(List<Estado> estadoInicialAFD) {

		List<Transicion> ret = new ArrayList<>();
		
		for(Transicion transicion : this.transicionesIntermedias) {
			if(this.tienenLosMismosEstados(transicion.getEstadoInicial(), estadoInicialAFD)) {
				ret.add(transicion);
			}
		}
		
		return ret;
	}
	
	
	//METODO PARA OBTENER LOS ESTADOS QUE SON FINALES DEL AFD
	private void crearEstadosFinales() {
		
		//recorro los estados del AFD que tengo creados
		for(List<Estado> estadosInicialesAFD : this.conjuntoDeEstadosInicialesAFD) {
			
			boolean esEstadoFinal = false;
			
			//recorro cada uno de esos estados del conjunto que eran un estado simple del AFND
			for(Estado estado : estadosInicialesAFD) {
				
				//recorro los estados Finales del AFND, si el estado del conjunto era final, lo seteo al flag para agregarlo como estado final 
				for(Estado estadoFinalAFND : this.afnd.getEstadosFinales()) {
					if(estadoFinalAFND.getValor() == estado.getValor()) {
						esEstadoFinal = true;
						break;
					}
				}
				if(esEstadoFinal)
					break;
			}
			
			if(esEstadoFinal) {
				esEstadoFinal = false;
				this.conjuntoDeEstadosFinalesAFD.add(estadosInicialesAFD);
			}
		}
	}
	
	
	//METODO PARA VER SI FALTA ALGUNA TRANSICION, PARA CREAR UN ESTADO BASURA
	private boolean faltanTransiciones() {
			
			//RECORRO TODOS LOS ESTADOS INICIALES DEL AFD
			for(List<Estado> estadoInicialAFD : conjuntoDeEstadosInicialesAFD) {
				
				//OBTENGO LAS TRANSICIONES INTERMEDIAS DE EL ESTADO INICIAL
				List<Transicion> transicionesEstadoInicialAFD = this.obtenerTransicionesEstadoAFD(estadoInicialAFD);
				
				//VEO SI EXISTEN TRANSICION CON TODOS LOS CARACTERES DEL ALFABETO
				for(char charInput : this.afnd.getAlfabetoInput()) {
					
					//SI NO EXISTE TRANSICION CON ESE CARACTER CREO el estado Basura
					if(!this.existeTransicionConChar(charInput, transicionesEstadoInicialAFD)) {
						return true;
					}
				}
			}
			return false;
	}
	
	//AGREGO EL ESTADO BASURA. SE VA A LLAMAR -1
	private void crearEstadoBasura() {
		
		this.estadoBasura = new Estado(-1);
		ArrayList<Estado> estadoBasuraAFD = new ArrayList<>();
		estadoBasuraAFD.add(this.estadoBasura);
		this.conjuntoDeEstadosInicialesAFD.add(estadoBasuraAFD);
	}
	
}
