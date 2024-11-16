public class PokerDiceManager {

    public static void main (String[] args) {
        int oldScore;
        System.out.println ("Testing begins\n");
        PokerDice game = new PokerDice();
        //********** phase 1 testing ************
        // testing the constructor
        assert game.getDice().size() == 5 : "dice should be an ArrayList of five";


        System.out.print("Testing values:");
        int[] values = {4, 4, 4, 4, 5};
        game.setDice(values);
        System.out.print(game.diceToString()); //values should be same as defined in values array
        System.out.println("- values used to test scoring");

        System.out.print("Starting score: ");
        System.out.println(game.getScore()); //scoring testing

        System.out.print("Total score after checking three of kind: ");
        game.checkThreeOfAKind();
        System.out.println(game.getScore()); //scoring testing

        System.out.print("Total score after checking four of kind: ");
        game.checkFourOfAKind();
        System.out.println(game.getScore());

        System.out.print("Total score after checking five of kind: ");
        game.checkFiveOfAKind();
        System.out.println(game.getScore());

        System.out.print("Total score after checking large straight: ");
        game.checkLargeStraight();
        System.out.println(game.getScore());

        System.out.print("Total score after checking small straight: ");
        game.checkSmallStraight();
        System.out.println(game.getScore());

        System.out.print("Total score after checking full house: ");
        game.checkFullHouse();
        System.out.println(game.getScore());

        System.out.print("Total score after checking chance: ");
        game.checkChance();
        System.out.println(game.getScore());
        System.out.println();


        System.out.print("Testing roll, should result in 5 random values,\n");
        game.rollDice();
        System.out.print("5 random values:");
        System.out.println(game.diceToString() + "\n");  //values should be new random values






        // TO DO: Add logic to test phase 1 methods
        System.out.println ("Testing ends");
    }


}


