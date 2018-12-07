package decryption;

import java.awt.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	static Scanner x;
	static char[] alphabet = new char[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		String fileName = "encrypted_input.txt";
		openFile(fileName);
		final String text = readFile();
		closeFile();
		
		System.out.println(text);
		
		//Şifrelemiş metindeki en çok kullanılan kelimeleri bul.
		final char max = getMax(text);
		final char secondMax = getSecondMax(text);
		final char thirdMax = getThirdMax(text);
		
		int success = 0;
		int[] aVeB;
		int aValue;
		int bValue;
		String kelime;
		int counter = 0;
		int lengthTest;
		
		String[] passWords = text.split("\\W+");
		String decrypted = "";
		
		Pattern p = Pattern.compile("[a-zA-Z]+"); 
		String dic = "dictionary.dat";
		openFile(dic);
		dic = readFile();
		closeFile();
		
		ArrayList<String> dictionary = new ArrayList<String>();
		Matcher m3 = p.matcher(dic);
		while(m3.find()) {
			dictionary.add(m3.group());
		}
		
		String[] testWords;
		
		/* Solution for Second Max */
		if(success<30) {
			System.out.println("First solving attempt in process...");
			aVeB = solveForT(max, secondMax);
			aValue = aVeB[0];
			bValue = aVeB[1];
			
			for(int i=0; i<passWords.length; i++) {
				kelime = ceydaSifreCoz(aValue, bValue, passWords[i]);
				decrypted += kelime + " ";
			} 
			
			testWords = decrypted.split("\\W+");
			counter = 0;
			for(int i=0; i<testWords.length; i++) {
				if(dictionary.contains(testWords[i])){
	        		counter++;
	        	}
			}

			lengthTest = testWords.length;
			success = (counter * 100) / lengthTest;
			
			if(success>30) {
				usingFileWriter(decrypted);
				System.out.println("Solved by using Second Max Char on String");
				System.out.println("The A and B values are:");
				System.out.println("a: " + aValue);
				System.out.println("b: " + bValue);
				System.out.println("The percentage of word in dictionary is: " + success + "%");
				System.out.println("***");
				System.out.println("The decrypted text is: ");
				System.out.println(decrypted);
			}
		} 
		
		if(success<30) {
			/* Solution for Third Max */
			System.out.println("Second solving attempt in process...");
			aVeB = solveForA(max, thirdMax);
			aValue = aVeB[0];
			bValue = aVeB[1];
			passWords = text.split("\\W+");
			decrypted = "";
			
			for(int i=0; i<passWords.length; i++) {
				kelime = ceydaSifreCoz(aValue, bValue, passWords[i]);
				decrypted += kelime + " ";
			}

			testWords = decrypted.split("\\W+");
			counter = 0;
			for(int i=0; i<testWords.length; i++) {
				if(dictionary.contains(testWords[i])){
	        		counter++;
	        	}
			}

			lengthTest = testWords.length;
			success = (counter * 100) / lengthTest;
			if(success>30) {
				usingFileWriter(decrypted);
				System.out.println("Solved by using Third Max Char on String");
				System.out.println("The A and B values are:");
				System.out.println("a: " + aValue);
				System.out.println("b: " + bValue);
				System.out.println("The percentage of word in dictionary is: " + success + "%");
				System.out.println("***");
				System.out.println("The decrypted text is: ");
				System.out.println(decrypted);
			}
		} 
		
		if(success<20) {
			//brute force
			System.out.println("Third solving attempt in process...");
			aValue = 0;
			bValue = 0;
			ArrayList<Integer> aValueList = new ArrayList<Integer>();
			aValueList.add(1);
			aValueList.add(3);
			aValueList.add(5);
			aValueList.add(7);
			aValueList.add(9);
			aValueList.add(11);
			aValueList.add(15);
			aValueList.add(17);
			aValueList.add(19);
			aValueList.add(21);
			aValueList.add(23);
			aValueList.add(25); 
			
			search: {
				for(int b=0; b<26; b++) {
					bValue=b;
					for(Integer a : aValueList) {
						aValue=a;
						passWords = text.split("\\W+");
						decrypted = "";
						
						for(int i=0; i<passWords.length; i++) {
							kelime = ceydaSifreCoz(aValue, bValue, passWords[i]);
							decrypted += kelime + " ";
						}
						testWords = decrypted.split("\\W+");
						counter = 0;
						for(int i=0; i<testWords.length; i++) {
							if(dictionary.contains(testWords[i])){
				        		counter++;
				        	}
						}

						lengthTest = testWords.length;
						success = (counter * 100) / lengthTest;

						if(success>30) {
							usingFileWriter(decrypted);
							System.out.println("Solved by using Brute Force");
							System.out.println("The A and B values are:");
							System.out.println("a: " + aValue);
							System.out.println("b: " + bValue);
							System.out.println("The percentage of word in dictionary is: " + success + "%");
							System.out.println("***");
							System.out.println("The decrypted text is: ");
							System.out.println(decrypted);
							break search;
						}
					}
					bValue++;
				} 
			}
			
		}
				
		
	}
	
	public static void openFile(String fileName) {
		try {
			x = new Scanner(new File(fileName));
		} catch (Exception e) {
			System.out.println("couldn't read the file");
		}
	}
	
	public static String readFile() {
		String essay = "";
		while(x.hasNext()) {
			String a = x.next();
			essay += a + " ";
		}
		return essay;
	}
	
	public static void closeFile() {
		x.close();
	}

	public static char getMax(String word) {
	    if (word == null || word.isEmpty()) {
	        throw new IllegalArgumentException("input word must have non-empty value.");
	    }
	    char maxchar = ' ';
	    int maxcnt = 0;
	    word = word.replaceAll("\\s+","");
	    // if you are confident that your input will be only ascii, then this array can be size 128.
	    int[] charcnt = new int[Character.MAX_VALUE + 1];
	    for (int i = word.length() - 1; i >= 0; i--) {
	        char ch = word.charAt(i);
	        // increment this character's cnt and compare it to our max.
	        if (++charcnt[ch] >= maxcnt) {
	            maxcnt = charcnt[ch];
	            maxchar = ch;
	        }
	    }
	    return maxchar;
	}
	
	public static char getSecondMax(String word) {
		char max = getMax(word);
		word = word.replace(max, ' ');
	    if (word == null || word.isEmpty()) {
	        throw new IllegalArgumentException("input word must have non-empty value.");
	    }
	    char maxchar = ' ';
	    int maxcnt = 0;
	    word = word.replaceAll("\\s+","");
	    // if you are confident that your input will be only ascii, then this array can be size 128.
	    int[] charcnt = new int[Character.MAX_VALUE + 1];
	    for (int i = word.length() - 1; i >= 0; i--) {
	        char ch = word.charAt(i);
	        // increment this character's cnt and compare it to our max.
	        if (++charcnt[ch] >= maxcnt) {
	            maxcnt = charcnt[ch];
	            maxchar = ch;
	        }
	    }
	    return maxchar;
	}
	
	public static char getThirdMax(String word) {

		char max = getMax(word);
		char ikinciMax = getSecondMax(word);
		word = word.replace(max, ' ');
		word = word.replace(ikinciMax, ' ');
	    if (word == null || word.isEmpty()) {
	        throw new IllegalArgumentException("input word must have non-empty value.");
	    }
	    char maxchar = ' ';
	    int maxcnt = 0;
	    word = word.replaceAll("\\s+","");
	    // if you are confident that your input will be only ascii, then this array can be size 128.
	    int[] charcnt = new int[Character.MAX_VALUE + 1];
	    for (int i = word.length() - 1; i >= 0; i--) {
	        char ch = word.charAt(i);
	        // increment this character's cnt and compare it to our max.
	        if (++charcnt[ch] >= maxcnt) {
	            maxcnt = charcnt[ch];
	            maxchar = ch;
	        }
	    }
	    return maxchar;
	}

	public static int findIndex(char p,char[] array) {
		int length = array.length;
		int indexP=0;
		for(int i=0; i<length; i++) {
			if(array[i]==p) {
				indexP=i;
				break;
			}
		}
		return indexP;
	}
	
	public static int[] solveForT(char max, char secondMax) {
		//indexMax = 4a + b
		//secondIndexMax = 19a + b
		int indexFark = findIndex(secondMax, alphabet) - findIndex(max, alphabet);
		//indexFark = 15a
		indexFark = (indexFark+26) % 26;
		//a = indexFark(inverse 15)
		int a = indexFark*7;
		a=a%26;
		//b=indexMax - 4a
		int b = findIndex(max, alphabet) - (4*a);
		while(b<0) {
			b=b+26;
		}
		//b=b%26;
		int[] aAndB= {a,b};
		return aAndB;
	}
	
	public static int[] solveForA(char max, char thirdMax) {
		//indexMax = 4a + b
		//indexThirdMax = 19a + b
		int indexFark = findIndex(thirdMax, alphabet) - findIndex(max, alphabet);
		//indexFark = 15a
		indexFark = (indexFark+26) % 26;
		//a = indexFark(inverse 15)
		int a = indexFark*7;
		a=a%26;
		//b=indexMax - 4a
		int b = findIndex(max, alphabet) - (4*a);
		while(b<0) {
			b=b+26;
		}
		//b=b%26;
		int[] aAndB= {a,b};
		return aAndB;
	}

	public static String ceydaSifreCoz(int a, int b, String string) {
		String newString = "";
		int l = string.length();
		char ch;
		for(int i=0; i<l; i++) {
			ch = string.charAt(i);
			if(Character.isUpperCase(ch)) {
				ch = Character.toLowerCase(ch);
				int indexCh = findIndex(ch, alphabet);
				// indexCh =(a.p + b) mod 26
				//fark = indexCh - b
				int fark = indexCh - b;
				fark = (fark + 26) % 26;
				//(indexCh - b) * a'nın tersi = p
				int aModular = modInverse(a, 26);
				int p = fark * aModular;
				p = p % 26;
				ch = alphabet[p];
				ch = Character.toUpperCase(ch);
				newString += ch;
			} else {
				int indexCh = findIndex(ch, alphabet);
				int fark = indexCh - b;
				fark = (fark + 26) % 26;
				int aModular = modInverse(a, 26);
				int p = fark * aModular;
				p = p % 26;
				ch = alphabet[p];
				newString += ch;
			}
		}
		
		return newString;
	}
	
	public static String ceydaKelime(int randomAgiven, int randomBgiven, String string) {
		String newString = "";
		int l = string.length();
		char ch;
		char[] alphabet = new char[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		//int[] arrayOfA = new int[] {1,3,5,7,9,11,15,17,19,21,23,25};
		//create a random 'b' value between 0(inclusive) and 25(exclusive)
		//int randomB = new Random().nextInt(26);
		int randomB = randomBgiven;
		int randomA = randomAgiven;
		//create a random value a GCD(a,26) = 1
		//a times p plus b
		for(int i=0; i<l; i++){
			ch = string.charAt(i);
			if(Character.isUpperCase(ch)) {
				ch = Character.toLowerCase(ch);
				int indexP = findIndex(ch, alphabet);
				int y = ((randomA*indexP)+randomB)%26; 
				ch = alphabet[y];
				ch = Character.toUpperCase(ch);
				newString += ch;
			}else {
				int indexP = findIndex(ch, alphabet);
				int y = ((randomA*indexP)+randomB)%26; 
				ch = alphabet[y];
				newString += ch;
			}
		}

		return newString;
	}
	
	public static int modInverse(int a, int m) 
	    { 
	        a = a % m; 
	        for (int x = 1; x < m; x++) 
	           if ((a * x) % m == 1) 
	              return x; 
	        return 1; 
	    }
	 
	public static void usingFileWriter(String fileContent) throws IOException
		{
		    FileWriter fileWriter = new FileWriter("decrypted.txt");
		    fileWriter.write(fileContent);
		    fileWriter.close();
		}
	
}
