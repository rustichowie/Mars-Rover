package behaviours;

import agents.SpaceshipAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class SpaceshipBehaviour extends CyclicBehaviour{
        
    private SpaceshipAgent myAgent;
    	
    public SpaceshipBehaviour(Agent agent){
        
        myAgent = (SpaceshipAgent)agent;
        
    }
	
    @Override
    public void action() {
        
    }
}
