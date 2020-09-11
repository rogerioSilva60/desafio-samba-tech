package br.com.desafio.sambatech.domain.service;

import org.springframework.stereotype.Service;

import br.com.desafio.sambatech.domain.repository.MidiaRepository;

@Service
public class CadastroMidiaService {

	private MidiaRepository repository;

	public CadastroMidiaService(MidiaRepository repository) {
		this.repository = repository;
	}
	
	
}
