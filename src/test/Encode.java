package test;

import java.util.*;

public class Encode
{
	
	public String encode()
	{
		String file = lZWHelper.readFile();
		HashMap dictionary = new HashMap();
		String previous = null;
		String current = file.substring(0,1);
		String combined = previous+current;
		int value = 256;
		String output = "";
		for(int i=1;i<file.length();i++)
		{
			if(combined.length()==1||dictionary.containsKey(combined))
			{
				
				previous = previous + current;
				
			}
			else
			{
				output = output+""+(char)(dictionary.get(previous));
				dictionary.put(combined,value);
				previous = current;
			}
			current = file.substring(i,i+1);
		}
	}
		
		
}