package model.domain;

public class Variable {
	
	public String variable;
	
	public Variable(String variable) {
		//X_{i}abX_{i}
		this.variable = variable;
	}

	public String getStringVariable() {
		return variable;
	}

	public void setStringVariable(String variable) {
		this.variable = variable;
	}

	
}
