/*
 * Controle responsável por gerar o resultado da prova (Gabarito com respostas)
 * 
 * */

package br.com.ambientinformatica.fatesg.sgep.controle;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.ambientjsf.util.UtilFacesRelatorio;
import br.com.ambientinformatica.fatesg.sgep.entidade.GabaritoResposta;
import br.com.ambientinformatica.fatesg.sgep.entidade.ItemQuestaoProva;
import br.com.ambientinformatica.fatesg.sgep.entidade.Prova;
import br.com.ambientinformatica.fatesg.sgep.entidade.SessaoProva;
import br.com.ambientinformatica.fatesg.sgep.persistencia.ProvaDao;
import br.com.ambientinformatica.fatesg.sgep.util.RelatorioUtil;

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

//	@SuppressWarnings("deprecation")
//	public void imprimirGabarito() {
//		Map<String, Object> parametros = new HashMap<>();
//		// parametros.put("nomeInstituicao", "FATESG");
//		// parametros.put("turma_periodo", "ADS - 1");
//		// parametros.put("TituloProva", "AII 2016");
//		try {
//			UtilFacesRelatorio.gerarRelatorioFaces("/jasper/resultadoGabarito.jasper",
//					gabarito.getRespostas(), parametros);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public void imprimirGabarito(ActionEvent evt) {
		   //TODO organizar esse metodo
			Prova provaImprimir = (Prova) UtilFaces.getValorParametro(evt, "sesImprimir");
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("PROVA_ID", provaImprimir.getId());
			
			try {
				FacesContext fc = FacesContext.getCurrentInstance();
			    ExternalContext ec = fc.getExternalContext();
			    ec.responseReset(); 
			    HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
			    ec.setResponseHeader("Content-Disposition", "attachment; filename=\"gabarito.pdf\"");
				OutputStream output = response.getOutputStream();
				new RelatorioUtil().gerarGabarito(parametros,output);
				fc.responseComplete();
				
			} catch (Exception e) {
				UtilFaces.addMensagemFaces("Houve um erro ao gerar o Relatório: " + e);
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
	
	public void imprimirGabaritoF(ActionEvent evt){
		try {
			List<ItemQuestaoProva> itensquestao = new ArrayList<>();
			Prova provaImprimir = provaDao.consultarGabarito(prova.getId());
			for (SessaoProva sessaoProva : provaImprimir.getSessoes() ) {
				for (ItemQuestaoProva itemQuestaoProva : sessaoProva.getItensQuestao()) {
					itensquestao.add(itemQuestaoProva);
				}
			}
			Map<String, Object> parametros = new HashMap<String, Object>();
			
			parametros.put("prova", provaImprimir);
			UtilFacesRelatorio.gerarRelatorioFaces("jasper/gabaritoFinal.jasper", itensquestao, parametros);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Houve um erro ao Gerar o Relatório Solicitado.\n Msg:" + e.getMessage());
		}
	}
}
