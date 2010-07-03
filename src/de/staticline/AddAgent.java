package de.staticline;

public class AddAgent extends CalculationAgent {
    
	public AddAgent(){
		super();
		this.agentOperation = "addAgent";
	}
	
	
    protected String doCalculationJob(double val1, double val2) {
        return ""+(val1+val2);
    }
}
