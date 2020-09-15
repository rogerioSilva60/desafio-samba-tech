package br.com.desafio.sambatech.infrastructure;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import br.com.desafio.sambatech.core.storage.StorageProperties;
import br.com.desafio.sambatech.domain.service.MediaStorageService;
import br.com.desafio.sambatech.domain.util.exception.StorageException;


public class S3MediaStorageService implements MediaStorageService {

	@Autowired
	private AmazonS3 amanzonS3;
	
	@Autowired
	private StorageProperties storageProperties;
	
	@Override
	public void armazenar(NovaMedia novaMedia) {
		try {
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
		} catch (Exception e) {
			throw new StorageException("Não foi possivel enviar arquivo para Amazon S3.", e);
		}
	}

	@Override
	public void deletar(String nomeArquivo) {
		try { 
			String caminhoArquivo = prepararArquivo(nomeArquivo);
			
			DeleteObjectRequest deleteObjectRequest = 
					new DeleteObjectRequest(storageProperties.getS3().getBucket(), caminhoArquivo);
			
			amanzonS3.deleteObject(deleteObjectRequest);
		} catch (Exception e) {
			throw new StorageException("Não foi possivel deletar arquivo para Amazon S3.", e);
		}
	}

	@Override
	public MediaRecuperada recuperar(String nomeArquivo) {
		try {
			String caminhoArquivo = prepararArquivo(nomeArquivo);
			
			URL url = amanzonS3.getUrl(storageProperties.getS3().getBucket(), caminhoArquivo);
			
			MediaRecuperada mediaRecuperada = MediaRecuperada.builder().url(url.toString()).build();
			
			return mediaRecuperada;
		} catch (Exception e) {
			throw new StorageException("Não foi possivel recuperar o caminho do arquivo na Amazon S3.", e);
		}
	}
	
	private String prepararArquivo(String nomeArquivo) {
		String caminho = String.format("%s/%s", storageProperties.getS3().getDiretorioMedia(), nomeArquivo);
		return caminho;
	}

}
