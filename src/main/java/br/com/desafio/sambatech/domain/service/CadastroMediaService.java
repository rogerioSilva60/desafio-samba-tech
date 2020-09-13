package br.com.desafio.sambatech.domain.service;

import java.util.List;

import br.com.desafio.sambatech.domain.entity.Media;

public interface CadastroMediaService {

	Media salvarOuAlterar(Media media);

	List<Media> listar(boolean deletadas);
	
	Media buscar(Long id);

	Media atualizarDeletado(Long id);

	Media atualizar(Media media, Long id);

	void deletar(Long id);

}
