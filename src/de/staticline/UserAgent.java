package de.staticline;

import javax.media.j3d.Behavior;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

public class UserAgent extends GuiAgent{
    transient protected UserAgentUI ui;
    public static final int CALC_EVENT = 1001;
    String requestedAgentType = "calcAgent";
    private AID[] allCalcAgents;
	private AID calcAgent;
    
    
    protected void setup(){
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("user-interaction");
        sd.setName("UserAgent");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
        
        //ui
        ui = new UserAgentUI(this);
        ui.setVisible(true);
    }
    
    // Put agent clean-up operations here
    protected void takeDown() {
        try {
        	// Deregister from the yellow pages
            DFService.deregister(this);
            //close UI
            ui.dispose();
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
        System.out.println("UserAgent " + getAID().getName() + " terminating.");
    }

    protected void onGuiEvent(GuiEvent ge) {
    	switch(ge.getType()){
    		case CALC_EVENT:
    			double val1 = (Double)ge.getParameter(0);
        		double val2 = (Double)ge.getParameter(1);
        		String operation = (String)ge.getParameter(2);
        		if(operation=="+"){
        			requestedAgentType="addAgent";
        		}else if(operation=="-"){
        			requestedAgentType="subtractAgent";
        		}else if(operation=="*"){
        			requestedAgentType="multiplicationAgent";
        		}else if(operation=="/"){
        			requestedAgentType="divisionAgent";
        		}else{
        			requestedAgentType="calcAgent";
        		}
        		System.out.println(this.getName() + " got CALC_EVENT: " +val1+operation+val2);
        		
        		//call for calcAgents w/ matching skills
                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType(requestedAgentType);
                template.addServices(sd);
                try {
                    DFAgentDescription[] result = DFService.search(this, template);
                    System.out.println("Found the following "+ requestedAgentType+"(s):");
                    if(result.length>0){
                    	allCalcAgents = new AID[result.length];
                    	for (int i = 0; i < result.length; ++i) {
                    		allCalcAgents[i] = result[i].getName();
                    		System.out.println(allCalcAgents[i].getName());
                    	}
                    	
                    	this.addBehaviour(new CRequest(val1,val2,operation));
                    }else{
                    	System.out.println("Found no "+requestedAgentType+"s :(");
                    }
                }
                catch (FIPAException fe) {
                    fe.printStackTrace();
                }
        		
        		
        		break;
    	}
    }
    

    
    private class CRequest extends Behaviour{
    	private int step = 0;
		
    	public CRequest(Double v1, Double v2, String op){
    		/*val1 = v1;
    		val2 = v2;
    		operation = op;*/
    		System.out.println(this);
    	}
    	
		public void action() {
			switch(step){
			case 0:
				
				break;
				
			}
			
			
		}
		@Override
		public boolean done() {
			
			return false;
		}
    }
}
