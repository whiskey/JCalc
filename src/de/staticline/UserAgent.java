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
import jade.lang.acl.MessageTemplate;

public class UserAgent extends GuiAgent{
    transient protected UserAgentUI ui;
    public static final int CALC_EVENT = 1001;
    private String requestedAgentType = "calcAgent";
    private AID[] matchingAgents;
    
    
    protected void setup(){
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("ui_calculation");
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
    			//TODO: tokenizer in CalCulationAgent(!) to parse a complex string like '(3+5)*3+5'
    			double val1 = (Double)ge.getParameter(0);
        		double val2 = (Double)ge.getParameter(1);
        		String operation = (String)ge.getParameter(2);
        		if(operation=="+"){
        			requestedAgentType="addAgent";
        		}else if(operation=="-"){
        			requestedAgentType="subtractAgent";
        		}else if(operation=="x"){
        			requestedAgentType="multiplyAgent";
        		}else if(operation=="/"){
        			requestedAgentType="divisionAgent";
        		}else{
        			requestedAgentType="calcAgent";
        		}
        		System.out.println(this.getName() + " got CALC_EVENT: " +val1+operation+val2 + "\nrequesting "+requestedAgentType);
        		
        		//call for calcAgents w/ matching skills
                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType(requestedAgentType);
                template.addServices(sd);
                try {
                    DFAgentDescription[] result = DFService.search(this, template);
                    if(result.length>0){
                    	System.out.println("Found the following "+ requestedAgentType+"(s):");
                    	matchingAgents = new AID[result.length];
                    	for (int i = 0; i < result.length; ++i) {
                    		matchingAgents[i] = result[i].getName();
                    		System.out.println(matchingAgents[i].getName());
                    	}
                    	System.out.println("\n");
                    	//choose one agent to make the calculation
                    	this.addBehaviour(new CRequest(val1, val2, operation));
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
    	private MessageTemplate mt;
    	private int step = 0;
    	private double val1;
    	private double val2;
    	private String operation;
    	private double result;
		
    	public CRequest(Double v1, Double v2, String op){
    		val1 = v1;
    		val2 = v2;
    		operation = op;
    	}
    	
		public void action() {
			switch(step){
			case 0:
				ACLMessage cfp = new ACLMessage(ACLMessage.REQUEST);
            	cfp.addReceiver(matchingAgents[0]);
            	cfp.setContent(requestedAgentType+" "+val1+" "+val2);
            	cfp.setConversationId("doCalcJob");
            	cfp.setReplyWith("cfp"+System.currentTimeMillis());
            	myAgent.send(cfp);
            	mt = MessageTemplate.and(MessageTemplate.MatchConversationId("doCalcJob"),
                        MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
            	step = 1;
				break;
			case 1:
				ACLMessage reply = myAgent.receive(mt);
				if(reply != null){
					if(reply.getPerformative() == ACLMessage.INFORM){
						//System.out.println(myAgent.getName()+" received from "+reply.getSender()+": "+reply.getContent());
						//update gui
						ui.setValue(reply.getContent());
						step=2;
					}else{
						System.out.println(reply.toString());
						System.out.println("oops");
						step=2;//FIXME better error handling, but now I don't care...
					}
				}
			}
			
			
		}
		
		public boolean done() {
			if(step==2){
				System.out.println(myAgent.getName()+" got a solution. Removing CRequest behavior.");
				myAgent.removeBehaviour(this);
			}
			return step==2;
		}
    }
}
