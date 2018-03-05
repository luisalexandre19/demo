package br.com.luis.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.luis.demo.entity.Vowel;
import br.com.luis.demo.repository.DemoRepository;
import br.com.luis.demo.util.DemoUtil;

/**
 * Classe responsável por processar a requisação da stream
 * 
 * @author luisalexandresilvasantos
 *
 */
@Service
public class DemoService {
	
	@Autowired
	private DemoRepository demoRepository;
	
	/**
	 * Processa a stram passada por parametro, @see findVowelInStream 
	 * e armazena no banco de dados
	 * 
	 * @param stream
	 * @return vowel com o resultado do processamento 
	 */
	public Vowel processStream(String stream) {
		
		Vowel vowel = new Vowel(stream);
		
		long initTime = System.currentTimeMillis();
		
		vowel.setVowel(findVowelInStream(stream));
		
		long endTime = System.currentTimeMillis();
		
		vowel.setTimeProcess((endTime - initTime) + "ms");

		demoRepository.save(vowel);

		return vowel;
		
	}

	/**
	 * Busca todos os processamentos realizados 
	 * 
	 * @return lista com todos os processamentos
	 */
	public Iterable<Vowel> findAll() {
		return demoRepository.findAll();
	}
	
	/**
	 * Metodo que irá encontrar o primeiro caractere Vogal, após uma consoante, 
	 *  onde a mesma é antecessora a uma vogal e que única na stream. 
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
		
		//Transforma todos caracteres para maisculo para comparações
		char[] streamCharArray = String.valueOf(stream.toUpperCase()).toCharArray();
		
		//Guarda as vogais encontrada e a quantidade de vezes que aparecem na stream
		Map<String, Integer> streamCharVowels = new HashMap<>();
		
		/*
		 * Guarda as vogais que sua antecessora é um consoante e a antecessora da consoante também é uma vogal
		 * Condição: vogal + consoante + vogal (está é considerada eleita)  
		 */
		List<String> streamCharElegibleVowels = new ArrayList<>();
		
		//Inicia a leitura na stream
		for (int i = 0; i < streamCharArray.length; i++) {
			
			//Obtem o caracter na posição i e transforma para String
			String streamChar = String.valueOf(streamCharArray[i]);
			
			//Verifica se o caracter é uma vogal
			if (DemoUtil.isVowel(streamChar)) {
				
				//Obtem no mapeamento a vogal
				Integer count = streamCharVowels.get(streamChar);
				
				//Verifica se a vogal ja foi mapeada
				if (count != null) {
					
					//Se sim, incrementa a quantidade
					streamCharVowels.put(streamChar, (++count));
					
				} else {
					
					//Se não, adiciona ao mapeamento
					streamCharVowels.put(streamChar, 1);
					
				}
				
			} else {

				//Obtem os caracteres anterior e posterior da consoante para verificar a regra
				String streamCharPrevious = String.valueOf(streamCharArray[i-1]);
				String streamCharNext = String.valueOf(streamCharArray[i+1]);
				
				//Verifica se a consoante está entre vogais, anterior e posterior 
				if (DemoUtil.isVowel(streamCharPrevious)
					&& DemoUtil.isVowel(streamCharNext)) {
					
					//Se estiver, adiciona a lista de possiveis vogais eleitas para validação final
					streamCharElegibleVowels.add(String.valueOf(stream.charAt(i + 1)));
					
				}
				
			}
			
		}
		
		//Verifica se encontrou alguma vogal elegida
		if (!streamCharElegibleVowels.isEmpty()) {
			
			//Percorre a lista encontrada
			for (String vowel : streamCharElegibleVowels) {
				
				//Se tiver somente uma única referencia dessa vogal, ela é considerada a eleita.
				if (streamCharVowels.get(vowel.toUpperCase()) == 1) {
					
					return vowel;
					
				}
				
			}
			
		}
		
		//Se não encontrar, é retornado o valor padrão
		return "Nao encontrada";
		
	}
	
}
