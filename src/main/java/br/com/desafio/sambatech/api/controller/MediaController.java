package br.com.desafio.sambatech.api.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafio.sambatech.api.dto.MediaDto;
import br.com.desafio.sambatech.domain.entity.Media;
import br.com.desafio.sambatech.domain.service.CadastroMediaService;
import br.com.desafio.sambatech.domain.util.response.Response;

@RestController
@RequestMapping("api/v1/medias")
public class MediaController {

	private CadastroMediaService cadastroMediaService;

	public MediaController(CadastroMediaService cadastroMediaService) {
		this.cadastroMediaService = cadastroMediaService;
	}

	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Response<Media>> salvar(@Valid MediaDto dto) throws IOException {
		Response<Media> response = new Response<>();
		Media media = Media.builder().nome(dto.getArquivo().getOriginalFilename()).contentType(dto.getArquivo().getContentType()).build();
		Media mediaSalva = cadastroMediaService.salvar(media, dto.getArquivo().getInputStream());
		response.setData(mediaSalva);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);

	}

	@GetMapping
	public ResponseEntity<Response<List<Media>>> listar(@RequestParam(required = false) Boolean ehTodas) {
		ehTodas = ehTodas == null ? true : ehTodas;
		Response<List<Media>> response = new Response<>();
		List<Media> medias = cadastroMediaService.listar(ehTodas);
		response.setData(medias);
		return ResponseEntity.ok(response);
	}

	@GetMapping("{id}")
	public ResponseEntity<Response<Media>> buscar(@PathVariable Long id) {
		Response<Media> response = new Response<>();
		Media media = cadastroMediaService.buscar(id);
		response.setData(media);
		return ResponseEntity.ok(response);
	}

	@PatchMapping("deletado/{id}")
	public ResponseEntity<Response<Media>> atualizarDeletado(@PathVariable Long id) {
		Response<Media> response = new Response<>();
		Media media = cadastroMediaService.atualizarDeletado(id);
		response.setData(media);
		return ResponseEntity.ok(response);
	}

	@PutMapping("{id}")
	public ResponseEntity<Response<Media>> atualizar(@PathVariable Long id, @RequestBody Media media) {
		Response<Media> response = new Response<>();
		Media mediaAtualizada = cadastroMediaService.atualizar(media, id);
		response.setData(mediaAtualizada);
		return ResponseEntity.ok(response);
	}

	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@DeleteMapping("{id}")
	public void deletar(@PathVariable Long id) {
		cadastroMediaService.deletar(id);
	}
}
