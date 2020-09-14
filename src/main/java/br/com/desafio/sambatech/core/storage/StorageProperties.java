package br.com.desafio.sambatech.core.storage;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("samba-tech.storage")
public class StorageProperties {

	private Local local = new Local();
	private S3 s3 = new S3();
	private TypeStorage typeStorage = TypeStorage.LOCAL;
	
	public enum TypeStorage {
		LOCAL, S3;
	}
	
	@Getter
	@Setter
	public class Local {
		private Path diretorioMedia;
	}
	
	@Getter
	@Setter
	public class S3 {
		private String idChaveAcesso;
		private String chaveAcessoSecreta;
		private String bucket;
		private String regiao;
		private String diretorioMedia;
	}
}
