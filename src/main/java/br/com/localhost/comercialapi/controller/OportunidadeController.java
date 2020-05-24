package br.com.localhost.comercialapi.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.com.localhost.comercialapi.model.Oportunidade;
import br.com.localhost.comercialapi.repository.OportunidadeRepository;

@RestController
@RequestMapping("/oportunidades")
public class OportunidadeController {
	
	@Autowired
	private OportunidadeRepository oportunidadeRepository;

	@GetMapping
	public List<Oportunidade> listar() {
		return oportunidadeRepository.findAll();
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Oportunidade> buscar(@PathVariable Long id) {
		Optional<Oportunidade> oportunidade = oportunidadeRepository.findById(id);
		
		if(!oportunidade.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nao existe oportunidade com esse id");
		}
		return ResponseEntity.ok(oportunidade.get());		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)  
	public Oportunidade adicionar(@Valid @RequestBody Oportunidade oportunidade){
		Optional<Oportunidade> oportunidadeExistente = 
				oportunidadeRepository.findByDescricaoAndNomeProspecto(oportunidade.getDescricao(), oportunidade.getNomeProspecto());
		
		if(oportunidadeExistente.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ja existe oportunidade com essa descricao");			
		}
		return oportunidadeRepository.save(oportunidade);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Oportunidade atualizar(@Valid @RequestBody Oportunidade oportunidade, @PathVariable Long id){
		Optional<Oportunidade> oportunidadeExistente = oportunidadeRepository.findById(id);

		if(!oportunidadeExistente.isPresent()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nao existe essa oportunidade para ser atualizada");
		}
		Oportunidade novaOportunidade = new Oportunidade(id, oportunidade.getNomeProspecto(),oportunidade.getDescricao(),oportunidade.getValor());
		return oportunidadeRepository.save(novaOportunidade);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void deletar(@PathVariable Long id){
		Optional<Oportunidade> oportunidade = oportunidadeRepository.findById(id);
		
		if(!oportunidade.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe essa oportunidade");
		}else {
			oportunidadeRepository.deleteById(id);
		}		
	}
}
