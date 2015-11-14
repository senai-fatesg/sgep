package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.List;

import br.com.ambientinformatica.fatesg.sgep.entidade.SessaoProva;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;

public interface SessaoProvaDao extends Persistencia<SessaoProva> {

	List<SessaoProva> consultarPeloTitulo(String nome);
}
