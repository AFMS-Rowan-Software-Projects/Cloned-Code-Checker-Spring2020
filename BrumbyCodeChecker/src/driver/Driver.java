package driver;
import fileIO.CFilesReader;
import java.io.File;
import java.util.ArrayList;
import analysis.*;
import lexer.*;
import node.*;
import parser.*;

public class Driver{

   public static void main(String[] args){
      File directory = new File("./");
      System.out.println(directory.getAbsolutePath());
      //Display working directory for debugging purposes
      
      //Test FileIO
      String newFile = CFilesReader.readFile(".\\cpp_data\\Coins.txt");
      System.out.println(newFile);
      
      /*Insert appropriate call to Scanner*/
      ArrayList<Token> tokens = new ArrayList<Token>();
      tokens.add(new TIdentifier("Ham Sandwiches"));
      double similarity = Parser.similarity(tokens, tokens);
      System.out.print(similarity);
   }
   

}