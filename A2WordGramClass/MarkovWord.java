
/**
 * Write a description of MarkovWord here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;

public class MarkovWord implements IMarkovModel{
    private String[] myText;
    private Random myRandom;
    private int myOrder;
    
    public MarkovWord(int order) {
        myOrder = order;
        myRandom = new Random();
    }
    
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    public void setTraining(String text){
        //break strings into words
        myText = text.split("\\s+");
    }
    
    //This method should return the first position from start that has words in 
    //the array words that match the WordGram target. 
    //If there is no such match then return -1.
    private int indexOf(String[] words, WordGram target, int start) {
        for(int k=start; k < words.length-myOrder; k++) {
            WordGram wg = new WordGram(words, k, myOrder);
            if(wg.equals(target)) {
                return k;
            }
        }
        return -1;
    }
    
    //This method returns an ArrayList of all the single words 
    //that immediately follow an instance of the WordGram parameter 
    //somewhere in the training text.
    private ArrayList<String> getFollows(WordGram kGram) {
        ArrayList<String> follows = new ArrayList<String>();
        int index = indexOf(myText, kGram, 0);
        while(index != -1) {
            follows.add(myText[index + myOrder]);
            index = indexOf(myText, kGram, index + 1);
        }
        /* below is another approach
        int pos = 0;
        while(pos < myText.length) {
            int start = indexOf(myText, kGram, pos);
            if(start == -1) {
                break;
            }
            // if pos = start + myOrder is the last string, then there is no follows
            if(start + myOrder >= myText.length){
               break; 
            }
            String next = myText[start+myOrder];
            follows.add(next);
            pos = start + myOrder;
        }
        */
        return follows;
    }
    
    //@Override
    public String getRandomText(int numWords){
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length - myOrder);  // random word to start with
        WordGram key = new WordGram(myText, index, myOrder);
        sb.append(key.toString());
        sb.append(" ");
        for(int k=0; k < numWords-myOrder; k++){
            ArrayList<String> follows = getFollows(key);
            //System.out.println("key is: "+key+"\t"+ "follows is:"+ follows);
            if (follows == null ||follows.size() == 0) {
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            key = key.shiftAdd(next);
        }
        return sb.toString().trim();
    }
    
    public String toString() {
        return "MarkovWord Order of " + myOrder;
    }
}
