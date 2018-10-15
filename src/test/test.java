package test;

import java.util.Scanner;

public class test {

	
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(" \"John\" \"Cullen\" \"Jimbob\"  ");
		while(sc.hasNext())
		{
			System.out.println(sc.next());
		}
	}
}
