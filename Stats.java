package PunnettSquares;
import java.util.*;
public class Stats 
{
	private String name;
	private String fraction;
	private double percent;
	public Stats(String n, String f, double p)
	{
		name = n;
		fraction = f;
		percent = p;
	}
	public String printout()
	{
		return "Probability of " + name + ": " + fraction + " or "+ String.format("%.3f", percent) + "%";
	}
	public static void sortByPercent(ArrayList<Stats> list)
	{
		for(int i = 0; i < list.size(); i++)
		{
			for(int j = list.size()-1; j > i; j--)
			{
				if(list.get(j).getPercent()>list.get(i).getPercent())
				{
					Stats temp = list.get(i);
					list.set(i, list.get(j));
					list.set(j, temp);
				}
			}	
		}	
	}
	public static String simplify(String fraction)
	{
	  int a = Integer.valueOf(fraction.substring(0, fraction.indexOf("/")));
	  int b = Integer.valueOf(fraction.substring(fraction.indexOf("/")+1));
	  return ""+(a/gcd(a,b))+"/"+(b/gcd(a,b));
	}
	public static int gcd(int a, int b)
	{
	   if (b==0) return a;
	   return gcd(b,a%b);
	}
	public double getPercent()
	{
		return percent;
	}

}
