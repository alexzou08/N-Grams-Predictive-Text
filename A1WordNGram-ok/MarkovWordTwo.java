
/**
 * Write a description of MarkovWordTwo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;

public class MarkovWordTwo implements IMarkovModel {
    private String[] myText;
    private Random myRandom;
    
    public MarkovWordTwo() {
        myRandom = new Random();
    }
    
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    public void setTraining(String text){
        //break strings into words
        myText = text.split("\\s+");
    }
    
    public String getRandomText(int numWords){
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length-2);  // random word to start with
        String key1 = myText[index];
        String key2 = myText[index+1];
        sb.append(key1);
        sb.append(" ");
        sb.append(key2);
        sb.append(" ");
        // numWords - 2 , because two have been setted
        for(int k=0; k < numWords-2; k++){
            ArrayList<String> follows = getFollows(key1, key2);
            //System.out.println("key is: "+key+"\t"+ "follows is:"+ follows);
            if (follows.size() == 0) {
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            key1 = key2;
            key2 = next;
        }
        return sb.toString().trim();
    }
    
    private int indexOf(String[] words, String target1, String target2, int start) {
        for(int k=start; k < words.length - 1; k++) {
            if(words[k].equals(target1)) {
                if(words[k + 1].equals(target2)) {
                    return k;
                }
            }
        }
        return -1;
    }
    
    private ArrayList<String> getFollows(String key1, String key2) {
        ArrayList<String> follows = new ArrayList<String>();
        int pos = 0;
        // pos + 1 dose not exceed the longest index
        while(pos < myText.length) {
            int start = indexOf(myText, key1, key2, pos);
            if(start == -1) {
                break;
            }
            // if pos = start + 2 is the last word, then there is no follows
            if(start + 2>= myText.length){
               break; 
            }
            String next = myText[start+2];
            follows.add(next);
            pos = start + 2;
        }
        return follows;
    }
}
