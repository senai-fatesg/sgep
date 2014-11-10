package br.com.ambientinformatica.fatesg.sgep.persistencia;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.fatesg.sgep.entidade.Item;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("itemDao")
public class ItemDaoJpa extends PersistenciaJpa<Item> implements ItemDao {

	private static final long serialVersionUID = 1L;

}
