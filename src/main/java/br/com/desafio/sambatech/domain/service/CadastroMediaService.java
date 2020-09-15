package br.com.desafio.sambatech.domain.service;

import java.io.InputStream;
import java.util.List;

import br.com.desafio.sambatech.domain.entity.Media;

public interface CadastroMediaService {

	Media salvar(Media media, InputStream inputStream);

	List<Media> listar(boolean deletadas);
	
	Media buscar(Long id);

	Media atualizarDeletado(Long id);

	Media atualizar(Media media, Long id, InputStream inputStream);

	void deletar(Long id);

}
