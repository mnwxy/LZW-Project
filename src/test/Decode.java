package test;

import java.util.*;
import java.io.*;

public class Decode
{		
		public static void main(String[] args) throws IOException
		{
			System.out.println(decode("yolo.txt.lzw"));
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
			String fileContents = LZWHelper.readFile(filename);
			HashMap<Integer, String> dictionary = new HashMap<Integer, String>();
			String reconstructed = "";
			
			// Populate dictionary with mapping of ASCII value (as a 32-bit integer) 
			// to its String representation.
			// e.g.
			// 0x0A -> "\n"
			// 0x41 -> "A"
			for(int i = 0; i < 256; i++)
			{
				dictionary.put(i, (char)(i)+"");
			}						
			
			// Tokenized message from contents of file.
			String[] tokens = fileContents.split(" ");
			
			// Number of extra entries in dictionary mapping.
			int count = Integer.parseInt(tokens[0]);
			
			for (int i = 1; i < 1 + count; ++i)
			{
				dictionary.put(i + 255, tokens[i]);
			}
			
			for(int i = 1 + count; i < tokens.length; ++i)
			{
				reconstructed += dictionary.get(Integer.parseInt(tokens[i]));
			}
			
			return reconstructed;
		}
}