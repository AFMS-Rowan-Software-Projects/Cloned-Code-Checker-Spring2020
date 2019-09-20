package parser;
import sablecc.node.*;
import java.util.ArrayList;
public class Parser{
   
	// TODO For % similarity. Not implemented yet
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
   
   /**
    * Looks in the Token ArrayList. Looks for features that signifies the start of a method. I.E public, private
    * Then a return type, name, and parameters (if any)
    * (Finds the start and end of a method)
    * @param ArrayList
    * @param start
    * @return indices
    */
   public static int[] findMethod(ArrayList<Token> file, int start){
	  //Returns start and end index (inclusively) of a method within a file
      int[] indices = new int[2];
      int lbracecounter;
      //Smallest possible method is 6 tokens long
      for(indices[0] = start; indices[0] < (file.size() - 6); indices[0]++){
         //REMINDER: If grammar is expanded to include data types, REWRITE this section accordingly
         indices[1] = indices[0] + 3;
         //Match starting pattern of method- Identifier Identifier LParen
         if(((file.get(indices[0]) instanceof TDataType) || 
        		 (file.get(indices[0]) instanceof TIdentifier)) && 
        		 (file.get(indices[0]+1) instanceof TIdentifier) && 
        		 (file.get(indices[0]+2) instanceof TLParen)) {
        	 //Capture everything between ( and ) of a method
        	 while(!(file.get(indices[1]) instanceof TRParen) && 
        			 (indices[1] < (file.size() - 3))) {
        		 indices[1]++;
          }
          indices[1]++;
          // EOF reached (End of File)
          if(indices[1] > (file.size() - 2)){
        	// Record that method was not found
            indices[0] = file.size();
            break;
          }
          if(!(file.get(indices[1]) instanceof TLBrace)){ //{ should be immediately after )
            continue;
          }
          else{
        	//Match up braces with one another and only terminate when finding the method's }
            lbracecounter = 1;
            for(indices[1]++; (lbracecounter != 0) && (indices[1] < file.size()); indices[1]++){
               if(file.get(indices[1]) instanceof TLBrace){
                  lbracecounter++;
               }else if(file.get(indices[1]) instanceof TRBrace){
                  lbracecounter--;
               }
            }
            if(lbracecounter == 0){
            	indices[1]--;
               break;//Successfully found a method 
            }
          }
         }
      } // end of for(indices[0]...)
      
      //Sentinel value indicates no method was found between starting position and EOF
      if(indices[0] >= (file.size() - 6)){
         indices[0] = -1;
      }
      return indices; // returns the line number where the method starts
   }
   
   
   /**
    * Cleans up tokens/gets rid of unused tokens
    */
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
   
       for(int i =s; i<=e; i++) {
           bleh.add(rec.get(i));
       }

      return bleh;
   }

}