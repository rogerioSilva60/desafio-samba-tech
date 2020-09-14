package br.com.desafio.sambatech.infrastructure;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import br.com.desafio.sambatech.core.storage.StorageProperties;
import br.com.desafio.sambatech.domain.service.MediaStorageService;


public class S3MediaStorageService implements MediaStorageService {

	@Autowired
	private AmazonS3 amanzonS3;
	
	@Autowired
	private StorageProperties storageProperties;
	
	@Override
	public void armazenar(NovaMedia novaMedia) {
		String caminhoArquivo = prepararArquivo(novaMedia.getNome());
		
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(novaMedia.getContentType());
		
		PutObjectRequest putObjectRequest = new PutObjectRequest(
				storageProperties.getS3().getBucket(),
				caminhoArquivo,
				novaMedia.getInputStream(),
				objectMetadata)
		.withCannedAcl(CannedAccessControlList.PublicRead);
		
		amanzonS3.putObject(putObjectRequest);
	}

	@Override
	public void deletar(String nomeArquivo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InputStream recuperar(String nomeArquivo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private String prepararArquivo(String nomeArquivo) {
		String caminho = String.format("%s/%s", storageProperties.getS3().getDiretorioMedia(), nomeArquivo);
		return caminho;
	}

}
