import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

/*****************************************************************

 *****************************************************************/
public class PokerDiceGUI extends JFrame implements ActionListener{

    /** the game that keeps track of everything */
    PokerDice theGame;

    /** score of the game */
    JLabel scoreLabel;

    /** a button for each scoring category */
    JButton rollButton;
    JButton smallStraightButton;
    JButton largeStraightButton;

    /** TO DO **/
    //Declare five buttons: threeKindButton, fourKindButton,
    //fiveKindButton, fullHouseButton, chanceButton;

    JButton threeKindButton;
    JButton fourKindButton;
    JButton fiveKindButton;
    JButton fullHouseButton;
    JButton chanceButton;

    /** menu items */
    JMenuBar menus;
    JMenu fileMenu;
    JMenuItem quitItem;
    JMenuItem newGameItem;

    /*********************************************
     * main method - starts the game
     *********************************************/
    public static void main(String [] args){
        PokerDiceGUI gui = new PokerDiceGUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setTitle("Poker Dice Game");
        gui.pack();
        gui.setVisible(true);
    }

    /***************************************************************
     Position the five dice and buttons on the screen
     ***************************************************************/
    public PokerDiceGUI(){
        // instantiate the game
        theGame = new PokerDice();
        setupGUI();
        setupMenu ();
        resetGame();

    }

    /************************************************************
     Setup all the GUI components
     ************************************************************/
    private void setupGUI(){

        // set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();

        // instantiate all buttons and JLabel
        rollButton = new JButton("roll");
        scoreLabel = new JLabel();
        smallStraightButton = new JButton("Small Straight");
        largeStraightButton = new JButton("Large Straight");

        /** TO DO **/
        // Initialize five buttons for 3-4-5 of kind,
        // fullhouse, and chance button

        threeKindButton = new JButton("Three of a Kind");
        fourKindButton = new JButton("Four of a Kind");
        fiveKindButton = new JButton("five of a Kind");
        fullHouseButton = new JButton("Full House");
        chanceButton = new JButton("Chance");


        // add all dice to the JFrame
        ArrayList<GVdie> dice = theGame.getDice();
        position.gridy = 1;
        position.gridx = 2;
        position.insets = new Insets(2,2,2,2);

        // positions the dice
        for (GVdie d : dice) {
            add(d, position);
            position.gridx++;
        }

        // add all buttons
        position.insets = new Insets(4,4,4,4);
        position.gridx = 2;
        position.gridy = 2;
        position.gridwidth = 2;
        add(smallStraightButton, position);

        position.gridy++;
        add(largeStraightButton, position);


        /** TO DO */
        //Add the Five buttons you created to the GUI
        //The location should be gridy++, basically one down
        position.gridy++;
        add(threeKindButton, position);

        position.gridy++;
        add(fourKindButton, position);

        position.gridy++;
        add(fiveKindButton, position);

        position.gridy++;
        add(fullHouseButton, position);

        position.gridy++;
        add(chanceButton, position);



        // add the roll button and the score
        position.gridwidth = 1;
        position.gridx = 4;
        position.gridy++;
        add(rollButton, position);

        position.gridx++;
        add(scoreLabel, position);

        // register all buttons with the Action Listener
        smallStraightButton.addActionListener(this);
        largeStraightButton.addActionListener(this);
        rollButton.addActionListener(this);

        /** TO DO */
        // Add action listeners to the FIVE buttons you put in the GUI

        threeKindButton.addActionListener(this);
        fourKindButton.addActionListener(this);
        fiveKindButton.addActionListener(this);
        fullHouseButton.addActionListener(this);
        chanceButton.addActionListener(this);


    }

    /****************************************************************
     * setup Menu
     ****************************************************************/
    private void setupMenu () {
        fileMenu = new JMenu("File");
        quitItem = new JMenuItem("Quit");
        newGameItem = new JMenuItem("New Game");
        fileMenu.add(newGameItem);
        fileMenu.add(quitItem);
        menus = new JMenuBar();
        setJMenuBar(menus);
        menus.add(fileMenu);

        // register the menu items with this action listener
        quitItem.addActionListener(this);
        newGameItem.addActionListener(this);
    }

    /*****************************************************************
     Handles all button and menu selection
     @param e the component that was pressed
     *****************************************************************/
    public void actionPerformed (ActionEvent e){
        // determine which button or menu was selected
        JComponent buttonPressed = (JComponent) e.getSource();

        if (buttonPressed == quitItem){
            System.exit(1);
        }

        if (buttonPressed == newGameItem){
            resetGame();
        }

        // For each of the scoring categories
        // if the button is selected
        //     ask the game to check the category
        //     disable button
        if (buttonPressed == largeStraightButton)
        {
            theGame.checkLargeStraight();
            buttonPressed.setEnabled(false);
        }
        if (buttonPressed == smallStraightButton)
        {
            theGame.checkSmallStraight();
            buttonPressed.setEnabled(false);
        }
        if (buttonPressed == threeKindButton)
        {
            theGame.checkThreeOfAKind();
            buttonPressed.setEnabled(false);
        }
        if (buttonPressed == fourKindButton)
        {
            theGame.checkFourOfAKind();
            buttonPressed.setEnabled(false);
        }
        if (buttonPressed == fiveKindButton)
        {
            theGame.checkFiveOfAKind();
            buttonPressed.setEnabled(false);
        }
        if (buttonPressed == fullHouseButton)
        {
            theGame.checkFullHouse();
            buttonPressed.setEnabled(false);
        }
        if (buttonPressed == chanceButton)
        {
            theGame.checkChance();
            buttonPressed.setEnabled(false);
        }

        // roll each dice that is not held
        if (buttonPressed == rollButton)
            theGame.rollDice();

        // if OK to roll then enable the button
        if (theGame.okToRoll())
            rollButton.setEnabled(true);
        else
            rollButton.setEnabled(false);

        scoreLabel.setText("Score: " + theGame.getScore());
        if (theGame.gameOver())
        {
            // display message
            JOptionPane.showMessageDialog (null, "Game ended");
            rollButton.setEnabled(false);
        }
    }

    /************************************************************
     Reset the game, the score, and enable all buttons
     ************************************************************/
    public void resetGame(){
        theGame.resetGame();
        scoreLabel.setText("Score: " + theGame.getScore());

        // enable all buttons
        smallStraightButton.setEnabled(true);
        largeStraightButton.setEnabled(true);
        threeKindButton.setEnabled(true);
        fourKindButton.setEnabled(true);
        fiveKindButton.setEnabled(true);
        fullHouseButton.setEnabled(true);
        chanceButton.setEnabled(true);

    }
}
