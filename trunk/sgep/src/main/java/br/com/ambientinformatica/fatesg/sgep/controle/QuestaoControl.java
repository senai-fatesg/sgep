package br.com.ambientinformatica.fatesg.sgep.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import org.primefaces.context.RequestContext;
import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.fatesg.api.entidade.Colaborador;
import br.com.ambientinformatica.fatesg.sgep.entidade.AlternativaQuestao;
import br.com.ambientinformatica.fatesg.sgep.entidade.EnumAlternativa;
import br.com.ambientinformatica.fatesg.sgep.entidade.EnumDificuldade;
import br.com.ambientinformatica.fatesg.sgep.entidade.EnumEstado;
import br.com.ambientinformatica.fatesg.sgep.entidade.QuestaoTemplate;
import br.com.ambientinformatica.fatesg.sgep.persistencia.ColaboradorDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.QuestaoTemplateDao;

@Controller("QuestaoControl")
@Scope("conversation")
public class QuestaoControl implements Serializable {

	private static final long serialVersionUID = 1L;


	private List<QuestaoTemplate> questoes = new ArrayList<QuestaoTemplate>();

	private Colaborador professor = new Colaborador();

	private List<Colaborador> professores = new ArrayList<Colaborador>();

	private QuestaoTemplate questaoSelecionada = new QuestaoTemplate();

	private AlternativaQuestao item = new AlternativaQuestao();

	private boolean rdbUsrLogadoSelecionado;
	
	private boolean isAlternativaEdicao = false;
	
	private String nomeProfessor;
	
	private static final int CAPACIDADE_MAXIMA_ALTERNATIVAS = 5;
	
	@Autowired
	private QuestaoTemplateDao questaoDao;
	
	@Autowired
	private ColaboradorDao colaboradorDao;

	@PostConstruct
	public void init() {
		this.listar();
	}

	public void confirmar() {
		try {
			
			if(this.isQuestaoValida(questaoSelecionada.getQuestao().getAlternativas().size())){	
				//if(rdbUsrLogadoSelecionado){
					
				//}
				
				//verifica se professor já está cadastrado na base do SGEP
				if(!isProfessorJaCadastrado(questaoSelecionada.getQuestao().getProfessor())){
					colaboradorDao.alterar(questaoSelecionada.getQuestao().getProfessor());
				}
				
				questaoDao.alterar(questaoSelecionada);
				this.listar();
				questaoSelecionada = new QuestaoTemplate();
				RequestContext.getCurrentInstance().execute("fecharDlgQuestao();");
			}
		} catch (Exception e) {
			colaboradorDao.getEntityManager().getTransaction().rollback();
			questaoDao.getEntityManager().getTransaction().rollback();
			UtilFaces.addMensagemFaces(e);
		}
	}
	
	public boolean verificarSalvarDadosPreenchidosPerdidos(){
		return isAlternativaEdicao() || this.item.getDescricao() != null;
	}
	
	private boolean isProfessorJaCadastrado(Colaborador colaborador) {
		return colaboradorDao.consultar(colaborador.getId()) != null;
	}

