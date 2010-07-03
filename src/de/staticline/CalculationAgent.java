package de.staticline;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public abstract class CalculationAgent extends Agent {
    protected String agentOperation = "calculation";
    protected boolean delivered = false;
    
    protected void setup(){
        // Register the book-selling service in the yellow pages
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(agentOperation);
        sd.setName("calcAgent");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
        // add behaviors
        addBehaviour(new OfferCalcService());
        addBehaviour(new DoCalcService());
        
        System.out.println("setup "+this.getName());
    }
    
    // Put agent clean-up operations here
    protected void takeDown() {
        // Deregister from the yellow pages
        try {
            DFService.deregister(this);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
        System.out.println("CalculationAgent " + getAID().getName() + " terminating.");
    }
    
    
    private class OfferCalcService extends CyclicBehaviour{
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // CFP Message received. Process it
                String service = msg.getContent();
                ACLMessage reply = msg.createReply();
                if (service == agentOperation) {
                    // The requested book is available for sale. Reply with the price
                    reply.setPerformative(ACLMessage.PROPOSE);
                    reply.setContent(agentOperation);
                }
                else {
                    // The requested book is NOT available for sale.
                    reply.setPerformative(ACLMessage.REFUSE);
                    reply.setContent("not-available");
                }
                myAgent.send(reply);
            }
            else {
                block();
            }
        }
    }
    
    private class DoCalcService extends CyclicBehaviour{
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // ACCEPT_PROPOSAL Message received. Process it
            	String[] content = msg.getContent().split("\\s+");
            	String operation = content[0];
            	double v1 = Double.parseDouble(content[1]);
            	double v2 = Double.parseDouble(content[2]);
                ACLMessage reply = msg.createReply();
                if (operation.matches(agentOperation)) {
                    reply.setPerformative(ACLMessage.INFORM);
                    //System.out.println(myAgent.getName()+": "+operation + " done for agent " + msg.getSender().getName());
                    reply.setContent(doCalculationJob(v1, v2));
                }
                else {
                    // The requested book has been sold to another buyer in the meanwhile .
                    reply.setPerformative(ACLMessage.FAILURE);
                    reply.setContent("not-available");
                }
                myAgent.send(reply);
            }
        }

    }
    
    protected String doCalculationJob(double val1, double val2){
        return "default";
    }
}
