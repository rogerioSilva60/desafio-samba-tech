package br.com.desafio.sambatech.api.dto;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import br.com.desafio.sambatech.core.storage.validation.FileContentType;
import lombok.Data;

@Data
public class MediaDto {

	@NotNull(message = "Arquivo obrigatório")
	@FileContentType(allowed = { "video/mp4"})
	private MultipartFile arquivo;
	
	private Long duracao;
	
	private String descricao;
}
