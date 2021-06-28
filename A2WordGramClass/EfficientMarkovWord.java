
/**
 * Write a description of EfficientMarkovWord here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
import edu.duke.*;

public class EfficientMarkovWord implements IMarkovModel{
    private HashMap<WordGram, ArrayList<String>> map;
    private String[] myText;
    private Random myRandom;
    private int myOrder;
    
    public EfficientMarkovWord(int order) {
        myOrder = order;
        map = new HashMap<WordGram, ArrayList<String>>();
    }
    
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    private void buildMap() {
        for(int k=0; k < myText.length - myOrder; k++) {
            WordGram wg = new WordGram(myText, k, myOrder);
            String next = myText[k + myOrder];
            if(map.containsKey(wg)) {
                map.get(wg).add(next);
            } else {
                ArrayList<String> list = new ArrayList<String>();
                list.add(next);
                map.put(wg, list);
            }
        }
    }
    
    public void setTraining(String text){
        //break strings into words
        myText = text.split("\\s+");
        buildMap();
    }

    private ArrayList<String> getFollows(WordGram kGram) {
        ArrayList<String> follows = new ArrayList<String>();
        return map.get(kGram);
    }
    
    @Override
    public String getRandomText(int numWords){
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length - myOrder);  // random word to start with
        WordGram key = new WordGram(myText, index, myOrder);
        sb.append(key.toString());
        sb.append(" ");
        for(int k=0; k < numWords-myOrder; k++){
            ArrayList<String> follows = getFollows(key);
            //System.out.println("key is: "+key+"\t"+ "follows is:"+ follows);
            if (follows == null || follows.size() == 0) {
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
    
    public void printHashMapInfo() {
        int maxSetSize = -1;
        for(WordGram wg : map.keySet()) {
            maxSetSize = Math.max(maxSetSize, map.get(wg).size());
            System.out.println(wg + "\t: " + map.get(wg));
        }
        System.out.println("Number of keys: \t" + map.keySet().size());
        System.out.println("Max Set Size: \t" + maxSetSize);
        System.out.println("Keys with Max Size:");
        for(WordGram wg : map.keySet()) {
            if(map.get(wg).size() == maxSetSize) {
                System.out.println(wg);
            }
        }
    }
    
    public String toString() {
        return "HashMap - MarkovWord Order of " + myOrder;
    }
}
