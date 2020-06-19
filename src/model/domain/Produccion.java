package model.domain;

import java.util.ArrayList;
import java.util.List;

public class Produccion {

	Variable variable;
	List<String> cuerpo;
	
	public Produccion(Variable variable, List<String> cuerpo) {
		this.variable = variable;
		this.cuerpo = cuerpo;
	}

	public Variable getVariable() {
		return variable;
	}

	public void setVariable(Variable variable) {
		this.variable = variable;
	}

	public List<String> getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(List<String> cuerpo) {
		this.cuerpo = cuerpo;
	}
	
}
