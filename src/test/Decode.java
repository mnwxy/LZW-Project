package test;

import java.util.*;
import java.io.*;

public class Decode
{		
		public static void main(String[] args) throws IOException
		{
			System.out.println(decode("yolo_encoded.txt"));
		}
		
		/**
		 * Decompresses a file encoded with Tao-Zhang implementation 
		 * of Lempel-Ziv-Welch encoding.
		 * 
		 * @param filename Path to file with compressed message.
		 * @return Decompressed message
		 * @throws IOException
		 */
		public static String decode(String filename) throws IOException
		{
			// File Reader to Read Files
			FileReader fr = new FileReader(filename);
			
			// Buffered Reader to Read Characters
			BufferedReader br = new BufferedReader(fr);
			
			// Dictionary with Letters
			HashMap<Integer, String> dictionary = new HashMap<Integer, String>();
			
			// String for Reconstructed Char
			String reconstructed = "";
			
			// Dictionary for 
			for(int i = 0; i < 256; i++)
			{
				dictionary.put(i, (char)(i)+"");
			}
			
			int a;
			String thisCharacter = "";
			String currentString = "";
			boolean currentlyReading = false;
			int countedLength = 0;
			int stringLength = 0;
			int index = 256;
			boolean foundX = false;;
			while ((a = br.read()) != -1) {
				if (String.valueOf((char)a).equals("x")){
					foundX = true;
				}
				if (foundX == false){
					thisCharacter = String.valueOf((char)a);
					if (currentlyReading){
						currentString += thisCharacter;
						countedLength ++;
						if (countedLength == stringLength) {
							dictionary.put(index, currentString);
							index++;
							currentString = "";
							countedLength = 0;
							currentlyReading = false;
						}
					}
					else {
						if (thisCharacter.equals(":")){
							stringLength = Integer.parseInt(currentString);
							currentlyReading = true;
							currentString = "";	
						}
						else {
							currentString += thisCharacter;
						}
					}
				}
				else {
					if (String.valueOf((char)a).equals("x") == false) {
						thisCharacter = String.valueOf((char)a);
						if (thisCharacter.equals(" ") && currentString.length() > 0) {
							//System.out.println(currentString);
							reconstructed += dictionary.get(Integer.parseInt(currentString));
							currentString = "";
						}
						else {
							if (thisCharacter.equals(" ") == false) {
								currentString += thisCharacter;
							}
							
						}
					}
				}
				
				
				
			}
			reconstructed += dictionary.get(Integer.parseInt(currentString));
//			for (Integer key : dictionary.keySet()){
//				System.out.println(key + ": " + dictionary.get(key));
//			}
			//String fileContents = LZWHelper.readFile(filename);
			
			
			
			
			// Populate dictionary with mapping of ASCII value (as a 32-bit integer) 
			// to its String representation.
			// e.g.
			// 0x0A -> "\n"
			// 0x41 -> "A"
									
			
			// Tokenized message from contents of file.
			//String[] tokens = fileContents.split(" ");
			
			// Number of extra entries in dictionary mapping.
			//int count = Integer.parseInt(tokens[0]);
			
//			for (int i = 1; i < 1 + count; ++i)
//			{
//				dictionary.put(i + 255, tokens[i]);
//			}
//			
//			for(int i = 1 + count; i < tokens.length; ++i)
//			{
//				reconstructed += dictionary.get(Integer.parseInt(tokens[i]));
//			}
			
			return reconstructed;
		}
}