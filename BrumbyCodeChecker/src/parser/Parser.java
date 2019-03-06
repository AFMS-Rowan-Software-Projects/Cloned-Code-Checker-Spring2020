package parser;
import node.*;
import java.util.ArrayList;
public class Parser{
   
   public static double similarity(ArrayList<Token> list1, ArrayList<Token> list2){
      //Assume sanitize has been called on the input already, getting rid of Whitespace and Comments
      if(list1.size() != list2.size()){
         return 0.1;
      }
      double len = list1.size();
      double matches = 0;
      for(int i = 0; i < len; i++){
         if(list1.get(i).getText().equals(list2.get(i).getText())){
            matches++;
         }
      }
      return matches / len;
   }
   
   
   /*public static ArrayList<Token> sanitize(ArrayList<Token> tlist){
   //NOTE: this method may become unnecessary if Ignored Tokens aren't produced by the Scanner
      for(int i = 0; i < tlist.length(); i++){
         if((tlist.get(i) instanceof TWhitespace) || (tlist.get(i) instanceof TComment)){
            tlist.remove(i);
            i--;//Reposition the iterator correctly
         }
      }
   }*/

}