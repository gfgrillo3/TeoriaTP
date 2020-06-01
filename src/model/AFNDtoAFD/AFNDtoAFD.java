package model.AFNDtoAFD;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import model.domain.AFD;
import model.domain.AFND;
import model.domain.Estado;
import model.domain.Transicion;
import model.domain.TransicionIntermedia;

public class AFNDtoAFD {

	private List<List<Estado>> estadosAFD;
	private List<Transicion> transicionesAFD;
	private List<TransicionIntermedia> transicionesIntermedias;
	private List<List<Estado>> conjuntoDeEstadosYaBuscadasSusTransicionesIntermedias;
	private AFND afnd;
	
	
	public AFD fromAFNDtoAFD(AFND afnd) {
		
		this.afnd = afnd;
		
		
		this.agregarTransicionesIniciales(afnd.getTransiciones());
		this.crearTablaAFD();
		/*
		for(TransicionIntermedia t : this.transicionesIntermedias) {
			System.out.print("EI = { ");
			for(Estado ei : t.getEstadoInicial()) {
				System.out.print(ei.getValor() + " , ");
			}
			System.out.print(" } ");
			System.out.print( " , INPUT = "+t.getInput());
			System.out.print("   -> EF = { ");
			for(Estado ef : t.getEstadoFinal()) {
				System.out.print(ef.getValor() + " , ");
			}
			System.out.println("");
		}
		*/
		//LLAMAR A AGREGAR TransicionesIntermedias de los conjuntos de estados que no recorrí
		this.agregarTransicionesIntermedias();
		
		
		for(TransicionIntermedia t : this.transicionesIntermedias) {
			System.out.print("EI = { ");
			for(Estado ei : t.getEstadoInicial()) {
				System.out.print(ei.getValor() + " , ");
			}
			System.out.print(" } ");
			System.out.print( " , INPUT = "+t.getInput());
			System.out.print("   -> EF = { ");
			for(Estado ef : t.getEstadoFinal()) {
				System.out.print(ef.getValor() + " , ");
			}
			System.out.println("");
		}
		
		return null;
	}
	
	private void crearTablaAFD() {
		
		
		
		
	}
	
	
	//METODO PARA AGREGAR LAS TRANSICIONES INTERMEDIAS
	//DEL SIMBOLO INICIAL (1)
	private void agregarTransicionesIniciales(List<Transicion> transiciones) {

		this.transicionesIntermedias = new ArrayList<TransicionIntermedia>();

		//RECORRER LOS ESTADOSINICIALES DE LAS TRANSICIONES INTERMEDIAS
		
		
		//RECORRO TODAS LAS TRANSICIONES QUE VAN A ESTAR ORDENADAS
		for(Transicion transicion: transiciones) {
			
			//SI NO ES EL SIMBOLO INICIAL, CORTO EL CICLO, YA AGREGUE TODAS LAS TRANSICIONES
			// DEL SIMBOLO INICIAL
			if(transicion.getEstadoInicial().getValor() != 1)
				break;
			
			//SI NO TENGO NINGUNA TRANSICION INTERMEDIA DEL SIMBOLO INICIAL, CREO UNA NUEVA
			if(this.transicionesIntermedias.isEmpty())
				transicionesIntermedias.add(new TransicionIntermedia(transicion));
			
			//SI TENGO UNA TRANSICION INTERMEDIA, ME FIJO SI TENGO UNA CON ESE INPUT
			else if(this.existeTransicionConChar(transicion.getInput(), this.transicionesIntermedias)){
				this.agregarEstadoFinal(transicion.getInput(), transicion.getEstadoFinal());
			}
			
			//SI NO TENGO CON ESE INPUT, CREO UNA NUEVA TRANSICION INTERMEDIA
			//PARA EL SIMBOLO INICIAL CON EL INPUT
			else {
				transicionesIntermedias.add(new TransicionIntermedia(transicion));
			}

		}
		
		
		//AGREGO EL 1 A la LISTA donde almaceno los conjuntos de ESTADOS RECORRIDOS
		this.conjuntoDeEstadosYaBuscadasSusTransicionesIntermedias = new ArrayList<>();
		ArrayList<Estado> estadoIni = new ArrayList<>();
		estadoIni.add(new Estado(1));
		this.conjuntoDeEstadosYaBuscadasSusTransicionesIntermedias.add(estadoIni);
		
	
	}
	
	
	//Metodo para saber si tengo una transicion intermedia con un input
	//para el símbolo inicial
	private boolean existeTransicionConChar(char input, List<TransicionIntermedia> transicionesIntermedias) {
	
		for(TransicionIntermedia transicionIntermedia : transicionesIntermedias) {
			if(transicionIntermedia.getInput() == input)
				return true;
		}
		
		return false;
	}
	
	
	//Metodo para agregar un estado final a una transicion intermedia
	private void agregarEstadoFinal(char input, Estado estadoFinal) {
		
		for(TransicionIntermedia transicionIntermedia : this.transicionesIntermedias) {
			if(transicionIntermedia.getInput() == input) {
				transicionIntermedia.addEstadoFinal(estadoFinal);
				return;
			}
			
		}
		
		
	}
	
	
	
