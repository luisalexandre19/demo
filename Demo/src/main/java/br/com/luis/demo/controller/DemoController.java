package br.com.luis.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.luis.demo.entity.Vowel;
import br.com.luis.demo.service.DemoService;

@RestController
@RequestMapping(path="/demo") 
public class DemoController {
	
	@Autowired
	private DemoService demoService;
	
	@GetMapping(path="/process/{stream}") 
	public @ResponseBody Vowel processStream(@PathVariable String stream) {
		return demoService.processStream(stream);
	}

	@GetMapping(path="/findAll")
	public @ResponseBody Iterable<Vowel> findAll() {
		return demoService.findAll();
	}

}
