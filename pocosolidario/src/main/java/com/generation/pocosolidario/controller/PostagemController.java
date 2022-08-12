package com.generation.pocosolidario.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.pocosolidario.model.PostagemModel;
import com.generation.pocosolidario.repository.PostagemRepository;

@RestController
@RequestMapping ("/postagens")
@CrossOrigin("*")
public class PostagemController {

	@Autowired
	private PostagemRepository postagemRepository;
	
	@GetMapping
	public ResponseEntity <List<PostagemModel>>getAll(){
		return ResponseEntity.ok(postagemRepository.findAll()); 
	}
	
	
	@GetMapping ("/{id}")
	public ResponseEntity <PostagemModel> getById (@PathVariable Long id){
		return postagemRepository.findById(id)
				.map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping ("/legenda/{legenda}")
	public ResponseEntity <List<PostagemModel>> getByLegenda (@PathVariable String local){ 
		return ResponseEntity.ok(postagemRepository.findAllBylocalizacaoContainingIgnoreCase (local));
	}
	@PostMapping
	public ResponseEntity <PostagemModel> post (@RequestBody @Valid  PostagemModel post){ //RequestBody pega o que tem no corpo da requisição
		return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(post));	
	}
	@PutMapping
	public ResponseEntity <PostagemModel> put (@RequestBody PostagemModel post){ 
		return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(post));
	}
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping ("/{id}")
	public void delete (@PathVariable Long id) {
	Optional<PostagemModel> post = postagemRepository.findById(id);
		if(post.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		postagemRepository.deleteById(id);
	}
	
}
