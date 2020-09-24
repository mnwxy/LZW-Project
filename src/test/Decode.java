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
			
			// Dictionary for First 256 Characters
			for(int i = 0; i < 256; i++)
			{
				dictionary.put(i, (char)(i)+"");
			}
			
			int a;
			
			// Current Character
			String thisCharacter = "";
			
			// Current String
			String currentString = "";
			
			// If Currently Reading
			boolean currentlyReading = false;
			
			// Int for How Many We Chars We Have Counted
			int countedLength = 0;
			
			// Int for Current Length of String
			int stringLength = 0;
			
			// Initial index After Dictionary of 0 to 255 has been added
			int index = 256;
			
			// Boolean for whether or not the Letter X has been read in yet, which indicates the start of the encoded text
			boolean foundX = false;;
			
			// While The End of the file has not been reached
			while ((a = br.read()) != -1)
			{
				// If the letter X has been found yet
				if (String.valueOf((char)a).equals("x"))
				{
					foundX = true;
				}
				if (foundX == false)
				{
					// ThisCharacter Becomes A (the current character)
					thisCharacter = String.valueOf((char)a);
					//If the reader is currently reading a value, not a delimiter
					if (currentlyReading)
					{
						// Add the Char Version of the Letter from the Buffered Reader to the current String that is being constructed
						currentString += thisCharacter;
						
						// counter for the length of the String constructed so far Increases by One
						countedLength ++;
						
						//if the current String has hit the specified length of the dictionary entry
						if (countedLength == stringLength)
						{
							// Add to Dictionary
							dictionary.put(index, currentString);
							// Add One to Index
							index++;
							// Reset currentString
							currentString = "";
							// Reset CountedLength
							countedLength = 0;
							// Reset currentlyReading
							currentlyReading = false;
						}
					}
					else
					{
						// If Statement for Delimiter ":", which represents the end of the entry's length and the start of the dictionary entry itself
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
				else
				{
					// If a != x
					if (String.valueOf((char)a).equals("x") == false)
					{
						// thisCharacter Becomes a
						thisCharacter = String.valueOf((char)a);
						
						// If thisCharacter == Space
						if (thisCharacter.equals(" ") && currentString.length() > 0)
						{
							//System.out.println(currentString);
							
							// add the decoded combination to the decoded message
							reconstructed += dictionary.get(Integer.parseInt(currentString));
							
							//reset the currentString after each individual code has been decoded
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
