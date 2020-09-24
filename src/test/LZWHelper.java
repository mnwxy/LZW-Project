//package test;
import java.io.*;
public class LZWHelper
{
	public static void main(String[] args) throws IOException
	{
		System.out.println(readFile("yolo.txt"));
	}
	public static String readFile(String fileName) throws IOException
	{
		StringBuffer finalOutput = new StringBuffer();
		String nextLine;
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		if(reader.ready())
		{
			while((nextLine = reader.readLine()) != null)
			{
				if (nextLine.trim().length() == 0) {
					finalOutput.append("\n");
				}
				
				finalOutput.append(nextLine);
			}
		}
		reader.close();
		return finalOutput.toString();
	}
}