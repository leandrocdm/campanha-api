package br.com.localhost.comercialapi.controller;

import br.com.localhost.comercialapi.model.Teste;
import br.com.localhost.comercialapi.repository.TesteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private TesteRepository testeRepository;
	
	@GetMapping
	public List<Teste> listar() {
	    return testeRepository.findAll();
	}

	@GetMapping("/{id}")
    public ResponseEntity<Teste> listar(@PathVariable Integer id){
	    Optional<Teste> t = testeRepository.findById(id);

        if(!t.isPresent()){
           // return ResponseEntity.notFound().build();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nao existe teste com esse id");
        }

        return ResponseEntity.ok(t.get());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Teste adicionar(@RequestBody @Valid Teste teste){
	    Optional<Teste> t = testeRepository.findByNome(teste.getNome());

	    if(t.isPresent()){
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nao eh possivel cadastrar teste com nome ja existente");
        }

        return testeRepository.save(teste);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Teste atualizar(@RequestBody Teste teste, @PathVariable Integer id){
	    Optional<Teste> t = testeRepository.findById(id);

	    if(!t.isPresent()){
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nao existe teste com esse id para ser atualizado");
        }

        Teste newT = new Teste();
	    newT.setId(id);
	    newT.setValor(teste.getValor());
	    newT.setNome(teste.getNome());

        return testeRepository.save(newT);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deletar(@PathVariable Integer id){
        Optional<Teste> t = testeRepository.findById(id);

        if(!t.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nao existe teste com esse id para ser deletado");
        }

        testeRepository.deleteById(id);
    }
}
