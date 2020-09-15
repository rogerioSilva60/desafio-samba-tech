package br.com.desafio.sambatech.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import br.com.desafio.sambatech.api.controller.MediaController;
import br.com.desafio.sambatech.domain.entity.Media;
import br.com.desafio.sambatech.domain.service.CadastroMediaService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(MediaController.class)
@AutoConfigureMockMvc
public class MediaControllerTest {

	private static final String USER_API = "/api/v1/medias";
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	CadastroMediaService cadastroService;
	
	@Test
	@DisplayName("Deve salvar media com sucesso")
	public void salvarMediaTest() throws Exception {
		//cenario
		Media mediaFake = Media.builder()
				.id(1l)
				.contentType("video/mp4")
				.nome("musica.mp4")
				.descricao("Media para testes")
				.duracao(54545226l)
				.build();
		
		//Simulando a resposta ao criar o usuario.
        BDDMockito.given(cadastroService.salvar(Mockito.any(Media.class), Mockito.any(InputStream.class)))
                .willReturn(mediaFake);
        
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("descricao", mediaFake.getDescricao());
        parameters.add("duracao", mediaFake.getDuracao().toString());
        
        MockMultipartFile file = new MockMultipartFile(
          "arquivo", 
          mediaFake.getNome(), 
          mediaFake.getContentType(), 
          mediaFake.getDescricao()
          .getBytes()
        );
        
        //Execucao
        //Simulando o envio pro controller.
        MockHttpServletRequestBuilder multipartRequest = multipart(USER_API)
        .file(file)
        .params(parameters)
        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
        .accept(MediaType.APPLICATION_JSON);
        
        
        //Verificacao
        mvc
           .perform(multipartRequest)
           .andExpect(status().isCreated())
           .andExpect(jsonPath("data.id").isNotEmpty())
           .andExpect(jsonPath("data.nome").value(mediaFake.getNome()))
           .andExpect(jsonPath("data.descricao").value(mediaFake.getDescricao()))
           .andExpect(jsonPath("data.duracao").value(mediaFake.getDuracao()));
        
	}
}
