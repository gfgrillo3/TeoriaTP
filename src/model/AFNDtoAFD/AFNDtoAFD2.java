package model.AFNDtoAFD;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import model.domain.AFD;
import model.domain.AFND;
import model.domain.Estado;
import model.domain.Transicion;
import model.domain.TransicionIntermedia;

public class AFNDtoAFD2 {

	private List<TransicionIntermedia> transicionesIntermedias;
	private List<List<Estado>> conjuntoDeEstadosYaBuscadasSusTransicionesIntermedias;
	private AFND afnd;
	
	
	public AFD fromAFNDtoAFD(AFND afnd) {
		
		this.afnd = afnd;
		
		
		this.agregarTransicionesIniciales(afnd.getTransiciones());
		
		List<TransicionIntermedia> transicionesActuales = new ArrayList<>();
		transicionesActuales = this.transicionesIntermedias;
		
		
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
		
		
		
		List<List<Estado>> estadosYaAgregadosTransiciones = new ArrayList<>();
		estadosYaAgregadosTransiciones = this.conjuntoDeEstadosYaBuscadasSusTransicionesIntermedias;
		
		//LLAMAR A AGREGAR TransicionesIntermedias de los conjuntos de estados que no recorrí
		List<TransicionIntermedia> transicionesFinales = new ArrayList<>();
		//transicionesFinales = 
				this.agregarTransicionesIntermedias(transicionesActuales, estadosYaAgregadosTransiciones);
		
		
		for(TransicionIntermedia t : transicionesFinales) {
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

	
	//METODO PARA AGREGAR LAS TRANSICIONES INTERMEDIAS
	//DEL SIMBOLO INICIAL (1)
	private void agregarTransicionesIniciales(List<Transicion> transiciones) {

		this.transicionesIntermedias = new ArrayList<TransicionIntermedia>();
		
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
	private List<TransicionIntermedia> agregarTransicionesIntermedias(List<TransicionIntermedia> transicionesActuales, List<List<Estado>> conjuntoDeEstadosYaBuscadasSusTransicionesIntermedias) {
		
		boolean reviseAlgunaTransicion = false;
		
		List<TransicionIntermedia> ret = new ArrayList<TransicionIntermedia>();
		List<TransicionIntermedia> copyRet = new ArrayList<TransicionIntermedia>();
		
		
		//CopyOnWriteArrayList<TransicionIntermedia> trans = new CopyOnWriteArrayList<TransicionIntermedia>();
		//trans.addAll(transicionesActuales);
		
		System.out.println("entro al for");
		
		
		//RECORRO LAS TRANSICIONES INTERMEDIAS QUE ENCONTRE
		for(TransicionIntermedia transicionIntermedia : transicionesActuales) {
			System.out.println("LLEGUE1");
			boolean reviseSusTransicionesIntermedias = false;
			
			//REVISO SI YA AGREGUE COMO ESTADO INICIAL EL CONJUNTO DE ESTADO FINAL DE LAS TRANSICIONES INTERMEDIAS
			for(List<Estado> conjuntoDeEstados : conjuntoDeEstadosYaBuscadasSusTransicionesIntermedias) {
				if(this.tienenLosMismosEstados(conjuntoDeEstados, transicionIntermedia.getEstadoFinal()))
					reviseSusTransicionesIntermedias = true;
			}
			
			//SI NO HABIA REVISADO LAS TRANSICIONES INTERMEDIAS
			//REVISO LAS TRANSICIONES INTERMEDIAS DEL CONJUNTO DE ESTADOS;
			if(!reviseSusTransicionesIntermedias) {
				System.out.println("llegue 2");
				copyRet = ret;
				//ret = 
						this.agregarTransicionesIntermediasConjuntoEstados(transicionIntermedia.getEstadoFinal(), copyRet);
				//copyRet = ret;
				System.out.println("LLEGUE 3");
				//MARCO EL CONJUNTO DE ESTADOS PARA SABER QUE YA BUSQUE SUS TRANSICIONES INTERMEDIAS
				conjuntoDeEstadosYaBuscadasSusTransicionesIntermedias.add(transicionIntermedia.getEstadoFinal());
				reviseAlgunaTransicion = true;
			}
			else {
				ret.add(transicionIntermedia);
				copyRet.add(transicionIntermedia);
			}
				//REVISAR SUS TRANSICIONES INTERMEDIAS
			
			System.out.print("EI = { ");
			for(Estado ei : transicionIntermedia.getEstadoInicial()) {
				System.out.print(ei.getValor() + " , ");
			}
			System.out.print(" } ");
			System.out.print( " , INPUT = "+transicionIntermedia.getInput());
			System.out.print("   -> EF = { ");
			for(Estado ef : transicionIntermedia.getEstadoFinal()) {
				System.out.print(ef.getValor() + " , ");
			}
			System.out.println("");
			
		}
		
		System.out.println("LLEGUE 4");
		//LLAMADA RECURSIVA
		if(reviseAlgunaTransicion)
			ret = this.agregarTransicionesIntermedias(ret, conjuntoDeEstadosYaBuscadasSusTransicionesIntermedias);
		
		return ret;

	}
	
	
	//VEO SI DOS CONJUNTOS DE ESTADOS TIENEN LOS MISMOS ESTADOS
	private boolean tienenLosMismosEstados(List<Estado> listaEstados, List<Estado> otraListaEstados) {

		/*
		//ORDENO LISTAS POR VALOR DE ESTADOS
		listaEstados = this.ordenarListaEstados(listaEstados);
		otraListaEstados = this.ordenarListaEstados(otraListaEstados);
		*/
		
		
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
	
	
	
	//METODO PARA AGREGAR LAS TRANSICIONES INTERMEDIAS DE UN CONJUNTO DE ESTADOS QUE SON FINALES DEL AFND
	//SON LOS QUE SE CONVIERTEN EN INICIALES EN EL AFD
	private List<TransicionIntermedia> agregarTransicionesIntermediasConjuntoEstados(List<Estado> conjuntoDeEstadosComoEstadoInicial2, List<TransicionIntermedia> transicionesActuales2) {
		
		List<TransicionIntermedia> transicionesIntermediasConjuntoEstados = new ArrayList<>();
		
		List<Estado> conjuntoDeEstadosComoEstadoInicial = new ArrayList<>();
		conjuntoDeEstadosComoEstadoInicial.addAll(conjuntoDeEstadosComoEstadoInicial2);
		
		List<TransicionIntermedia> transicionesActuales = new ArrayList<>();
		transicionesActuales.addAll(transicionesActuales2);
		
		
		//PARA CADA ESTADO DEL CONJUNTO BUSCO SUS TRANSICIONES INTERMEDIAS
		for(Estado estado: conjuntoDeEstadosComoEstadoInicial) {
			List<TransicionIntermedia> transicionesIntermediasEncontradas = this.obtenerTransicionIntermedia(estado);
			
			//AGREGO TODAS LAS QUE ECONTRE EN LA LISTA DE TRANSICIONES DEL CONJUNTO DE ESTADOS
			for(TransicionIntermedia transEncontrada : transicionesIntermediasEncontradas) {
				transicionesIntermediasConjuntoEstados.add(transEncontrada);
			}
		}
		//UNIR TRANSICIONES DE LAS LISTAS
		
		//AGREGO LAS TRANSICIONES INTERMEDIAS ENCONTRADAS A LA LISTA DE TRANSICIONES INTERMEDIAS
		for(TransicionIntermedia transicionIntermedia: transicionesIntermediasConjuntoEstados) {
			transicionesActuales.add(transicionIntermedia);
		}
		
		return transicionesActuales;
	}

	
	//DEVUELVE LA LISTA DE TRANSICIONES INTERMEDIAS DE UN ESTADO
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
