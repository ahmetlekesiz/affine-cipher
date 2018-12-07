package encryption;

import java.util.Random;

public class EncryptionChar {

	public static int randomAGenerator() {
		int[] arrayOfA = new int[] {1,3,5,7,9,11,15,17,19,21,23,25};
		int randomA = getRandomFromArray(arrayOfA);
		return randomA; 
	}
	
	public static int randomBGenerator() {
		int randomB = new Random().nextInt(26);
		return randomB; 
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
	
	public static int getRandomFromArray(int[] a) {
		int rndm = new Random().nextInt(a.length);
		return a[rndm];
	}

}
