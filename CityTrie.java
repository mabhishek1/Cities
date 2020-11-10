import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Trie{
	char value;
	int end = 0;
	Trie[] children = new Trie[27];
	
	Trie(char value){
		this.value = value;
	}
}

public class CityTrie {

	public static String[] getParagraph() {
		String path = "/home/abhishek/Java/Streams/src/paragraph";
	    StringBuilder sb = null;
		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
	        sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        
	        System.out.println(sb.toString());
	    }catch (Exception e) {
			// TODO: handle exception
	    	System.out.println("Exception handeled");
		}
		return sb.toString().split("[-+,.?*/= ]");

	}
	
	
	
	
	/*
		Utility method to get the cities from file
	 */
	public static String[] getCities() {
		String line = "";  
		List<String> cities = new ArrayList<String>();
		try{    
			BufferedReader br = new BufferedReader(new FileReader("/home/abhishek/Java/Streams/src/worldcities.csv"));  
			while ((line = br.readLine()) != null){  
				String[] city = line.split(",");
//				System.out.println("Employee [First Name=" + city[0]);
				cities.add(city[0].substring(1, city[0].length()-1));
			}  
		}   
		catch (IOException e){  
			e.printStackTrace();  
		}  
		String[] array = cities.toArray(new String[0]);
//		String[] array = {"Kolkata","Mumbai","Delhi","Agra","Bangalore","Kolkataa","Agra"};
		return array;
	}
	
	
	
	
	
	private static void buildTrieUtil(Trie node, String string) {
		// TODO Auto-generated method stub
		string = string.toLowerCase();
		for(int i = 0;i<string.length();i++) {
			char currChar = string.charAt(i);
			int index = currChar - 97;
//			System.out.println(currChar);
			if(currChar>=97 && currChar<=122) {
				index = currChar - 97;
			}else {
				index = 26;	
			}
//			System.out.println(index+" "+currChar);
			if(node.children[index] == null) {
				node.children[index] = new Trie(currChar);
//				System.out.println("Here "+node.children[index].value);
			}
			node = node.children[index];
//			System.out.println("Here "+node.value);
		}
		node.end = 1;
	}
	
	
	
	private static Trie buildTrie(String[] cities) {
		// TODO Auto-generated method stub
		Trie node = new Trie('H');
		for(int i = 0;i<cities.length;i++) {
			buildTrieUtil(node,cities[i]);
		}
		return node;
	}
	
	
	
	private static boolean searchTrie(String string,Trie node) {
		// TODO Auto-generated method stub
		string = string.toLowerCase();
		for(int i = 0;i<string.length();i++) {
			char currChar = string.charAt(i);
			int index = 0;
			if(currChar>=97 && currChar<=122) {
				index = currChar - 97;
			}else {
				index = 26;	
			}
			
			if(node.children[index] == null) {
				return false;
//				System.out.println("Here "+node.children[index].value);
			}
			node = node.children[index];
		}
		if(node.end == 1) {
			return true;
		}
		return false;
	}
	
	
	
	public static String filter(String city) {
		if(city.charAt(city.length()-1)<65) {
			city = city.substring(0, city.length()-2);
		}
		return city;
	}
	
	
	
	public static boolean findString(String city,Trie root) {
		city = filter(city);
		
		if(searchTrie(city,root)) {
			return true;
		}
		return false;
	}
	
	
	
	private static List<Integer> findParagraphCities(String[] paragraph,Trie root) {
		// TODO Auto-generated method stub
		int i = 0;
		List<Integer> index = new ArrayList<Integer>();
		
		while(i<paragraph.length) {
			
			String currentString = paragraph[i];
			if(currentString.length()>=1 && (currentString.charAt(0)>=65 && currentString.charAt(0)<=90)) {
				int start = i;
//				System.out.println(currentString);
				if(findString(currentString,root)) {
					
					index.add(start);
					i++;
				}
				else {
					int max = 1;
					while(max<=3) {
//						System.out.println("Here "+currentString);
						currentString = currentString +" "+ paragraph[start+max];
						if(findString(currentString,root)) {
							index.add(start);
							i = start+max+1;
						}
						if(max == 3) {
							i++;
						}
						max++;
					}
					
				}
				
			}
			i++;
		}
		
		return index;
	}
	
	
	
	public static void main(String[] args) {
		String[] paragraph = getParagraph();
		String[] cities = getCities();
		Trie root = buildTrie(cities);
		List<Integer> findParagraphCities = findParagraphCities(paragraph,root);
		System.out.print("Cities At index -> ");
		for(Integer integer:findParagraphCities) {
			System.out.print(integer+" ");
		}
	}

	

	
}
