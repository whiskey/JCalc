package de.staticline;

public class SubtractAgent extends CalculationAgent {

	public SubtractAgent() {
		super();
		this.agentOperation = "subtractAgent";
	}
	
	protected String doCalculationJob(double val1, double val2) {
        return ""+(val1-val2);
    }

}
