package br.com.ambientinformatica.fatesg.sgep.util;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;

public class PropertiesLoader {

	public String getValor(String nomeArquivo, String chave){
		ClassLoader loader = Thread.currentThread().getContextClassLoader(); 
		Properties props = new Properties(); 
		try {
			InputStream resourceStream = loader.getResourceAsStream(nomeArquivo);
			props.load(resourceStream);
		} catch (IOException e) {
			UtilFaces.addMensagemFaces(e);
		} 
		return (String) props.get(chave);
	}
}