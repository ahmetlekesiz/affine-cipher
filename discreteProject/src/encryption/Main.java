package encryption;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	static Scanner x;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
			
		String input = "input.txt";
		openFile(input);
		String essay = readFile();
		closeFile();
	        
        Pattern p = Pattern.compile("[a-zA-Z]+"); 
        Matcher m1 = p.matcher(essay);
        
		String newEssay = "";
		
		int randomA = EncryptionChar.randomAGenerator();
		int randomB = EncryptionChar.randomBGenerator();
		
		System.out.println("The A and B values:");
		System.out.println("A: " + randomA);
		System.out.println("B: " + randomB);
		
        
        while (m1.find()) {
        	String sifrelenmisKelime = EncryptionChar.ceydaKelime(randomA, randomB,m1.group());
        	newEssay += sifrelenmisKelime + " ";
        }
        
        usingFileWriter(newEssay);
        
        System.out.println("Source: " + essay);
        System.out.println("Encrypted: " + newEssay);

		
	}

	public static void usingFileWriter(String fileContent) throws IOException
	{
	    FileWriter fileWriter = new FileWriter("encrypted_input.txt");
	    fileWriter.write(fileContent);
	    fileWriter.close();
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
	
}
