package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.List;

import br.com.ambientinformatica.fatesg.api.entidade.EnumUf;
import br.com.ambientinformatica.fatesg.api.entidade.Municipio;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;

public interface MunicipioDao extends Persistencia<Municipio>{

   List<Municipio> listarPorUf(EnumUf uf, String descricao) throws PersistenciaException;

	Municipio consultarPorCodigoIBGE(Integer codigoIBGE)throws PersistenciaException;

}
