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
	
	@Getter
	@Setter
	public class Local {
		private Path diretorioMedia;
	}
}
