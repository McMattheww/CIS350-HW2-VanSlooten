import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class PokerDice {

    private ArrayList<GVdie> dice;
    private HashMap<Integer, Integer> tally;

    private int score;
    private int numRolls;
    private int numRounds;

    private static final int THREE_OF_A_KIND = 25;
    private static final int FOUR_OF_A_KIND = 40;
    private static final int FIVE_OF_A_KIND = 50;
    private static final int FULL_HOUSE = 35;
    private static final int SMALL_STRAIGHT = 30;
    private static final int LARGE_STRAIGHT = 45;
    private static final int NUM_DICE = 5;

    //constructor -
    public PokerDice(){
        dice = new ArrayList<GVdie>();
        for(int i = 0; i < NUM_DICE; i++){
            dice.add(new GVdie());
        }
        tally = new HashMap<Integer, Integer>();
        resetGame();
    }

    public void resetGame(){
        score = 0;
        numRolls = 0;
        numRounds = 0;
        for (GVdie d: dice){
            d.setBlank();
            d.setHeld(false);
        }
    }

    public int getScore(){
        return score;
    }
    public int getNumRolls(){
        return numRolls;
    }
    public int getNumRounds(){
        return numRounds;
    }
    public ArrayList<GVdie> getDice(){
        return dice;
    }

    public boolean okToRoll(){//only three rolls allowed per round, otherwise false to indicate you cant roll again.
        boolean val;
        val = numRolls < 3;
        return val;
    }
    public boolean gameOver(){  //set true when game is over - after 7 rounds
        boolean val;
        val = numRounds >= 7;
        return val;
    }


    private void tallyDice(){
        for(int i = 1; i<7; i++ ){
            tally.put(i, 0);
        }
        for(GVdie die: dice){
            int newVal = tally.get(die.getValue()) + 1;
            tally.put(die.getValue(), newVal);
        }
    }

    private boolean hasStraight(int length){
        int currentStraight = 0;
        int maxStraight = 0;
        boolean hasStraight = false;
        tallyDice();

        for(int i = 1; i<7; i++ ){
            if(tally.get(i) != 0){
                currentStraight += 1;
            }
            else {
                currentStraight = 0;
            }

            if (currentStraight > maxStraight){
                maxStraight = currentStraight;
            }
        }

        if (maxStraight >= length){
            hasStraight = true;
        }
        return hasStraight;
    }

    private boolean hasMultiples(int count){
        int maxMultiples = 0;
        boolean hasMultiples = false;
        tallyDice();

        for(int i = 1; i<7; i++ ){
            if(tally.get(i) > maxMultiples){
                maxMultiples = tally.get(i);
            }
        }
        if(maxMultiples >= count){
            hasMultiples = true;
        }
        return hasMultiples;
    }

    public boolean hasStrictPair(){
        boolean hasPair = false;
        tallyDice();

        for(int i = 1; i<7; i++ ){
            if(tally.get(i) == 2){
                hasPair = true;
            }
        }
        return hasPair;
    }

    private void nextRound(){
        numRounds += 1;
        numRolls = 0;
        for (GVdie d: dice){
            d.setBlank();
            d.setHeld(false);
        }
    }



    public void rollDice(){
        for (GVdie die: dice){
            if (!die.isHeld()){
                die.roll();
            }
        }
        numRolls ++;
    }

    public void checkThreeOfAKind(){
        if(hasMultiples(3)){
            score += THREE_OF_A_KIND;
        }
        nextRound();
    }

    public void checkFullHouse(){
        if (hasMultiples(3) && hasStrictPair()){
            score += FULL_HOUSE;
        }
        else if (hasMultiples(5)) {
            score += FULL_HOUSE;
        }
        nextRound();
    }

    public void checkSmallStraight(){
        if(hasStraight(4)){
            score += SMALL_STRAIGHT;
        }
        nextRound();
    }

    public void checkLargeStraight(){
        if(hasStraight(5)){
            score += LARGE_STRAIGHT;
        }
        nextRound();
    }

    public void checkFiveOfAKind(){
        if(hasMultiples(5)){
            score += FIVE_OF_A_KIND;
        }
        nextRound();
    }

    public void checkFourOfAKind(){
        if (hasMultiples(4)){
            score += FOUR_OF_A_KIND;
        }
        nextRound();
    }

    public void checkChance(){
        int sum = 0;
        for(GVdie die: dice){
            sum += die.getValue();
        }
        score += sum;
        nextRound();
    }





    public String diceToString(){
         String str = " [";
         for (GVdie die: dice){
             str += die.getValue() + ",";
         }
         str = str.substring(0, str.length() - 1);
         str += "] ";
         return str;
    }

    public void setDice(int[] values) {  //for testing, set dice to specific values.
        for(int i = 0; i < values.length; i++){
            do{
                dice.get(i).roll();
            }while (dice.get(i).getValue() != values[i]);
        }
    }

}
