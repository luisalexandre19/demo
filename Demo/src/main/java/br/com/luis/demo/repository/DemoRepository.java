package br.com.luis.demo.repository;

import org.springframework.data.repository.CrudRepository;
import br.com.luis.demo.entity.Vowel;

/**
 * Classe para abstração do Crud com a base de dados, usando JPA
 * 
 * @author luisalexandresilvasantos
 *
 */
public interface DemoRepository extends CrudRepository<Vowel, Long> {

}
