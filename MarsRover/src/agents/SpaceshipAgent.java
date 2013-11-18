package agents;

import behaviours.SendLocationBehaviour;
import behaviours.SpaceshipBehaviour;
import jade.core.AID;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ontologies.GridField;
import swing.GridComponent;
import swing.BoardInstructionsComponent;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpaceshipAgent extends Agent {
    
    private GridField[][] map;
    private int dimensions;
    private JFrame mapFrame;
    private GridComponent gridComp;
    private BoardInstructionsComponent boardComp;
    private int gridSize;
    private int xSpaceship;
    private int ySpaceship;
    private int xSpaceshipGridPos;
    private int ySpaceshipGridPos;
    private Agent agent;
    private List<AID> rovers;

    protected void setup(){
            Object[] args = getArguments();
            agent = this;

            dimensions = 15;
            gridSize = 25;

            rovers = new ArrayList<>();

            map = new GridField[dimensions][dimensions];

            mapFrame = new JFrame();
            mapFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            mapFrame.setPreferredSize(new Dimension(800, 680));

            gridComp = new GridComponent();
            gridComp.setPreferredSize(new Dimension(200, 200));

            boardComp = new BoardInstructionsComponent();
            boardComp.setPreferredSize(new Dimension(90, 90));

            JButton button = new JButton("Send Spaceship Location");

            JPanel compPanel = new JPanel();
            compPanel.setLayout(new BorderLayout());
            compPanel.add(boardComp, BorderLayout.WEST);
            compPanel.add(gridComp, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(button);

            mapFrame.getContentPane().add(compPanel, BorderLayout.CENTER);
            mapFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

            mapFrame.pack();
            mapFrame.setVisible(true);

            fillMap();

            setSpaceshipLocation();

            printMap();
            printSpaceshipLocation();

            initObstacles();
            initRocks();

            printObsticlesAndRocks();

            printBoard();

            button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        agent.addBehaviour(new SendLocationBehaviour(agent));
                    }
            });

            try{
                DFService.register(agent, getDFAgentDescription());
                updateAvailableRovers();
            }catch(FIPAException fe){
                fe.printStackTrace();
            }

            addBehaviour(new SpaceshipBehaviour(this));
    }

    public List<AID> getRovers(){
        return rovers;
    }
    
    public int getxSpaceshipGridPos() {
        return xSpaceshipGridPos;
    }

    public int getySpaceshipGridPos() {
        return ySpaceshipGridPos;
    }

    public DFAgentDescription getDFAgentDescription(){

        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setName( getLocalName() );
        serviceDescription.setType("spaceship-agent");

        DFAgentDescription dfAgentDescription = new DFAgentDescription();
        dfAgentDescription.setName( getAID() );
        dfAgentDescription.addServices(serviceDescription);

        return dfAgentDescription;
    }

    private void updateAvailableRovers(){
        DFAgentDescription dfAgentDescription = new DFAgentDescription();

        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType("rover-agent");

        dfAgentDescription.addServices(serviceDescription);

        try{
            DFAgentDescription[] result = DFService.search( agent, dfAgentDescription );
            System.out.println("");
            System.out.println("----< Spaceship found following rovers >----");
            for( int i = 0; i < result.length; i++){
                rovers.add(result[i].getName());
                System.out.println("Rover " + (i+1) + ": " + result[i].getName().getLocalName() );
            }
            System.out.println("");
            if(rovers.isEmpty()){
                System.out.println("No rovers available");
                doDelete();
            }

        } catch(FIPAException fe){
            fe.printStackTrace();
        }
    }
        
    public void printSpaceshipLocation(){
            gridComp.addGrid(xSpaceship, ySpaceship, gridSize, gridSize, Color.BLUE, "S");
    }

    public void printBoard(){
        boardComp.addText("Spaceship", "Obsticles", "Rocks");
    }

    public void printObsticlesAndRocks(){
        for(int tx = 0; tx < dimensions; tx++){
            for(int ty = 0; ty < dimensions; ty++){
                int i = ((tx+1)*gridSize) + 100;
                int j = ((ty+1)*gridSize) + 20;
                if(map[tx][ty].isObstacle()){
                    gridComp.addGrid(i, j, gridSize, gridSize, new Color(229, 24, 24), "O");
                }
                if(map[tx][ty].getNumberOfRocks() != 0){
                    gridComp.addGrid(i, j, gridSize, gridSize, new Color(52, 181, 95), "" + map[tx][ty].getNumberOfRocks());
                }

            }
        }
    }
			
    public void printMap(){

            for(int tx = 0; tx < dimensions; tx++){
                    for(int ty = 0; ty < dimensions; ty++){
                            int x = ((tx+1)*gridSize) + 100;
                            int y = ((ty+1)*gridSize) + 20;
                            int ss = (Math.abs(xSpaceshipGridPos - tx)+Math.abs(ySpaceshipGridPos - ty));

                            gridComp.addGrid(x, y, gridSize, gridSize, Color.BLACK, "");

                            map[ty][ty].setSignalStrength(ss);
                    }

            }
    }
	
    public void initObstacles(){
        Random rand = new Random();
        int noOfObstacles = (rand.nextInt(4) + 3);

        int count = 0;
        while(count < noOfObstacles){
            int i = rand.nextInt(dimensions);
            int j = rand.nextInt(dimensions);

            if(!map[i][j].isObstacle() && !map[i][j].isSpaceship()){
                map[i][j].setObstacle(true);
                count++;
            }
        }

    }
        
    public void initRocks(){
        Random rand = new Random();
        int count = 0;
        int noOfRocks = (rand.nextInt(6) + 10);

        while(count < noOfRocks){
            int i = rand.nextInt(dimensions);
            int j = rand.nextInt(dimensions);

            if(!map[i][j].isObstacle() && !map[i][j].isSpaceship() && map[i][j].getNumberOfRocks() == 0){
                int cluster = rand.nextInt(10)+1;
                if(cluster < 4){
                    map[i][j].setNumberOfRocks(1);
                }
                else {
                    map[i][j].setNumberOfRocks(cluster);
                }
                count++;
            }
        }
    }
        
    public void setSpaceshipLocation(){
        xSpaceshipGridPos = (int) (Math.random() * dimensions);
        ySpaceshipGridPos = (int) (Math.random() * dimensions);
        map[xSpaceshipGridPos][ySpaceshipGridPos].setSpaceship(true);
        xSpaceship = ((xSpaceshipGridPos+1)*gridSize) + 100;
        ySpaceship = ((ySpaceshipGridPos+1)*gridSize) + 20;
    }
	
    public void fillMap(){
	for(int i = 0; i < dimensions; i++){
            for(int j = 0; j < dimensions; j++){
                map[i][j] = new GridField();
		
                if(i == 0){
                    map[i][j].setTop(false);
		}
                if(i == dimensions){
                    map[i][j].setBottom(false);
		}
		if(j == 0){
                    map[i][j].setLeft(false);
                }
                if(j == dimensions){
                    map[i][j].setRight(false);
                }	
            }
        }
    }    
}
