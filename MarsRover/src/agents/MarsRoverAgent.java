/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

/**
 *
 * @author Lassei
 */
public class MarsRoverAgent extends Agent {

    private int posX;
    private int posY;
    private boolean hasRock;
    private SpaceshipAgent spaceshipAgent;

    protected void setup() {
        try {
            //Registers the agent to DF
            DFService.register(this, getDFAgentDescription());
            spaceshipAgent = getSpaceship();
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }


    }

    /**
     * Sets the service description and DF description
     *
     * @return dfAgentDescription
     */
    private DFAgentDescription getDFAgentDescription() {

        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setName(getLocalName());
        serviceDescription.setType("company-agent");

        DFAgentDescription dfAgentDescription = new DFAgentDescription();
        dfAgentDescription.setName(getAID());
        dfAgentDescription.addServices(serviceDescription);

        return dfAgentDescription;
    }

    /**
     * Gets all carriers that is regstrered to the DFAgentDescription
     */
    private SpaceshipAgent getSpaceship() {

        DFAgentDescription dfAgentDescription = new DFAgentDescription();

        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType("spaceship-agent");

        dfAgentDescription.addServices(serviceDescription);

        try {
            DFAgentDescription[] result = DFService.search(this, dfAgentDescription);
            System.out.println();
            System.out.println("----< Company found following carriers >----");
            for (int i = 0; i < result.length; i++) {
                
            }
            

        } catch (FIPAException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public boolean hasRock() {
        return hasRock;
    }

    public void setHasRock(boolean hasRock) {
        this.hasRock = hasRock;
    }
}
