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

· 750 files in about 2 minutes

· 425 files containing duplicates

· 270,000 method comparisons (computing all matches, 0% and above) 

```
Specify a percentage for a close match: 70

Duplicate found in the following methods:
C:\Users\cstil\workspace\TestingDuplicate\src\Test2.java:  test2:  13
C:\Users\cstil\workspace\TestingDuplicate\src\Test2.java:  test1:  2
----------> 75.85%

Duplicate found in the following methods:
C:\Users\cstil\workspace\TestingDuplicate\src\Test2.java:  test3:  26
C:\Users\cstil\workspace\TestingDuplicate\src\Test2.java:  test1:  2
----------> 76.25%

Duplicate found in the following methods:
C:\Users\cstil\workspace\TestingDuplicate\src\Test2.java:  test4:  36
C:\Users\cstil\workspace\TestingDuplicate\src\Test2.java:  test1:  2
----------> 100.00%

Duplicate found in the following methods:
C:\Users\cstil\workspace\TestingDuplicate\src\Test2.java:  test4:  36
C:\Users\cstil\workspace\TestingDuplicate\src\Test2.java:  test2:  13
----------> 75.85%

Duplicate found in the following methods:
C:\Users\cstil\workspace\TestingDuplicate\src\Test2.java:  test4:  36
C:\Users\cstil\workspace\TestingDuplicate\src\Test2.java:  test3:  26
----------> 76.25%
Total Files: 1
Affected Files: 1
Affected Methods: 5

   ```

# License 
This is a repository intended to be used for a Software Engineering class at Rowan Univerity.


