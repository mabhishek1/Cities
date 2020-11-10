import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Node{
	String cityName;
	Node next = null;
	
	Node(String cityName){
		this.cityName = cityName;
	}
}


public class City {
	
	
	private static Node[] hash;
	
	
	/*
	 Inserts the node in proper hash slot
	 This method handles collison by using chaining
	 */
	 
	public static  void insertIntoHash(int hashValue,String city,Node[] hash) {	
		if(hash[hashValue] == null) {
			hash[hashValue] = new Node(city);
		}else {
			Node current = hash[hashValue];
			while(current.next != null) {
				current = current.next;
			}
			current.next = new Node(city);
		}
	}
	
	
	/*
	  	input:all cities
	 	Generates the hash table and calls 
	 	generateHashUtil and insertIntoHash
	 */
	public static void generateHash(String[] cities) {
		hash = new Node[5000];
		for(int i=0;i<cities.length;i++) {
			int generateHashUtil = generateHashUtil(cities[i]);
			insertIntoHash(generateHashUtil,cities[i],hash);
		}
		
	}
	
	/*
 		input:city
 		calculates the hash by using ascii values
	 */
	
	public static int generateHashUtil(String city) {
		int value = 0;
		for(int i = 0;i<city.length();i++) {
			value = value + city.charAt(i);
		}
//		System.out.println(city+" "+value+" "+(value/100));
		return value/10;
		
	}
	
	
	/*
		Utility method to get the paragraph from file
	 */
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
//		String[] array = {"Kolkata","Mumbai","Delhi","Agra","Bangalore"};
		return array;
	}
	
	
	/*
	 	Remove unwanted chars from a string 
	 */
	public static String filter(String city) {
		if(city.charAt(city.length()-1)<65) {
			city = city.substring(0, city.length()-2);
		}
		return city;
	}
	
	/*
 		Find String from hashmap 
	 */
	public static boolean findString(String city) {
		String filter = filter(city);
		int value = generateHashUtil(filter);
		if(findCity(value,filter)) {
			return true;
		}
		return false;
	}
	
	private static boolean findCity(int value,String city) {
		if(hash[value] == null) {
			return false;
		}
		Node node = hash[value];
		
		while(node != null) {
			String str = node.cityName;
			if(str.equals(city)) {
				return true;
			}
			node = node.next;
		}
		return false;
	}


	private static List<Integer> findParagraphCities(String[] paragraph) {
		// TODO Auto-generated method stub
		int i = 0;
		List<Integer> index = new ArrayList<Integer>();
		
//		System.out.println(index.size());
		
		while(i<paragraph.length) {
			
			String currentString = paragraph[i];
			if(currentString.length()>=1 && (currentString.charAt(0)>=65 && currentString.charAt(0)<=90)) {
				int start = i;
//				System.out.println(currentString);
				if(findString(currentString)) {
					
					index.add(start);
					i++;
				}
				else {
					int max = 1;
					while(max<=3) {
//						System.out.println("Here "+currentString);
						currentString = currentString +" "+ paragraph[start+max];
						if(findString(currentString)) {
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
	
	public static void main(String[] args) throws IOException {
		String[] paragraph = getParagraph();	
//		System.out.println(paragraph.length);
		String[] cities = (String[]) getCities();
//		for(String c:cities) {
//			System.out.println(c);
//		}
//		System.out.println(cities.length);
		generateHash(cities);
//		for(int i = 0;i<hash.length;i++) {
//			if(hash[i] == null) {
//				System.out.println(i+"->");
//			}else {
//				Node node = hash[i];
//				int count = 0;
//				while(node != null) {
//					count++;
//					node = node.next;
//				}
//				System.out.println(i+"->"+count);
//			}
//		}
		List<Integer> findParagraphCities = findParagraphCities(paragraph);
//		System.out.println("Size "+findParagraphCities.size());
		System.out.print("Cities At index -> ");
		for(Integer integer:findParagraphCities) {
			System.out.print(integer+" ");
		}
	}


	
}
