package br.com.luis.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe que armazena o resultado do processamento
 * 
 * @author luisalexandresilvasantos
 *
 */
@Entity
public class Vowel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String stream;
	private String vowel;
	private String timeProcess;

	public Vowel() {
		super();
	}

	public Vowel(String stream) {
		super();
		this.stream = stream;
	}

	@JsonIgnore
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	@JsonProperty(value="vogal")
	public String getVowel() {
		return vowel;
	}

	public void setVowel(String vowel) {
		this.vowel = vowel;
	}

	@JsonProperty(value="tempoTotal")
	public String getTimeProcess() {
		return timeProcess;
	}

	public void setTimeProcess(String timeProcess) {
		this.timeProcess = timeProcess;
	}

}
