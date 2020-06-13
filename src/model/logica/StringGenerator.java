package model.logica;

import java.util.List;
import java.util.stream.Collectors;

import model.domain.Estado;

public class StringGenerator {

	
	public static String toStringListEstados(List<Estado> estados) {

		String ret = "{";

		ret += estados
		           .stream()
		           .map(a -> String.valueOf(a.getValor()))
		           .collect(Collectors.joining(","));
		ret += "}";
		return ret;

	}
}
