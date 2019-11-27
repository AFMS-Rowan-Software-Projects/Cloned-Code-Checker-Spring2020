# Brumby Code Checker:  

The Brumby Code Checker is a redundancy code scanner to effectively find and flag duplicated code in C++, Java, and Ada files. The code checker uses the [SabbleCC](http://sablecc.org/) compiler and a multi dimensional similarity heuristic. It functions by running three independent similarity algorithm and computes a weighted average of those three scores. This heuristic consists of a Cosine Similarity, an ordered Naive (1 to 1) Similarity, and a Longest Common Subsequence algorithm. The application works on C++, Java, Ada, and header files.  To do this, it tokenizes the code, then discards comments, white space, and unknown tokens, while also renaming variables to an identifier. 

# How to Run: 
### In Eclipse: 
* Run the Application class in the Driver package as "Run as", then click run configurations 

* go to the arguments tab, then program arguments 

* type in a file path followed by (space) .java (or .cpp/.ada depending on file language)
	 ```
	C:\\Users\\username\workspace\\TestingDuplicate\\src .java
	```
  
* click apply

* click run

* you will be prompted "Specify a percentage for a close match: " which the user will enter a number (0-100) as the minimum percent match between 2 methods that will be accepted 

### In Eclipse Plugin Environment 
* Please refer to [Code-Checker-Eclipse-Plug-In](https://github.com/AFMS-Rowan-Software-Projects/Code-Checker-Eclipse-Plug-In) repository README for all information 


### In Linux: 
* clone the git repo

* load into eclipse to build the class files

*  run the program using java on the command line from the bin directory
	```
	stilwelld@penguin:~/test/RU-Software-Engineering/BrumbyCodeChecker/bin$ 	  
	java driver.Application /home/stilwelld/test/RU-Software-
	Engineering/BrumbyCodeChecker/src .java
   ```
   
# Results: 
Absolute file path names and line in the source file is saved and then grouped duplicates together, prints out a report:

· 2100 files in about 4 minutes

· 553 files containing duplicates

· 3500 lines of code
```
Duplicate found in the following files:  
/home/username/test/RU-Software-Engineering/BrumbyCodeChecker/src/sablecc/java_lexer/Lexer.java:getText:407  
/home/username/test/RU-Software-Engineering/BrumbyCodeChecker/src/sablecc/lexer/Lexer.java:getText:407  
  
Duplicate found in the following files:  
/home/username/test/RU-Software-Engineering/BrumbyCodeChecker/src/sablecc/java_lexer/Lexer.java:id:1003  
/home/username/test/RU-Software-Engineering/BrumbyCodeChecker/src/sablecc/lexer/Lexer.java:id:1186  
  
Duplicate found in the following files:  
/home/username/test/RU-Software-Engineering/BrumbyCodeChecker/src/sablecc/node/EOF.java:clone:23  
/home/username/test/RU-Software-Engineering/BrumbyCodeChecker/src/sablecc/node/TLBrace.java:clone:23  
  
Duplicate found in the following files:  
/home/username/test/RU-Software-Engineering/BrumbyCodeChecker/src/sablecc/node/TBlank.java:clone:23  
/home/username/test/RU-Software-Engineering/BrumbyCodeChecker/src/sablecc/node/TDocumentationComment.java:clone:23  
/home/username/test/RU-Software-Engineering/BrumbyCodeChecker/src/sablecc/node/TIdentifier.java:clone:23  
/home/username/test/RU-Software-Engineering/BrumbyCodeChecker/src/sablecc/node/TNumericConstant.java:clone:23  
/home/username/test/RU-Software-Engineering/BrumbyCodeChecker/src/sablecc/node/TTextLiteral.java:clone:23  
/home/username/test/RU-Software-Engineering/BrumbyCodeChecker/src/sablecc/node/TUnknown.java:clone:23  
Total Files: 44  
Affected Files: 7  
Lines of code affected: 36
   ```

# License 
This is a repository intended to be used for a Software Engineering class at Rowan Univerity.


