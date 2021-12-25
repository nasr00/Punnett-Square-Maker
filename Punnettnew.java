package PunnettSquares;
import java.util.*;
public class Punnettnew
{
	private String gene1;
	private String gene2;
	private String[][] square;
	private ArrayList<String> probs = new ArrayList<String>();
	public Punnettnew(String a, String b)
	{
		gene1 = a; 
		gene2 = b;
		square = new String[(int)Math.pow(2,(gene1.length()/2))+2][(int)Math.pow(2,(gene2.length()/2))+2];
		print();
	}
	public ArrayList<String> getProbs() {
		return this.probs;
	}
	public String[][] separator(String x)
	{
		
		String[][] split = new String[x.length()/2][2];
		int count = 0;
		for(int i = 0; i < split.length; i++)
		{
			for(int j = 0; j < split[i].length; j++)
			{
				split[i][j]= x.substring(count, count+1);
				count++;
			}
		}
		return split;
	}
	public static String toBinary(int i, int places)
	{
		String bin = Integer.toBinaryString(i);
		String form = "%"+places+"s";
		String binary = String.format(form, bin).replace(' ', '0');
		return binary;
	}
	public String[] combos(String x)
	{
		String[][] y = separator(x);
		String[] cnations = new String[(int)Math.pow(2,  (x.length()/2))];
		for(int i = 0; i < Math.pow(2,(x.length()/2)); i++)
		{
			String binbin = toBinary(i, x.length()/2);
			String temp="";
			for(int j = 0; j < binbin.length(); j++)
			{
				temp+=y[j][Integer.valueOf(binbin.substring(j, j+1))];
			}
			cnations[i]=temp;
		}
		return cnations;
	}
	public void print()
	{

		
		String[] gene1combos = combos(gene1);
		String[] gene2combos = combos(gene2);
		square[0][0]="";
		square[0][1]="";
		square[1][0]="";
		square[1][1]="+";
		for(int i = 2; i < square.length; i++)
		{
			square[0][i]=gene1combos[i-2];
			square[i][0]=gene2combos[i-2];
			square[1][i]="-";
			square[i][1]="|";
		}
		for(int i = 2; i < square.length; i++)
		{
			for(int j = 2; j < square[i].length; j++)
			{
				String q = "";
				for(int k = 0; k < gene1.length()/2; k++)
				{
					q+=square[0][j].substring(k,k+1);
					q+=square[i][0].substring(k,k+1);
				}
				square[i][j]=alphabetical(q);
			}
		}
		probabilities();
	}
	public String[][] getSquare() {
		return this.square;
	}
	public String alphabetical(String z)
	{
		
		String[] tempray = new String[z.length()];
		for(int i = 0; i < tempray.length; i++)
			tempray[i]=z.substring(i, i+1);
		z="";
		for(int i = 1; i < tempray.length; i+=2)
		{
			if(tempray[i].compareTo(tempray[i-1])<0)
			{
				z+=tempray[i];
				z+=tempray[i-1];
			}
			else
			{
				z+=tempray[i-1];
				z+=tempray[i];
			}
					
		}
		return z;
	}
	public void probabilities()
	{
		ArrayList<String> uniques = new ArrayList<String>();
		for(int i = 2; i < square.length; i++)
		{
			for(int j = 2; j < square[i].length; j++)
			{
				uniques.add(square[i][j]);
			}
		}
		for(int i = 0; i < uniques.size(); i++)
		{
			for(int j = i+1; j < uniques.size(); j++)
			{
			if(uniques.get(j).contentEquals(uniques.get(i)))
			{
				uniques.remove(j);
				j--;
			}
			}
		}
		ArrayList<String> outputs = new ArrayList<String>();
		outputs.add(uniques.size() + " unique combination(s) found.\n");
		
		ArrayList<Stats> statistics = new ArrayList<Stats>();
		
		for(int i = 0; i < uniques.size(); i++)
		{
			int counter = 0;
			for(int j = 2; j < square.length; j++)
			{
				for(int k = 2; k < square[j].length; k++)
				{
					if(square[j][k].contentEquals(uniques.get(i)))
						counter++;
				}
			}
			statistics.add(new Stats(uniques.get(i), Stats.simplify(""+counter+"/"+(int)Math.pow(square.length-2,2)), 100*(double)counter/(Math.pow(square.length-2,2))));
		}
		Stats.sortByPercent(statistics);
		
		for(int q = 0; q < statistics.size(); q++)
			outputs.add(statistics.get(q).printout());
		this.probs = outputs;
	}

}
