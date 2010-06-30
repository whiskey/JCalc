package de.staticline;

import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;

public class UserAgent extends GuiAgent{
    transient protected UserAgentUI ui;
    public static final int CALC_EVENT = 1001;
    
    
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
        // Deregister from the yellow pages
        try {
            DFService.deregister(this);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
        System.out.println("UserAgent " + getAID().getName() + " terminating.");
    }

    protected void onGuiEvent(GuiEvent ge) {
        System.out.println(ge.toString());
    }
    
    public void calculate(Double val1, Double val2, String operation){
        //
    }
}