	public void listar() {
		try {
			questoes = questaoDao.listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void excluir() {
		try {
			questaoDao.excluirPorId(questaoSelecionada.getIdQuestaoTemplate());
			questaoSelecionada = new QuestaoTemplate();
			questoes = questaoDao.listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void carregarQuestao() {
		try {
			this.questaoSelecionada = questaoDao.carregarQuestao(questaoSelecionada);
		} catch (Exception e) {
			
			UtilFaces.addMensagemFaces("Falha ao carregar objetos");
		}
	}
	
	
	public List<Colaborador> completarColaboradores(String nome) {
		List<Colaborador> colaboradores = colaboradorDao.listarPorNome(nome); 
		return colaboradores;
	}
	
	public void adicionarItem() {
		try {
			if (isAlternativaValida()) {
				item.setOrdem(questaoSelecionada.getQuestao()
						.getAlternativas(), this.isAlternativaEdicao);
				questaoSelecionada.getQuestao().addItem(item, isAlternativaEdicao);
			}
		} catch (Exception e) {
			UtilFaces
					.addMensagemFaces("Não foi possivel adicionar a alternativa.\n"
							+ e.getMessage());
		} finally {
			item = new AlternativaQuestao();
		}
	}

	private boolean isAlternativaValida() {
		if(this.isCapacidadeMaximaPreenchida(questaoSelecionada.getQuestao().getAlternativas().size())){
			UtilFaces
			.addMensagemFaces("Atenção!\n Capacidade maxima de itens alcançada.");
			
			return false;
		};
		return true;
	}
	
	public boolean isCapacidadeMaximaPreenchida(int quantidadeQuestao){
		return quantidadeQuestao == CAPACIDADE_MAXIMA_ALTERNATIVAS && !isAlternativaEdicao;
	}
	
	private boolean isQuestaoValida(int quantidadeQuestao){
		if(!this.isCamposObrigatoriosPreenchidos()){
			UtilFaces.addMensagemFaces("Campo(s) obrigatório(s) não informado(s)!");
			return false;
		}
		
		if(!this.isCapacidadeMaximaPreenchida(quantidadeQuestao)){
			UtilFaces.addMensagemFaces("É necessário adicionar "+ CAPACIDADE_MAXIMA_ALTERNATIVAS +" para a questão!");
			return false;
		}
		return true;
	}
	
	private boolean isCamposObrigatoriosPreenchidos(){
		return questaoSelecionada.getQuestao().getEnunciado() != ""
				&& questaoSelecionada.getQuestao().getAssunto() != ""
				&& questaoSelecionada.getQuestao().getEstado().hashCode() > 0 
				&& questaoSelecionada.getQuestao().getDificuldade().hashCode() > 0
				&& questaoSelecionada.getQuestao().getResposta().hashCode() > 0;
	}
	
	public void editarItem(AlternativaQuestao alternativa) {
		try {
			this.item = alternativa;
			this.isAlternativaEdicao = true;
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void removerItem(AlternativaQuestao alternativa) {
		try {
			this.questaoSelecionada.getQuestao().removerItem(alternativa);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public List<SelectItem> getEstados() {
		return UtilFaces.getListEnum(EnumEstado.values());
	}

	public List<SelectItem> getDificuldades() {
		return UtilFaces.getListEnum(EnumDificuldade.values());
	}

	public void limpar() {
		questaoSelecionada = new QuestaoTemplate();
		item = new AlternativaQuestao();
	}

	public void limparItem() {
		item = new AlternativaQuestao();
	}

	public List<QuestaoTemplate> getQuestoes() {
		return questoes;
	}

	public Colaborador getProfessor() {
		return professor;
	}

	public void setProfessor(Colaborador professor) {
		this.professor = professor;
	}

	public List<Colaborador> getProfessores() {
		return professores;
	}

	public void setProfessores(List<Colaborador> professores) {
		this.professores = professores;
	}

	public AlternativaQuestao getItem() {
		return item;
	}

	public void setItem(AlternativaQuestao item) {
		this.item = item;
	}

	public QuestaoTemplate getQuestaoSelecionada() {
		return questaoSelecionada;
	}

	public void setQuestaoSelecionada(QuestaoTemplate questaoSelecionada) {
		this.questaoSelecionada = questaoSelecionada;
	}

	public String getNomeProfessor() {
		return nomeProfessor;
	}

	public void setNomeProfessor(String nomeProfessor) {
		this.nomeProfessor = nomeProfessor;
	}

	public boolean isRdbUsrLogadoSelecionado() {
		return rdbUsrLogadoSelecionado;
	}

	public void setRdbUsrLogadoSelecionado(boolean rdbUsrLogadoSelecionado) {
		this.rdbUsrLogadoSelecionado = rdbUsrLogadoSelecionado;
	}

	public List<SelectItem> getAlternativas() {
		return UtilFaces.getListEnum(EnumAlternativa.values());
	}

	public boolean isAlternativaEdicao() {
		return isAlternativaEdicao;
	}

	public void setAlternativaEdicao(boolean isAlternativaEdicao) {
		this.isAlternativaEdicao = isAlternativaEdicao;
	}


	

}
