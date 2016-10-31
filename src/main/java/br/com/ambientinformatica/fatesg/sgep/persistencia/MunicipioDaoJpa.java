package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.fatesg.api.entidade.EnumUf;
import br.com.ambientinformatica.fatesg.api.entidade.Municipio;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.util.UtilLog;

@Repository("municipioDao")
public class MunicipioDaoJpa extends PersistenciaJpa<Municipio> implements MunicipioDao {

	private static final long serialVersionUID = 1L;


	@SuppressWarnings("unchecked")
   @Override
   public List<Municipio> listarPorUf(EnumUf uf, String descricao) throws PersistenciaException {
       try {

           String sql = "select distinct m from Municipio m where 1 = 1 ";

           if(uf != null){
               sql += " and m.uf = :uf";
           }
           if(descricao != null && !descricao.isEmpty()){
               sql += " and upper(m.descricao) like upper(:descricao)";
           }

           sql += " order by m.descricao";
           Query query = em.createQuery(sql);
           

           if(uf != null){
               query.setParameter("uf", uf);
           }
           if(descricao != null && !descricao.isEmpty()){
           	query.setParameter("descricao", descricao + "%");
           }

           return query.getResultList();
       }catch (Exception e) {
           UtilLog.getLog().error(e.getMessage(), e);
       }
       return null;
   }


	@Override
	public Municipio consultarPorCodigoIBGE(Integer codigoIBGE) throws PersistenciaException {
		try{
         Query query = em.createQuery("select distinct m from Municipio m where m.codigoIbge = :codigoIbge");
         query.setParameter("codigoIbge", codigoIBGE);
         return (Municipio) query.getSingleResult();
     } catch (Exception e){
         UtilLog.getLog().error(e.getMessage(), e);
     }
     return null;
	}
}