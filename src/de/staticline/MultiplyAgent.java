package de.staticline;

public class MultiplyAgent extends CalculationAgent {

	public MultiplyAgent() {
		super();
		this.agentOperation = "multiplyAgent";
	}
	
	protected String doCalculationJob(double val1, double val2) {
        return ""+(val1*val2);
    }

}
