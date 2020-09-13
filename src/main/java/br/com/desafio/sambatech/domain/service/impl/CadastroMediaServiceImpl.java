package br.com.desafio.sambatech.domain.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.desafio.sambatech.domain.entity.Media;
import br.com.desafio.sambatech.domain.repository.MediaRepository;
import br.com.desafio.sambatech.domain.service.CadastroMediaService;

@Service
public class CadastroMediaServiceImpl implements CadastroMediaService {

	private MediaRepository repository;

	public CadastroMediaServiceImpl(MediaRepository repository) {
		this.repository = repository;
	}

	@Override
	public Media salvarOuAlterar(Media media) {
		return repository.save(media);
	}

	@Override
	public List<Media> listar(boolean ehTodas) {
		List<Media> lista = ehTodas ? repository.findAll() : repository.findByDeletado(ehTodas);		 
		return lista;
	}

	@Override
	public Media buscar(Long id) {
		Media media = repository.findById(id)
			.orElseThrow(() -> new RuntimeException("Media não encontrada"));
		return media;
	}
	
	@Override
	public Media atualizarDeletado(Long id) {
		Media media = buscar(id);
		media.setDeletado(!media.isDeletado());
		Media mediaAtualizada = salvarOuAlterar(media);
		return mediaAtualizada;
	}

	@Override
	public Media atualizar(Media media, Long id) {
		buscar(id);
		media.setId(id);
		Media mediaAtualizada = salvarOuAlterar(media);
		return mediaAtualizada;
	}

	@Override
	public void deletar(Long id) {
		buscar(id);
		repository.deleteById(id);
	}
	
}
