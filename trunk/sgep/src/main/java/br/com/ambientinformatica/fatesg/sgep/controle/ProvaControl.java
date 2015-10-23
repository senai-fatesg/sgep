package br.com.ambientinformatica.fatesg.sgep.controle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.ambientjsf.util.UtilFacesRelatorio;
import br.com.ambientinformatica.fatesg.sgep.entidade.ItensProva;
import br.com.ambientinformatica.fatesg.sgep.entidade.Prova;
import br.com.ambientinformatica.fatesg.sgep.entidade.Questao;
import br.com.ambientinformatica.fatesg.sgep.entidade.Template;
import br.com.ambientinformatica.fatesg.sgep.persistencia.ColaboradorDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.ProvaDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.TemplateDao;
import br.com.ambientinformatica.util.UtilException;

@Controller("ProvaControl")
@Scope("conversation")
public class ProvaControl {
	
	@Autowired
	private ProvaDao provaDao;
	
	@Autowired
	private TemplateDao templateDao;
	
	@Autowired
	private ColaboradorDao colaboradorDao;
	
	private List<Prova> provas = new ArrayList<Prova>();

	private Questao questao = new Questao();

	private Prova prova = new Prova();
	
	private Prova provaSelecionada = new Prova();
	
	private ItensProva itensProva = new ItensProva();
	
	private Template template = new Template();

	
	@PostConstruct
	public void init() {
		try {
	      colaboradorDao.listarTodos();
      } catch (Exception e) {
    	  UtilFaces.addMensagemFaces("Houve um erro ao listar Todos: "+ e);
      }
		listar(null);
	}

	public void imprimirProva(ActionEvent evt){
		Map<String, Object> parametros = new HashMap<String, Object>();
//		parametros.put("nomeInstituicao", EmpresaLogadaControl.getEmpresa().getNome());
//		parametros.put("valorTotal", 55.5);
		
		try {
			UtilFacesRelatorio.gerarRelatorioFaces("jasper/prova.jasper", prova.getQuestoes(), parametros);
		} catch (UtilException e) {
			UtilFaces.addMensagemFaces("Houve um erro ao gerar o Relatório: "+ e);
		}
	}
	
	public void confirmar(ActionEvent evt) {
		try {
			provaDao.alterar(prova);
			listar(evt);
			UtilFaces.addMensagemFaces("Operação realizada com sucesso");
			prova = new Prova();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void listar(ActionEvent evt) {
		try {
			provas = provaDao.listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void excluir() {
		try {
			provaDao.excluirPorId(prova.getId());
			prova = new Prova();
			provas = provaDao.listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void limpar() {
		prova = new Prova();
	}
	
	@SuppressWarnings("finally")
	public List<Template> completarTemplate() {
		List<Template> listaTemplates = new ArrayList<Template>();
		try {
			listaTemplates = templateDao.consultarPelaDescricao(template.getDescricao());
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro ao buscar o Template.");
		} finally{
			if (listaTemplates.size() == 0) {
				UtilFaces.addMensagemFaces("Template não encontrado.");
			}
			return listaTemplates;
		}
	}
	
	//Gets e Sets
	public Prova getProva() {
		return prova;
	}

	public List<Prova> getProvas() {
		return provas;
	}

	public void setProva(Prova prova) {
		this.prova = prova;
	}

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public Prova getProvaSelecionada() {
		return provaSelecionada;
	}

	public void setProvaSelecionada(Prova provaSelecionada) {
		try {
	         if(provaSelecionada != null){
	            Prova p = provaDao.consultar(provaSelecionada.getId());
	            this.provaSelecionada = p;
	         }else{
	            this.provaSelecionada = null;
	         }
	      } catch (Exception e) {
	         UtilFaces.addMensagemFaces(e);
	      }
	}

	public ItensProva getItensProva() {
		return itensProva;
	}

	public void setItensProva(ItensProva itensProva) {
		this.itensProva = itensProva;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}
	
}
