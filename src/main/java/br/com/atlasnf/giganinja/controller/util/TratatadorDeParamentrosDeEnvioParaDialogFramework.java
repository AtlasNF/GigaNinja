package br.com.atlasnf.giganinja.controller.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TratatadorDeParamentrosDeEnvioParaDialogFramework {
	
private Map<String, List<String>> parametros = new HashMap<String, List<String>>();
	
	public TratatadorDeParamentrosDeEnvioParaDialogFramework add(String nome, String valor) {
		parametros.put(nome, Arrays.asList(valor));
		return this;
	}
	
	public void add(Map<String, String> parametros) {
		for (Map.Entry<String, String> item : parametros.entrySet()) {
			add(item.getKey(), item.getValue());
		}
	}
	
	public  Map<String, List<String>> getParametros() {
		return parametros;
	}

}
