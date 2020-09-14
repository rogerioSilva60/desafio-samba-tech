package br.com.desafio.sambatech.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import br.com.desafio.sambatech.core.storage.StorageProperties.TypeStorage;
import br.com.desafio.sambatech.domain.service.MediaStorageService;
import br.com.desafio.sambatech.infrastructure.LocalMediaStorageService;
import br.com.desafio.sambatech.infrastructure.S3MediaStorageService;

@Configuration
public class StorageConfig {

	@Autowired
	private StorageProperties properties;
	
	@Bean
	public AmazonS3 amazonS3() {
		try {
			BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(properties.getS3().getIdChaveAcesso(), 
					properties.getS3().getChaveAcessoSecreta());
			
			return AmazonS3ClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
					.withRegion(properties.getS3().getRegiao())
					.build();
		} catch (Exception e) {
			
		}
		return null;
	}
	
	@Bean
	public MediaStorageService mediaStorageService() {
		if(TypeStorage.S3.equals(properties.getTypeStorage())) {
			return new S3MediaStorageService();
		} else {
			return new LocalMediaStorageService();
		}
	}
}
