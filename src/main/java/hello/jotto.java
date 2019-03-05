package hello;

import java.io.*;
import java.util.*;
public class jotto
{
    //Randomly find 3 words without repeating letter
    public static ArrayList<String> randomly3Words(ArrayList<String> wl){
        ArrayList<String> result = new ArrayList<String>();
        Random rand = new Random();
        boolean notfound=true;
        String word1="";
        String word2="";
        String word3="";
        while(notfound) {
            word1 = wl.get(rand.nextInt(wl.size()));
            ArrayList<String> array1 = findWordNo(word1, wl);
            if (array1.size() > 0) {
                word2 = array1.get(rand.nextInt(array1.size()));
                ArrayList<String> array2 = findWordNo(word2, array1);
                if (array2.size() > 0) {
                    word3 = array2.get(rand.nextInt(array2.size()));
                    notfound = false;
                }
            }
        }
        result.add(word1);
        result.add(word2);
        result.add(word3);
        return result;
    }

    //Update table
    public static HashMap<String, Integer> updateTable(HashMap<String, Integer> table){
        HashMap<String, Integer> result=new HashMap<String, Integer>();
        for(Map.Entry<String, Integer> e : table.entrySet()) {
            String key = e.getKey();
            Integer value = e.getValue();
            boolean update=false;
            if(isinNumber(key)==value){
                for (int i = 0; i <key.length();i++) {
                    if(!isin.contains(key.charAt(i)+"")) {
                        if(!notin.contains(key.charAt(i)+""))
                            notin+=key.charAt(i)+"";
                    }
                }
                update=true;
            }
            else if(notinNumber(key)+value==key.length()){
                for (int i = 0; i <key.length();i++) {
                    if(!isin.contains(key.charAt(i)+"")) {
                        if(!notin.contains(key.charAt(i)+""))
                            isin+=key.charAt(i)+"";
                    }
                }
                update=true;
            }
            if(!update){
                String newKey="";
                int newValue=value;
                for (int i = 0; i <key.length();i++) {
                    if(isin.contains(key.charAt(i)+"")){
                        newValue-=1;
                    }
                    else if(notin.contains(key.charAt(i)+"")){
                    }
                    else
                        newKey+=key.charAt(i);
                }
                result.put(newKey,newValue);
            }
        }
        return result;
    }

    //return number in the isin of a string
    public static int isinNumber(String s){
        int count=0;
        if(isin.length()>0) {
            for (int i = 0; i <isin.length();i++) {
                if(s.contains(isin.charAt(i)+""))
                    count+=1;
            }
        }
        return count;
    }

    //return number in the notin of a string
    public static int notinNumber(String s){
        int count=0;
        if(notin.length()>0) {
            for (int i = 0; i <notin.length();i++) {
                if(s.contains(notin.charAt(i)+""))
                    count+=1;
            }
        }
        return count;
    }


    //Find word list that containing no elements from a string
    public static ArrayList<String> findWordNo(String s, ArrayList<String> wl){
        ArrayList<String> result = new ArrayList<String>();
        for (int i=0;i<wl.size();i++){
            boolean contain =false;
            for(int j=0;j<s.length();j++){
                if (wl.get(i).contains(s.charAt(j)+""))
                    contain=true;
            }
            if (!contain)
                result.add(wl.get(i));
        }
        return result;
    }

    //Find word list that containing all the elements of a string
    public static ArrayList<String> findWord(String s, ArrayList<String> wl){
        ArrayList<String> result = new ArrayList<String>();
        for (int i=0;i<wl.size();i++){
            int count =0;
            for(int j=0;j<s.length();j++){
                if (wl.get(i).contains(s.charAt(j)+""))
                    count+=1;
            }
            if (count==s.length())
                result.add(wl.get(i));
        }
        return result;
    }
    //Find word list that containing all the elements of a s1 and other letters are all from s2
    public static ArrayList<String> findWordConditions(String s1, String s2,ArrayList<String> wl){

        ArrayList<String> result = new ArrayList<String>();
        for (int i=0;i<wl.size();i++){
            int count1=0;
            int count2=0;
            for(int j=0;j<s1.length();j++){
                if (wl.get(i).contains(s1.charAt(j)+""))
                    count1+=1;
            }
            for(int j=0;j<s2.length();j++){
                if (wl.get(i).contains(s2.charAt(j)+""))
                    count2+=1;
            }
            if (count1==s1.length() && count2==5-s1.length())
                result.add(wl.get(i));
        }
        return result;
    }
    //Return number of letters thar are in the choosing word
    public static int returnNumber(String word,String choosingWord){
        int count=0;
        for(int i=0;i<word.length();i++){
            if(choosingWord.contains(word.charAt(i)+""))
                count+=1;
        }
        return count;
    }
    //Return a string that contains no elements in given string
    public static String returnString(String s,String given){
        String result="";
        for (int i=0;i<given.length();i++){
            if(!s.contains(given.charAt(i)+""))
                result+=given.charAt(i);
        }
        return result;
    }

