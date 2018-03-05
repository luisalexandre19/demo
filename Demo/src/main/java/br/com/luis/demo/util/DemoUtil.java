package br.com.luis.demo.util;

/**
 * Classe utlitária com metodos utilizaveis em todo projeto 
 * 
 * @author luisalexandresilvasantos
 *
 */
public class DemoUtil {
	
	/**
	 * Verifica se o caracter é uma vogal
	 * 
	 * @param c
	 * @return true caso seja vogal ou false caso contrário
	 */
	public static boolean isVowel(String c) {
		return "AEIOU".indexOf(c) != -1;
	}
	
}
