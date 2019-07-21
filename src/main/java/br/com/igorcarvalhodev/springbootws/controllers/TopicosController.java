package br.com.igorcarvalhodev.springbootws.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.igorcarvalhodev.springbootws.models.Topico;
import br.com.igorcarvalhodev.springbootws.models.dtos.TopicoDto;
import br.com.igorcarvalhodev.springbootws.models.dtos.TopicoFormDto;
import br.com.igorcarvalhodev.springbootws.repositories.CursoRepository;
import br.com.igorcarvalhodev.springbootws.repositories.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

	@Autowired
	TopicoRepository repository;

	@Autowired
	CursoRepository cursoRepository;

	@GetMapping
	public List<TopicoDto> listar(String nomeCurso) {
		if (nomeCurso != null && !nomeCurso.isBlank() && !nomeCurso.isEmpty()) {
			return TopicoDto.converter(repository.findByCurso_Nome(nomeCurso));
		} else {
			return TopicoDto.converter(repository.findAll());
		}
	}

	/*
	 * @RequestBody para receber o parametro no corpo da requisição http
	 * ResponseEntity devolve como retorno uma uri(endpoint) e o recurso que acabou
	 * de ser criado UriComponentsBuilder como parametro do metodo faz com que o
	 * spring injete ele para ser usado automaticamente
	 * 
	 */

	@PostMapping
	public ResponseEntity<Topico> salvar(@RequestBody TopicoFormDto topicoForm,
			UriComponentsBuilder componentsBuilder) {
		Topico topico = topicoForm.converter(cursoRepository);
		repository.save(topico);

		URI location = componentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(location).body(topico);
	}

}
