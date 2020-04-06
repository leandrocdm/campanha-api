package br.com.localhost.comercialapi.repository;

import br.com.localhost.comercialapi.model.Teste;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface TesteRepository extends JpaRepository<Teste, Integer> {

    Optional<Teste> findByNome(String nome);
    Optional<Teste> findByNomeAndValor(String nome, BigDecimal valor);

}
