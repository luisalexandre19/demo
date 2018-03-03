package br.com.luis.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.luis.demo.entity.Vowel;
import br.com.luis.demo.repository.DemoRepository;
import br.com.luis.demo.util.DemoUtil;

@Service
public class DemoService {
	
	@Autowired
	private DemoRepository demoRepository;
	
	public Vowel processStream(String stream) {
		
		Vowel vowel = new Vowel(stream);
		
		long initTime = System.currentTimeMillis();
		
		vowel.setVowel(findVowelInStream(stream));
		
		long endTime = System.currentTimeMillis();
		
		vowel.setTimeProcess((endTime - initTime) + "ms");

		demoRepository.save(vowel);

		return vowel;
		
	}

	public Iterable<Vowel> findAll() {
		return demoRepository.findAll();
	}
	
	/**
	 * Metodo que irá encontrar o primeiro caractere Vogal, após uma consoante, 
	 *  onde a mesma é antecessora a uma vogal e que não se repita no resto da stream. 
	 * @param vogal
	 * @return vogal 
	 * 
	 Exemplo:
	Input:  aAbBABacafe
	Output: e
	No exemplo, ‘e’ é o primeiro caractere Vogal da stream que não se repete
	após a primeira Consoante ‘f’o qual tem uma vogal ‘a’ como antecessora.
	
	O resultado do processamento deverá ser igual á:
	
	{
	  "stream": " aAbBABacafe",
	  "vogal": "e",
	  "tempoTotal": "10ms"
	}

	 * 
	 */
	private String findVowelInStream(String stream) {
		
		char[] streamCharArray = String.valueOf(stream.toUpperCase()).toCharArray();
		
		for (int i = 1; i < (streamCharArray.length - 1); i++) {
			
			if (!DemoUtil.isVowel(streamCharArray[i])
					&& DemoUtil.isVowel(streamCharArray[i-1])
					&& DemoUtil.isVowel(streamCharArray[i+1])
					&& !DemoUtil.containsStartByPosition(streamCharArray, streamCharArray[i+1], i+2)
					) {
				
				return String.valueOf(streamCharArray[i+1]);
				
			}
			
		}
		
		return "Nao encontrada";
		
	}
	
}
