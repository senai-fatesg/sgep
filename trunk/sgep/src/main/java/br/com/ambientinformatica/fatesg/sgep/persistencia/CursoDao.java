package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.List;

import br.com.ambientinformatica.fatesg.api.entidade.Curso;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;

public interface CursoDao extends Persistencia<Curso> {

	public List<Curso> listarPorNome(String nome);

}
