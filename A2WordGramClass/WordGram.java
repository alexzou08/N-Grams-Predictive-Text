
public class WordGram {
    private String[] myWords;
    private int myHash;

    public WordGram(String[] source, int start, int size) {
        myWords = new String[size];
        System.arraycopy(source, start, myWords, 0, size);
    }

    public String wordAt(int index) {
        if (index < 0 || index >= myWords.length) {
            throw new IndexOutOfBoundsException("bad index in wordAt "+index);
        }
        return myWords[index];
    }

    public int length(){
        return myWords.length;
    }

    public String toString(){
        String ret = "";
        for(int k=0; k < myWords.length; k++) {
            ret += myWords[k] + " ";
        }
        return ret.trim();
    }

    public boolean equals(Object o) {
        WordGram other = (WordGram) o;
        // compare me to other
        if(this.length() != other.length()) {
            return false;
        }
        for(int k=0; k < myWords.length; k++) {
            if(! myWords[k].equals(other.wordAt(k))) {
                return false;
            }
        }
        return true;
    }
    //return a new WordGram the same size as the original, consisting of each word 
    //shifted down one index (for example the word in slot 1 would move to slot 0, 
    //the word in slot 2 would move to slot 1, etc.) and word added to the end of 
    //the new WordGram.This method should not alter the WordGram on which it is called.
    public WordGram shiftAdd(String word) { 
        WordGram out = new WordGram(myWords, 0, myWords.length);
        for(int k=1; k < myWords.length; k++) {
            out.myWords[k-1] = myWords[k];
        }
        out.myWords[myWords.length - 1] = word;
        return out;
    }
    
    public int hashCode() {
        return this.toString().hashCode();
    }
}