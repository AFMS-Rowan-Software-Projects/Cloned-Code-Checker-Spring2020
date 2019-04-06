package test;
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
      String newFile = CFilesReader.readFile(".\\src\\cpp_data\\Coins.txt");
      System.out.println(newFile);
      
      /*Insert appropriate call to Scanner*/
      ArrayList<Token> tokens = new ArrayList<Token>();
      tokens.add(new TIdentifier("Michelle is the greatest sponsor!"));
      double similarity = Parser.similarity(tokens, tokens);
      System.out.println("Expected 1.0: " + similarity);
      ArrayList<Token> tokens2 = new ArrayList<Token>();
      tokens2.add(new TKeyword("while"));
      similarity = Parser.similarity(tokens, tokens2);
      System.out.println("Expected not 1.0: " +similarity);
   }
   

}