	///////////////////////////////////
	//////////////////////////////////
	/////////////////////////////////
	////////////////////////////////
	
	//
	private void agregarTransicionesIntermedias() {
		
		boolean reviseAlgunaTransicion = false;
		
		//RECORRO LAS TRANSICIONES INTERMEDIAS QUE ENCONTRE
		for(TransicionIntermedia transicionIntermedia : this.transicionesIntermedias) {
			
			boolean reviseSusTransicionesIntermedias = false;
			
			//REVISO SI YA AGREGUE COMO ESTADO INICIAL EL CONJUNTO DE ESTADO FINAL DE LAS TRANSICIONES INTERMEDIAS
			for(List<Estado> conjuntoDeEstados : this.conjuntoDeEstadosYaBuscadasSusTransicionesIntermedias) {
				if(this.tienenLosMismosEstados(conjuntoDeEstados, transicionIntermedia.getEstadoFinal()))
					reviseSusTransicionesIntermedias = true;
			}
			
			//SI NO HABIA REVISADO LAS TRANSICIONES INTERMEDIAS
			//REVISO LAS TRANSICIONES INTERMEDIAS DEL CONJUNTO DE ESTADOS;
			if(!reviseSusTransicionesIntermedias) {
				this.agregarTransicionesIntermediasConjuntoEstados(transicionIntermedia.getEstadoFinal());
				reviseAlgunaTransicion = true;
			}
				//REVISAR SUS TRANSICIONES INTERMEDIAS
		}
		
		
		//LLAMADA RECURSIVA
		if(reviseAlgunaTransicion)
			this.agregarTransicionesIntermedias();
		

	}
	
	
	//VEO SI DOS CONJUNTOS DE ESTADOS TIENEN LOS MISMOS ESTADOS
	private boolean tienenLosMismosEstados(List<Estado> listaEstados, List<Estado> otraListaEstados) {

		//ORDENO LISTAS POR VALOR DE ESTADOS
		listaEstados = this.ordenarListaEstados(listaEstados);
		otraListaEstados = this.ordenarListaEstados(otraListaEstados);
		
		//SI NO TIENEN EL MISMO TAMAÑO, SON DISTINTAS LISTAS
		if(listaEstados.size() != otraListaEstados.size())
			return false;
		
		//RECORRO LOS ESTADOS Y VEO SI SON LOS MISMOS
		for(int i = 0; i<listaEstados.size(); i++) {
			if(listaEstados.get(i).getValor() != listaEstados.get(i).getValor())
				return false;
		}
		//SI ERAN TODOS IGUALES Y TIENEN LA MISMA LONGITUD, SON LA MISMA LISTA
		return true;
	}
	
