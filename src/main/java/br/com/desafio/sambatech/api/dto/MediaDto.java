package br.com.desafio.sambatech.api.dto;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MediaDto {

	@NotNull(message = "Arquivo obrigatório")
	private MultipartFile arquivo;
}
