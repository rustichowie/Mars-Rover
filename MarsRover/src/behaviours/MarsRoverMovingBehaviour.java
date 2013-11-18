/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviours;

import agents.MarsRoverAgent;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.logging.Level;
import java.util.logging.Logger;
import jess.JessException;
import jess.JessUtil;
import ontologies.GridField;

/**
 *
 * @author Haavard
 */
public class MarsRoverMovingBehaviour extends CyclicBehaviour{

    private MarsRoverAgent myAgent;
    private JessUtil util;
    
    public MarsRoverMovingBehaviour(MarsRoverAgent a, String file){
        myAgent = a;
        util = new JessUtil(myAgent, file);
        
        
        
    }
    
    @Override
    public void action() {
        GridField[][] map = myAgent.getMap();
        GridField current = map[myAgent.getPosX()][myAgent.getPosY()];
        GridField left = null;
        GridField right = null;
        GridField top = null;
        GridField bottom = null;
        //GridField left = map[myAgent.getPosX()];
        try {
            if(current.hasLeft()){
                left = map[myAgent.getPosX()][myAgent.getPosY() - 1];
            } else{
                left = new GridField(false, true, false, false, false, 0, 0, 0, true, 0, 0);
            }
            if(current.hasRight()){
                right = map[myAgent.getPosX()][myAgent.getPosY() + 1];
            } else{
                right = new GridField(true, false, false, false, false, 0, 0, 0, true, 0, 0);
            }
            if(current.hasBottom()){
                 bottom = map[myAgent.getPosX() + 1][myAgent.getPosY()];
            } else{
                bottom = new GridField(false, false, true, false, false, 0, 0, 0, true, 0, 0);
            }
            if(current.hasTop()){
                 top = map[myAgent.getPosX() - 1][myAgent.getPosY()];
            } else{
                top = new GridField(false, false, false, true, false, 0, 0, 0, true, 0, 0);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        
        
        try {
            util.search(left, right, top, bottom, current, myAgent);
            
        } catch (JessException ex) {
            Logger.getLogger(MarsRoverMovingBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