	//ORDENAR LISTA POR ESTADOS DE MENOR A MAYOR
	private List<Estado> ordenarListaEstados(List<Estado> listaEstados){
		
		//ORDENAR DE MENOR A MAYOR POR VALOR DE ESTADO
				Collections.sort(listaEstados, new Comparator<Estado>(){
					@Override
					public int compare(Estado estado, Estado otroEstado) {
						if(estado.getValor() > otroEstado.getValor())
							return 1;
						if(estado.getValor() < otroEstado.getValor())
							return -1;
						return 0;
					}
					 });
				return listaEstados;
	}
	
	
	
	//METODO PARA AGREGAR LAS TRANSICIONES INTERMEDIAS DE UN CONJUNTO DE ESTADOS
	private void agregarTransicionesIntermediasConjuntoEstados(List<Estado> conjuntoDeEstadosComoEstadoInicial) {
		
		List<TransicionIntermedia> transicionesIntermedia = null;
		
		for(Estado estado: conjuntoDeEstadosComoEstadoInicial) {
			transicionesIntermedia = this.obtenerTransicionIntermedia(estado);
		}
		

		//AGREGO EL ESTADO/CONJUNTO DE ESTADOS, A LA LISTA DONDE ALMACENO LOS CONJUNTOS DE ESTADOS RECORRIDOS
		this.conjuntoDeEstadosYaBuscadasSusTransicionesIntermedias.add(conjuntoDeEstadosComoEstadoInicial);
		
		//AGREGO LAS TRANSICIONES INTERMEDIAS ENCONTRADAS A LA LISTA DE TRANSICIONES INTERMEDIAS
		for(TransicionIntermedia transicionIntermedia: transicionesIntermedia) {
			this.transicionesIntermedias.add(transicionIntermedia);
		}
		
	}

	
	
	private List<TransicionIntermedia> obtenerTransicionIntermedia(Estado estado) {
		
		List<TransicionIntermedia> ret = new ArrayList<TransicionIntermedia>();

		//RECORRER LOS ESTADOSINICIALES DE LAS TRANSICIONES INTERMEDIAS
		//RECORRO TODAS LAS TRANSICIONES QUE VAN A ESTAR ORDENADAS
		for(Transicion transicion: this.afnd.getTransiciones()) {
			
			
			//SI El ESTADO ES MAYOR, CORTO EL CICLO, YA AGREGUE TODAS LAS TRANSICIONES
			// PORQUE ESTABAN ORDENADAS
			if(transicion.getEstadoInicial().getValor() > estado.getValor())
				break;
			
			//SI ES EL ESTADO INICIAL QUE NECESITO, REVISO SUS TRANSICIONES
			if(transicion.getEstadoInicial().getValor() == estado.getValor())
			{	
						
				//SI NO TENGO NINGUNA TRANSICION INTERMEDIA DEL SIMBOLO INICIAL, CREO UNA NUEVA
				if(ret.isEmpty())
					ret.add(new TransicionIntermedia(transicion));
		
				//SI TENIA TRANSICION CON ESE INPUT, LE AGREGO UN ESTADO FINAL
				else if(this.existeTransicionConChar(transicion.getInput(), ret)) {
					ret = this.agregarEstadoFinalSegundo(transicion.getInput(), estado, ret);
				}
				
				//SI NO TENGO CON ESE INPUT, CREO UNA NUEVA TRANSICION INTERMEDIA
				//PARA EL ESTADO CON EL INPUT
				else {
					transicionesIntermedias.add(new TransicionIntermedia(transicion));
				}
				
			}
		}
		return ret;
	}
	
		
		//Metodo para agregar un estado final a una transicion intermedia de una lista
		private List<TransicionIntermedia> agregarEstadoFinalSegundo(char input, Estado estadoFinal, List<TransicionIntermedia> transicionesIntermedias) {
			
			for(TransicionIntermedia transicionIntermedia : transicionesIntermedias) {
				if(transicionIntermedia.getInput() == input) {
					transicionIntermedia.addEstadoFinal(estadoFinal);
					break;
				}
				
			}
			return transicionesIntermedias;		
		}

	

		public List<TransicionIntermedia> getTransicionesIntermedias(){
			return this.transicionesIntermedias;
		}
}
