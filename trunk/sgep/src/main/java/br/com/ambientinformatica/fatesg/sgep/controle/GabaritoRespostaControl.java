/*
 * Controle responsável por gerar o resultado da prova (Gabarito com respostas)
 * 
 * */

package br.com.ambientinformatica.fatesg.sgep.controle;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFacesRelatorio;
import br.com.ambientinformatica.fatesg.sgep.entidade.GabaritoResposta;
import br.com.ambientinformatica.fatesg.sgep.entidade.ItemQuestaoProva;
import br.com.ambientinformatica.fatesg.sgep.entidade.Prova;
import br.com.ambientinformatica.fatesg.sgep.entidade.SessaoProva;
import br.com.ambientinformatica.fatesg.sgep.persistencia.ProvaDao;

@Controller("GabaritoRespostaControl")
@Scope("conversation")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class GabaritoRespostaControl {

	private HashMap questoes;
	@Autowired
	private ProvaDao provaDao;
	
	private Prova prova = new Prova();
	
	private GabaritoResposta gabarito = new GabaritoResposta();

	@PostConstruct
	public void init() {
		
	}
	
	public void inicializarItensQuestaoProva(){
		HashMap entryListaQuestoes = new HashMap<>();
		Prova p = provaDao.consultar(prova.getId());
		int i = 0;
		for (SessaoProva itemSessao : p.getSessoes()) {
			for (ItemQuestaoProva itemQuestaoProva : itemSessao.getItensQuestao()) {
				entryListaQuestoes.put(i+1, itemQuestaoProva.getQuestao().getQuestao().getResposta());
				i++;
			}
		}
		this.setQuestoes(entryListaQuestoes);
		this.getGabarito().setQuestoes(this.getQuestoes());
	}


	public GabaritoResposta getGabarito() {
		return this.gabarito;
	}
	
	public void setGabarito(GabaritoResposta gabarito) {
		this.gabarito = gabarito;
	}

	@SuppressWarnings("deprecation")
	public void imprimirGabarito() {
		Map<String, Object> parametros = new HashMap<>();
		// parametros.put("nomeInstituicao", "FATESG");
		// parametros.put("turma_periodo", "ADS - 1");
		// parametros.put("TituloProva", "AII 2016");
		try {
			UtilFacesRelatorio.gerarRelatorioFaces("/jasper/resultadoGabarito.jasper",
					gabarito.getRespostas(), parametros);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public HashMap getQuestoes() {
		return questoes;
	}
	
	public void setQuestoes(HashMap questoes) {
		this.questoes = questoes;
	}

	public Prova getProva() {
		return prova;
	}

	public void setProva(Prova prova) {
		this.prova = prova;
	}
}