    public static String pickWord(HashMap<String, Integer>table,ArrayList<String> wl,ArrayList<String> wordGuessed,int step,ArrayList<String> rand3w){
        if(isin.length()==5){
            ArrayList<String> fw=findWord(isin,wl);
            if (fw.size()>0) {
                for (int j = 0; j < fw.size(); j++) {
                    if (!wordGuessed.contains(fw.get(j)))
                        return fw.get(j);
                }
            }
            else return "There is no such word!";
        }
        else if(step<=3){
            return rand3w.get(step-1);
        }
        else if(isin.length()==4){
            String s= (String) table.keySet().toArray()[0];
            for(int i=0;i<s.length();i++){
                ArrayList<String> fw=findWord(isin+s.charAt(i),wl);
                if (fw.size()>0) {
                    for (int j = 0; j < fw.size(); j++) {
                        if (!wordGuessed.contains(fw.get(j)))
                            return fw.get(j);
                    }
                }
            }
        }

        else if(table.size()==1){
            String s= (String) table.keySet().toArray()[0];
            for(int i=0;i<s.length();i++){
                ArrayList<String> fwc=findWordConditions(isin,s,wl);
                if (fwc.size()>0) {
                    for (int j = 0; j < fwc.size(); j++) {
                        if (!wordGuessed.contains(fwc.get(j)))
                            return fwc.get(j);
                    }
                }
            }
        }
        else{
            for(int i=0;i<wl.size();i++) {
                boolean state=true;
                for (Map.Entry<String, Integer> e : table.entrySet()) {
                    int count=0;
                    String key = e.getKey();
                    Integer value = e.getValue();
                    for(int j=0;j<key.length();j++){
                        if (wl.get(i).contains(key.charAt(j)+""))
                            count+=1;
                    }
                    if(count!=value) {
                        state = false;
                    }
                }
                if ((state)&&(!wordGuessed.contains(wl.get(i)))){
                    return wl.get(i);
                }

            }
        }

        return "";
    }


    public static String isin="";
    public static String notin="";
    public ArrayList<String> result1 = new ArrayList<String>();
    public ArrayList<String> wordList = new ArrayList<String>();
    public String secret;
    HashMap<String, Integer> table = new HashMap<String, Integer>();
    ArrayList<String> rand3w;
    public jotto()throws Exception{
        File file = new File("src/main/java/hello/WordList.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st="";
        isin="";
        notin="";
        while ((st = br.readLine()) != null)  {
            this.wordList.add(st);
        }
        rand3w=randomly3Words(this.wordList);
        Random rand = new Random();
        secret = wordList.get(rand.nextInt(wordList.size()));
    }

    public int count(String ug){
        int i;
        int re=0;
        for(i=0;i<ug.length();i++){
            if(secret.indexOf(ug.charAt(i))>=0){
                re+=1;
            }
        }
        return re;
    }



    public String process1(int step) {

        //int number=0;
        //step+=1;
        String pick = pickWord(table, wordList, this.result1, step, rand3w);
        if (pick.equals("")) {
            return "You got wrong!";
        }
        //System.out.println("Guess "+(step+"")+": "+pick);
        //Scanner input = new Scanner(System.in);
        //number = input.nextInt();
        return pick;
    }
    public void process2(int step,int number,String pick){
        this.result1.add(pick);
            String atoz="abcdefghijklmnopqrstuvwxyz";
            this.table.put(pick,number);
            if(step==3) {
                String forth = returnString(rand3w.get(0) + rand3w.get(1) + rand3w.get(2), atoz);
                int sum=0;
                for ( Integer e: table.values() ) {
                    sum+=e;
                }
                table.put(forth,5-sum);
            }

            for (int i=0;i<table.size();i++){
                table=updateTable(table);
            }
            System.out.println("in:"+isin);
            System.out.println("notin:"+notin);
            System.out.println(table);
    }
}
