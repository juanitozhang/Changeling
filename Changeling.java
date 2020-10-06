import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Changeling {

  public static List<String> solved(String father, Map<String,String> parents){
    List<String> out = new LinkedList<>();
    while(father != null){
      out.add(father);
      father = parents.get(father);
    }
    return out;
  }

  public static List<String> nextLevel(String start,  List<String> dict, int len){
    List<String> out = new LinkedList<>();
    for (String s: dict){
    if (s.length() == len){
      for (int i =0; i < len; i++){
          Boolean app = true;
          for (int j =0;j<len;j++){
              if (i == j && s.charAt(i) == start.charAt(i)){
                  app = false;
              }
              if (i != j && s.charAt(j) != start.charAt(j)){
                  app = false;
              }
          }
          if (app){
              out.add(s);
              break;
          }
      }
    }
    }
    return out;

  }


  public static List<String> solve(String start, String target, List<String> dict, int len){
    Map<String,String> parents = new HashMap<>();
    Queue<String> breadth = new LinkedList<>();
    parents.put(start, null);
    breadth.add(start);
    while (breadth.size() != 0) {
      String temp = breadth.remove();
      List<String> tempnext = nextLevel(temp, dict, len);
      for (String s: tempnext){
        if(!parents.containsKey(s)){
          if(s.equals(target)){
            parents.put(s, temp);
            return solved(s, parents);
          }
          parents.put(s, temp);
          breadth.add(s);
        }
      }
    }
    return null;

  }

  public static List<String> creatDict(Scanner scnr) {
    List<String> out = new LinkedList<>();
    while (scnr.hasNextLine()) {
      String line = scnr.nextLine();
        out.add(line);
    }
    return out;
  }

  public static void outPrint(List<String> ans){
    if (ans == null){
      System.out.println("No solution was found");
      System.exit(3);
    }
    StringBuilder out = new StringBuilder();
    out.append("The path is: [ ");
    out.append(ans.get(ans.size() -1));
    for (int i = ans.size() -2; i >= 0; i--){
      out.append(" --> ");
      out.append(ans.get(i));
    }
    out.append(" ].");
    System.out.println(out.toString());

  }

  public static void main(String[] args) throws FileNotFoundException {
    String start = "";
    String goal = "";
    int len = 0;
    File dictFile = new File("WORD.LST.txt");
    if (args.length == 2){
      start = args[0];
      goal = args[1];
    }
    else if (args.length == 3){
      dictFile = new File(args[0]);
      start = args[1];
      goal = args[2];
    }
    else{
      System.out.println("Incorrect amount of arguments");
      System.out.println("Use: dictionaryFilePath startWord goalWord");
      System.out.println("Or: startWord goalWord");
      System.exit(1);
    }
    if (start.equals(goal)){
        System.out.println("These words are the same.");
        System.exit(6);
    }
    if (start.length() != goal.length()){
        System.out.println("Error: '"+start+"' and '"+goal+"' don't have same amount of characters.");
      System.exit(5); //my program works for words of any length.
    }
    Scanner scnr = new Scanner(dictFile);
    List<String> dictionary = creatDict(scnr);
      
    
    if (!dictionary.contains(start) || !dictionary.contains(goal)){
      System.out.println("Error: either '"+start+"' or '" +goal+"' are not in selected dictionary.");
      System.exit(2);
    }
    
    len = start.length();
    List<String> ans = solve(start, goal, dictionary, len);
    outPrint(ans);
  }
}
