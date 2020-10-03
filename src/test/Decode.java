/**
* Praise be to Ms. Kaufman and Computer Science A teachers.
* They spoke the truth when they spoke of handwritten code and BlueJ.
*/

package test;

//imports
import java.util.*;
import java.io.*;

public class Decode
{		
		public static void main(String[] args) throws IOException
		{
			//print out the decoded message
			System.out.println(Decode("lzw-text0_encoded.txt"));
		}
		
		/**
		 * Decompresses a file encoded with Tao-Zhang implementation 
		 * of Lempel-Ziv-Welch encoding.
		 * 
		 * @param filename Path to file with compressed message.
		 * @return Decompressed message
		 * @throws IOException
		 */
		public static String Decode(String filename) throws IOException
		{
			//a String to hold the reconstructed message
			String reconstructed = "";
			try {
				//a reader to read the file
				FileReader fileReader = new FileReader(filename);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				//a dictionary to store all of the codes and their values
				HashMap<Integer, String> dictionary = new HashMap<Integer, String>();
				//add all of the single characters and their ascii values to the dictionary
				for(int i = 0; i < 256; i++)
				{
					dictionary.put(i, (char)(i)+"");
				}
				//an int to hold the value of each character that is read in
				int charValue;
				//represents the current character being read in
				String thisCharacter = "";
				//represents the String that is currently being built
				String currentString = "";
				//tells the computer whether or not we are reading an actually valuable String or if we're on a delimiter
				boolean currentlyReading = false;
				//represents the counted length of the String being constructed so far
				int countedLength = 0;
				//represents the target length of the String being constructed
				int stringLength = 0;
				//represents the index of each dictionary entry
				int index = 256;
				//tells the computer whether or not we have started reading actual codes, or if we are still reading dictionary entries, separated by an x
				boolean foundX = false;
				//start reading the file
				while ((charValue = bufferedReader.read()) != -1) {
					//if we find the x, then we know to start reading in actual codes, so we restart the while loop to ignore the x
					if (String.valueOf((char)charValue).equals("x")){
						foundX = true;
						continue;
					}
					//if the x hasn't been found yet
					if (foundX == false){
						//set thisCharacter to the current character
						thisCharacter = String.valueOf((char)charValue);
						//if we are reading a significant part
						if (currentlyReading){
							//add thisCharacter to the currentString and increment the countedLength
							currentString += thisCharacter;
							countedLength ++;
							//once we reach the target length, we put the entry into the dictionary, increment the index, and reset currentString, countedLength, and currentlyReading
							if (countedLength == stringLength) {
								dictionary.put(index, currentString);
								index++;
								currentString = "";
								countedLength = 0;
								currentlyReading = false;
							}
						}
						//if we are not reading a significant part
						else {
							//if we're on a delimiter, then we record the target length of the next combination and reset currentString, and set currentlyReading to true
							if (thisCharacter.equals(":")){
								stringLength = Integer.parseInt(currentString);
								currentlyReading = true;
								currentString = "";	
							}
							//otherwise, just append thisCharacter to currentString
							else {
								currentString += thisCharacter;
							}
						}
					}
					//if the x has been found already
					else {
						//set thisCharacter to the character currently being read
						thisCharacter = String.valueOf((char)charValue);
						//if we hit the delimiter, then we can translate the current code
						if (thisCharacter.equals(" ")) {
							reconstructed += dictionary.get(Integer.parseInt(currentString));
							//reset currentString
							currentString = "";
						}
						//if we haven't found the delimiter yet, then keep reading until we do
						else {
							currentString += thisCharacter;
						}
								
					}
				}
					
					
					
			}
			catch(Exception e){
				System.out.println("The following error occured: " + e);
			}
			//return the reconstructed String
			return reconstructed;
		}
}