package fileIO;
import java.io.*;
import java.util.logging.Logger;

/**
 * Reads in everything in a file
 */
public class CFilesReader {

	private static final Logger LOGGER = Logger.getLogger(CFilesReader.class.getName());
	
	private static String newFile;
	private static String refFile;
	
	public static void readFiles(String[] args) {
		newFile = readFile(args[0]);
		refFile = readFile(args[1]);
	}
	
	public static String readFile(String file)
	{
		InputStream inStream = null;
		File directory = new File("./");
		//System.out.println(directory.getAbsolutePath());
		try {
			inStream = new FileInputStream(file);
		} 
		catch (FileNotFoundException e) {
			LOGGER.info("File not found. FileNotFoundException caught.");
		} 
		
		BufferedReader buf = new BufferedReader(new InputStreamReader(inStream)); 
		
		String line = null;;
		try {
			line = buf.readLine();
		} 
		catch (IOException e) {
			LOGGER.info("BufferedReader could not read file. IOException caught.");
		} 
		
		StringBuilder sb = new StringBuilder(); 
		
		while(line != null){
			sb.append(line).append("\n"); 
			try {
				line = buf.readLine();
			}
			catch (IOException e) {
				LOGGER.info("BufferedReader could not read file. IOException caught.");
			} 
		} 
		
		try {
			buf.close();
			inStream.close();
		} catch (IOException e) {
			LOGGER.info("Could not close stream. IOException caught.");
		}
		String fileAsString = sb.toString(); 
		return fileAsString;
	}

	public static String getNewFile() {
		return newFile;
	}

	public static String getRefFile() {
		return refFile;
	}

	
}
