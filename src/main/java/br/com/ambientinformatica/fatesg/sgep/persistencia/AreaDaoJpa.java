package br.com.ambientinformatica.fatesg.sgep.persistencia;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.fatesg.sgep.entidade.Area;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("areaDao")
public class AreaDaoJpa extends PersistenciaJpa<Area> implements AreaDao {

	private static final long serialVersionUID = 1L;

}
