package br.com.luis.demo.util;

public class DemoUtil {
	
	public static boolean isVowel(char c) {
		return "AEIOU".indexOf(c) != -1;
	}
	
	public static boolean containsStartByPosition(char[] array, char key, int startPosition) {
		
		for (int i = startPosition; i < array.length; i++) {

			if (array[i] == key) {
				return true;
			}
			
		}
		
		return false;
	}

}
