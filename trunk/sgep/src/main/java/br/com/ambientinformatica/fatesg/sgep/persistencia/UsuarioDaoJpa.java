package br.com.ambientinformatica.fatesg.sgep.persistencia;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.fatesg.sgep.entidade.Usuario;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.util.UtilLog;

@Repository("usuarioDao")
public class UsuarioDaoJpa extends PersistenciaJpa<Usuario> implements UsuarioDao{

   private static final long serialVersionUID = 1L;
   
   @Override
	public Usuario consultarPorLogin(String login) throws PersistenceException {
		try {
			String sql = "select distinct u from Usuario u left join fetch u.papeis p where u.login = :login";
			Query query = em.createQuery(sql);
			query.setParameter("login", login);
			return (Usuario) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
			throw new PersistenceException("Erro ao consultar usuário pelo Login");
		}
	}

}
