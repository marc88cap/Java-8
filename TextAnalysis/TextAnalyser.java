import java.util.*;

public class TextAnalyser{
    public static void main(String[] args){
        TextAnalyser as = new TextAnalyser();
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter text: ");
        String text = sc.nextLine();
        as.processText(text);
        System.out.println();
    }
    
    public void processText(String text){
        ArrayList<Letter> letters = new ArrayList<>(26);
        ArrayList<Word> wordLengths = new ArrayList<>(text.replaceAll("\\W","").split("\\s").length);
        ArrayList<Word> words = new ArrayList<>(text.replaceAll("\\W","").split("\\s").length);
        int index;
        
        //loop words
        for(String w : text.toLowerCase().split("\\s")){
            w = w.replaceAll("[^a-zA-Z\'-]","").toLowerCase();
            //count same words
            Word word = new Word(w);
            index = words.indexOf(word); //look in array for same object
            if(index>=0){ //if index is same or more than 0, same obj exists
                words.get(index).incrementCounterByOne(); //inc existing obj
            }
            else{
                words.add(word); //add new object to ArrayList
            }
            //count same length words
            Word wordLen = new Word(w.length());
            index = wordLengths.indexOf(wordLen);
            if(index>=0){
                wordLengths.get(index).incrementCounterByOne();
            }
            else{
                wordLengths.add(wordLen);
            }
            //count chars
            for(char c : w.replaceAll("\\W","").toCharArray()){
                String ch = String.valueOf(c);
                Letter letter = new Letter(ch);
                index = letters.indexOf(letter);
                if(index>=0)
                    letters.get(index).incrementCounterByOne();
                else{
                    letters.add(letter);
                }
            }
        }
        //sort chars from a to z
        Collections.sort(letters, new CompareLetters());
        Collections.sort(wordLengths, new CompareNumbers());
        System.out.println("\nCount characters: ");
        for(int i = 0 ; i<letters.size(); i++){ //print same character count
                System.out.println(letters.get(i).getLetter()+" -> "
                                    +letters.get(i).getCounter());
        }
        
        System.out.println("\nCount words: ");
        for(int i = 0; i<words.size(); i++){ //print words count
            System.out.printf("%s -> %d%n",words.get(i).getWord(),
                                            words.get(i).getCounter());
        }
        System.out.println();
        for(int i = 0; i<wordLengths.size(); i++){ //print same word length count
            System.out.printf("%d-letter words -> %dx%n",
                                wordLengths.get(i).getLength(),
                                wordLengths.get(i).getCounter());
        }
    }
    
    public class Letter{
        private String c;
        private int counter = 1;
        public Letter(String c){
            this.c = c;
        }
        
        public void incrementCounterByOne(){
            this.counter++;
        }
        
        public int getCounter(){
            return this.counter;
        }
        
        public String getLetter(){
            return this.c;
        }
        
        public boolean equals(Object ob){
            Letter l = (Letter)ob;
            return (l.getLetter().equals(c));
        }
    }
    
    public class Word{
        private int length, counter = 1;
        private String word;
        
        public Word(int length){
            this.length = length;
        }
        
        public Word(String w){
            this.word = w;
        }
        
        public void incrementCounterByOne(){
            this.counter++;
        }
        
        public String getWord(){
            return this.word;
        }
        
        public int getLength(){
            return this.length;
        }
        
        public int getCounter(){
            return this.counter;
        }
        //indexOf declaration
        public boolean equals(Object ob){
            Word w = (Word)ob;
            return ((w.word!=null)?(w.word.equals(this.word)):(w.length==this.length));
        }
    }
    //sort declaration for Characters
    public class CompareLetters implements Comparator<Letter>{
        @Override
        public int compare(Letter l1, Letter l2) {
            return l1.getLetter().compareTo(l2.getLetter());
        }
    
    }
    //sort declaration for Numbers
    public class CompareNumbers implements Comparator<Word>{
        @Override
        public int compare(Word o1, Word o2) {
            return o1.getLength()-o2.getLength();
        }
    
    }
}
