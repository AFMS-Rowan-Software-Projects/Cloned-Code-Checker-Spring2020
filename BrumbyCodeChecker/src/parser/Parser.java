package parser;
import sablecc.node.*;
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
   
   public static int[] findMethod(ArrayList<Token> file, int start){
      int[] indices = new int[2];//Returns start and end index (inclusively) of a method within a file
      int lbracecounter;
      for(indices[0] = start; indices[0] < (file.size() - 6); indices[0]++){//Smallest possible method is 6 tokens long
         //REMINDER: If grammar is expanded to include data types, REWRITE this section accordingly
         indices[1] = start + 3;
         //Match starting pattern of method- Identifier Identifier LParen
         if(((file.get(indices[0]) instanceof TDataType) || (file.get(indices[0]) instanceof TIdentifier)) && (file.get(indices[0]+1) instanceof TIdentifier) && (file.get(indices[0]+2) instanceof TLParen)){
          //Capture everything between ( and ) of a method
          while(!(file.get(indices[1]) instanceof TRParen) && (indices[1] < (file.size() - 3))){
            indices[1]++;
          }
          if(indices[1] > (file.size() - 2)){//EOF reached
            indices[0] = file.size();//Record that method was not found
            break;
          }
          if(!(file.get(indices[1]) instanceof TLBrace)){//{ should be immediately after )
            continue;
          }else{
            lbracecounter = 1;//Match up braces with one another and only terminate when finding the method's }
            for(indices[1]++; (lbracecounter != 0) && (indices[1] < file.size()); indices[1]++){
               if(file.get(indices[1]) instanceof TLBrace){
                  lbracecounter++;
               }else if(file.get(indices[1]) instanceof TRBrace){
                  lbracecounter--;
               }
            }
            if(lbracecounter == 0){
               break;//Successfully found a method 
            }
          }
         }
      }
      if(indices[0] > (file.size() - 6)){//Sentinel value indicates no method was found between starting position and EOF
         indices[0] = -1;
      }
      return indices;
   }
   
   
   public static ArrayList<Token> sanitize(ArrayList<Token> t_large){
   //NOTE: this method may become unnecessary if Ignored Tokens aren't produced by the Scanner
	   ArrayList<Token> tlist = (ArrayList<Token>) t_large.clone();
      for(int i = 0; i < tlist.size(); i++){
         if((tlist.get(i) instanceof InvalidToken) || (tlist.get(i) instanceof TBlank)
        		 || (tlist.get(i) instanceof TDocumentationComment) || (tlist.get(i) instanceof TEndOfLineComment)
        		 || (tlist.get(i) instanceof TTraditionalComment) || (tlist.get(i) instanceof TUnknown)){
            tlist.remove(i);
            i--;//Reposition the iterator correctly
         }
      }
      return tlist;
   }
   
   //Takes in an array of tokens with a start and end point and returns a smaller array between those two points
   public static ArrayList<Token> subarray (ArrayList<Token> rec, int start, int end){
       int s = start;
       int e = end;
       ArrayList<Token> bleh = new ArrayList<Token>();
   
       for(int i =s; i<=e; i++)
       {
           bleh.add(rec.get(i));
       }

      return bleh;
   }

}