
/**
 * Write a description of class MarkovWordOne here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class MarkovWordOne implements IMarkovModel {
    private String[] myText;
    private Random myRandom;
    
    public MarkovWordOne() {
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
        int index = myRandom.nextInt(myText.length-1);  // random word to start with
        String key = myText[index];
        sb.append(key);
        sb.append(" ");
        for(int k=0; k < numWords-1; k++){
            ArrayList<String> follows = getFollows(key);
            //System.out.println("key is: "+key+"\t"+ "follows is:"+ follows);
            if (follows.size() == 0) {
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            key = next;
        }
        return sb.toString().trim();
    }
    
    private int indexOf(String[] words, String target, int start) {
        for(int k=start; k < words.length; k++) {
            if(words[k].equals(target)) {
                return k;
            }
        }
        return -1;
    }
    
    public void testIndexOf() {
        String input = "this is just a test yes this is a simple test";
        String[] words = input.split("\\s+");
        int a = indexOf(words, "this", 0);
        int b = indexOf(words, "this", 3);
        int c = indexOf(words, "frog", 0);
        int d = indexOf(words, "frog", 5);
        int e = indexOf(words, "simple", 2);
        int f = indexOf(words, "test", 5);
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
        System.out.println(e);
        System.out.println(f);
    }
    
    private ArrayList<String> getFollows(String key) {
        ArrayList<String> follows = new ArrayList<String>();
        int pos = 0;
        while(pos < myText.length) {
            int start = indexOf(myText, key, pos);
            if(start == -1) {
                break;
            }
            // if pos = start + 1 is the last word, then there is no follows
            if(start + 1>= myText.length){
               break; 
            }
            String next = myText[start+1];
            follows.add(next);
            pos = start + 1;
        }
        return follows;
    }

}
