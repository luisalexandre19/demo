package br.com.luis.demo.repository;

import org.springframework.data.repository.CrudRepository;
import br.com.luis.demo.entity.Vowel;

public interface DemoRepository extends CrudRepository<Vowel, Long> {

}
