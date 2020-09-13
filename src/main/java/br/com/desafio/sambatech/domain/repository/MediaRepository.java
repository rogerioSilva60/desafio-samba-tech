package br.com.desafio.sambatech.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.desafio.sambatech.domain.entity.Media;

public interface MediaRepository extends JpaRepository<Media, Long>{

	List<Media> findByDeletado(boolean deletado);

}
