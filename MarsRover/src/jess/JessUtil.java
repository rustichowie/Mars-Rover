package jess;

import agents.MarsRoverAgent;
import agents.SpaceshipAgent;
import jade.core.Agent;
import java.util.logging.Level;
import java.util.logging.Logger;
import ontologies.GridField;



public class JessUtil {

    // class variables
    private Rete jess; // holds the pointer to jess
    private MarsRoverAgent myAgent; // holds the pointer to this agent

    /**
     * Creates a
     * <code>BasicJessBehaviour</code> instance
     *
     * @param agent the agent that adds the behaviour
     * @param jessFile the name of the Jess file to be executed
     */
    public JessUtil(MarsRoverAgent agent, String jessFile) {
        jess = new Rete();
        try {
            jess.batch(jessFile);
            String rover = "(bind ?rover (assert (rover (name "+agent.getLocalName()+") (carrying " + agent.hasRock()+"))))";
            this.makeassert(rover);
            jess.run();
        } catch (JessException ex) {
            Logger.getLogger(JessUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private boolean isEmpty(String string) {
        return (string == null) || string.equals("");
    }

    /**
     * makeasserts a fact representing an ACLMessage in Jess. It is called after
     * the arrival of a message.
     */
    public void makeassert(String fact) {
        try {
            jess.executeCommand(fact);
        } catch (JessException re) {
            re.printStackTrace(System.err);
        }
    }
    
    public String getNextDirection() throws JessException{
        String dir = (String)jess.fetch("direction").externalAddressValue(null);
        return dir;
    }
    
    public void pickup(GridField gf, MarsRoverAgent rover) throws JessException{
        
        String pickup = "";
        if(jess.fetch("pickup") != null)
            pickup = (String)jess.fetch("pickup").externalAddressValue(null);
        else
            pickup = "false";
        
        if(pickup.equals("true")){
            gf.setNumberOfRocks(gf.getNumberOfRocks() - 1);
            rover.setHasRock(true);
            jess.store("pickup", "false");
        }    
    }
    
    public void drop(MarsRoverAgent rover) throws JessException{
        String drop = "";
        if(jess.fetch("drop") != null)
            drop = (String)jess.fetch("pickup").externalAddressValue(null);
        else
            drop = "false";
        
        if(drop.equals("true")){
            jess.store("drop", "false");
            rover.setHasRock(false);
        }    
    }
    
    public boolean alert() throws JessException{
        String alert = "";
        if(jess.fetch("alert") != null)
            alert = (String)jess.fetch("alert").externalAddressValue(null);
        else
            alert = "false";
        
        if(alert.equals("true"))
            return true;
        else
            return false;
    }
    public void search(GridField left, GridField right, GridField top, GridField bottom, GridField current) throws JessException{
        
        String left_assert = "(assert (gridbox (direction left) (signal "+left.getSignalStrength()+")"
                                    + " (obstacle "+left.isObstacle()+") (rocks "+left.getNumberOfRocks()+")"
                                        + " (grain "+left.isGrain()+") (came_from "+left.isCame_from()+") (is_spaceship "+left.isSpaceship()+")))";
        String right_assert = "(assert (gridbox (direction right) (signal "+right.getSignalStrength()+")"
                                    + " (obstacle "+right.isObstacle()+") (rocks "+right.getNumberOfRocks()+")"
                                        + " (grain "+right.isGrain()+") (came_from "+right.isCame_from()+") (is_spaceship "+right.isSpaceship()+")))";
        String top_assert = "(assert (gridbox (direction top) (signal "+top.getSignalStrength()+")"
                                    + " (obstacle "+top.isObstacle()+") (rocks "+top.getNumberOfRocks()+")"
                                        + " (grain "+top.isGrain()+") (came_from "+top.isCame_from()+") (is_spaceship "+top.isSpaceship()+")))";

        String bottom_assert = "(assert (gridbox (direction bottom) (signal "+bottom.getSignalStrength()+")"
                                    + " (obstacle "+bottom.isObstacle()+") (rocks "+bottom.getNumberOfRocks()+")"
                                        + " (grain "+bottom.isGrain()+") (came_from "+bottom.isCame_from()+")"
                                            + " (is_spaceship "+bottom.isSpaceship()+")))";
         String current_assert = "(assert (gridbox (direction this) (signal "+current.getSignalStrength()+")"
                                    + " (obstacle "+current.isObstacle()+") (rocks "+current.getNumberOfRocks()+")"
                                        + " (grain "+current.isGrain()+") (came_from "+current.isCame_from()+") (is_spaceship "+current.isSpaceship()+")))";
        
        this.makeassert(left_assert);
        this.makeassert(top_assert);
        this.makeassert(right_assert);
        this.makeassert(bottom_assert);
        this.makeassert(current_assert);
        jess.run();
    }
    
    /**
     * Remove the first and the last character of the string (if it is a
     * quotation mark) and convert all backslash quote in quote It is used to
     * convert a Jess content into an ACL message content.
     */
    private String unquote(String str) {
        String t1 = str.trim();

        if (t1.startsWith("\"")) {
            t1 = t1.substring(1);
        }

        if (t1.endsWith("\"")) {
            t1 = t1.substring(0, t1.length() - 1);
        }

        int len = t1.length();
        int i = 0;
        int j = 0;
        int k = 0;
        char[] val = new char[len];
        t1.getChars(0, len, val, 0); // put chars into val

        char[] buf = new char[len];

        boolean maybe = false;

        while (i < len) {
            if (maybe) {
                if (val[i] == '\"') {
                    j--;
                }

                buf[j] = val[i];
                maybe = false;
                i++;
                j++;
            } else {
                if (val[i] == '\\') {
                    maybe = true;
                }

                buf[j] = val[i];
                i++;
                j++;
            }
        }

        return new String(buf, 0, j);
    }

    /**
     * @return the String representing the facts (even more than one fact is
     * allowed, but this method just returns one fact) to be asserted in Jess as
     * a consequence of the receipt of the passed ACL Message. The messate
     * content is quoted before asserting the Jess Fact. It is unquoted by the
     * JessFact2ACL function.
     */
    public String assertString(String string) {
        String fact;

        if (string == null) {
            return "";
        }

        fact = "(assert " + string + ")";

        return fact;
    }

    public void printFacts() {
        try {
            jess.executeCommand("(facts)");
        } catch (JessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
