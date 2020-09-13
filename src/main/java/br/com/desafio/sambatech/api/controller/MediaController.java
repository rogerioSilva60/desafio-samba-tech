package br.com.desafio.sambatech.api.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
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
import org.springframework.web.multipart.MultipartFile;

import br.com.desafio.sambatech.domain.entity.Media;
import br.com.desafio.sambatech.domain.service.CadastroMediaService;


@RestController
@RequestMapping("api/v1/medias")
public class MediaController {

	private CadastroMediaService cadastroMediaService;
	
	public MediaController(CadastroMediaService cadastroMediaService) {
		this.cadastroMediaService = cadastroMediaService;
	}

	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping
	public Media salvar(MultipartFile multipartFile) throws IllegalStateException, IOException {

//		Path path = Path.of("src/main/resources/catalogos", multipartFile.getOriginalFilename());
//		multipartFile.transferTo(path);
		Media media = Media.builder().nome(multipartFile.getOriginalFilename())
				.build();
		Media mediaSalva = cadastroMediaService.salvarOuAlterar(media);
		return mediaSalva;
	}
	
	@GetMapping
	public List<Media> listar(@RequestParam(required = false) Boolean ehTodas) {
		ehTodas = ehTodas == null ? true : ehTodas;
		List<Media> medias = cadastroMediaService.listar(ehTodas);
		return medias;
	}

	@GetMapping("{id}")
	public Media buscar(@PathVariable Long id) {
		Media media = cadastroMediaService.buscar(id);
		return media;
	}
	
	@PatchMapping("{id}")
	public Media atualizarDeletado(@PathVariable Long id) {
		Media media = cadastroMediaService.atualizarDeletado(id);
		return media;
	}
	
	@PutMapping("{id}")
	public Media atualizar(@PathVariable Long id, @RequestBody Media media) {
		Media mediaAtualizada = cadastroMediaService.atualizar(media, id);
		return mediaAtualizada;
	}
	
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@DeleteMapping("{id}")
	public void deletar(@PathVariable Long id) {
		cadastroMediaService.deletar(id);
	}
}
