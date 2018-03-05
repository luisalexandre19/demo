package br.com.luis.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.luis.demo.entity.Vowel;
import br.com.luis.demo.service.DemoService;

/**
 * Classe que gera a ApiRestfull para requisação da stream e buscar dos processamentos de streams realizados
 * Assim como prover um status positivo ao healthcheck realizado pelo docker
 * 
 * @author luisalexandresilvasantos
 *
 */
@RestController
@RequestMapping(path="/demo") 
public class DemoController {
	
	@Autowired
	private DemoService demoService;
	
	/**
	 * Recebe por path a stream a ser processa, invoca o serviço para processamento
	 * e retorna o retorno do processamento 
	 * @param stream para processamento
	 * @return Vowel com o retorno do processamento
	 */
	@GetMapping(path="/process/{stream}") 
	public @ResponseBody Vowel processStream(@PathVariable String stream) {
		return demoService.processStream(stream);
	}

	/**
	 * Retorna todos os processamentos realizados
	 * @return lista dos processamentos realizados
	 */
	@GetMapping(path="/findAll")
	public @ResponseBody Iterable<Vowel> findAll() {
		return demoService.findAll();
	}
	
	/**
	 * Fornece um indicador de positivo ao healthcheck do docker
	 * 
	 * @return string OK
	 */
	@GetMapping(path="/healthCheck")
	public @ResponseBody String healthCheck() {
		return "OK";
	}

}
