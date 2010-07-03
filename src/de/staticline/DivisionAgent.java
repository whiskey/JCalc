package de.staticline;

public class DivisionAgent extends CalculationAgent {

	public DivisionAgent() {
		super();
		this.agentOperation = "divisionAgent";
	}
	
	protected String doCalculationJob(double val1, double val2) {
        return ""+(val1/val2);
    }

}
