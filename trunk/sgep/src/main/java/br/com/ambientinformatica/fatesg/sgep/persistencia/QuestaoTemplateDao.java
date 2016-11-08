package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.util.List;

import br.com.ambientinformatica.fatesg.sgep.entidade.QuestaoTemplate;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;

public interface QuestaoTemplateDao extends Persistencia<QuestaoTemplate> {

	public List<QuestaoTemplate> consultarPor(String paralvra, String tipo) throws Exception;

	public QuestaoTemplate carregarQuestao(QuestaoTemplate questao) throws Exception;

	public List<QuestaoTemplate> listarQuestoes() throws Exception;
}
