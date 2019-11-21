package com.prestonsproductions.oauth2service.config;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.ConfigValueFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

@Configuration
public class PropertiesConfig {

	@Bean
	public Config properties() throws IOException {
		Config conf = ConfigFactory.parseFile(new File("oauth2service.properties"));
		
		// Set Log Level
		Logger root = (Logger)LoggerFactory.getLogger("com.prestonsproductions.oauth2service");
		root.setLevel(getLogLevel(conf.getString("com.prestonsproductions.oauth2service.LOG_LEVEL")));
		
		//AWSKMS kms = AWSKMSClientBuilder.defaultClient();
		//conf = addNewPropertyToConfig(conf,decrypt(kms, conf.getString("com.prestonsproductions.oauth2service.DATABASE_PASSWORD")), "com.prestonsproductions.oauth2service.DATABASE_PASSWORD");;
		return conf;
	}
	
	@SuppressWarnings("unused")
	private Config addNewPropertyToConfig(Config newConf, String decryptedString, String property) {
		ConfigValue cv = ConfigValueFactory.fromAnyRef(decryptedString);
		newConf = newConf.withValue(property, cv);
		return newConf;
	}
	
	@SuppressWarnings("unused")
	private String decrypt(AWSKMS kms, String password) {
		String decodedString = "";
		ByteBuffer ciphertext = ByteBuffer.wrap(com.amazonaws.util.Base64.decode(password));
		DecryptRequest decreq = new DecryptRequest().withCiphertextBlob(ciphertext);
		ByteBuffer plaintext = kms.decrypt(decreq).getPlaintext();
		decodedString = Charset.forName("UTF-8").decode(plaintext).toString();
		return decodedString;
	}
	
	private Level getLogLevel(String level) {
		Level l = Level.INFO;
		if (level != null) {
			if ("debug".equalsIgnoreCase(level)) l = Level.DEBUG;
			else if ("info".equalsIgnoreCase(level)) l = Level.INFO;
			else if ("error".equalsIgnoreCase(level)) l = Level.ERROR;
			else if ("off".equalsIgnoreCase(level)) l = Level.OFF;
			else if ("all".equalsIgnoreCase(level)) l = Level.ALL;
		}
		return l;
	}

}