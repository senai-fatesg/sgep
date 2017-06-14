package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.List;

import br.com.ambientinformatica.fatesg.sgep.entidade.Prova;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;

public interface ProvaDao extends Persistencia<Prova> {

	List<Prova> listarProvas() throws PersistenciaException;

	Prova consultarProva(Integer id) throws PersistenciaException;

}
