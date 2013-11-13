/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

import behaviours.CustomReceiverBehaviour;
import behaviours.MarsRoverBehaviour;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ReceiverBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 *
 * @author Lassei
 */
public class MarsRoverAgent extends Agent {

    private int posX;
    private int posY;
    private boolean hasRock;
    private AID spaceshipAgent;

    protected void setup() {




        try {
            //Registers the agent to DF
            DFService.register(this, getDFAgentDescription());
            spaceshipAgent = getSpaceship();
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        MessageTemplate tmpl = MessageTemplate.MatchPerformative(ACLMessage.CFP);
        addBehaviour(new CustomReceiverBehaviour(this, -1, tmpl) {
            public void handle(ACLMessage msg) {
                System.out.println("R1:");
                if (msg == null) {              
                    block();
                } else {
                    String[] coordinates = msg.getContent().split("-");
                    posX = Integer.parseInt(coordinates[0]);
                    posY = Integer.parseInt(coordinates[1]);
                    addBehaviour(new MarsRoverBehaviour(myAgent));
                }
            }
        });

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
    private AID getSpaceship() {

        DFAgentDescription dfAgentDescription = new DFAgentDescription();

        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType("spaceship-agent");

        dfAgentDescription.addServices(serviceDescription);

        try {
            DFAgentDescription[] result = DFService.search(this, dfAgentDescription);
            System.out.println();
            System.out.println("----< Company found following carriers >----");
            if (result.length != 0) {
                return result[0].getName();
            } else {
                doDelete();
                return null;
            }
        } catch (FIPAException e) {
            return null;
        }
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
