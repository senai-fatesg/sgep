package br.com.ambientinformatica.fatesg.sgep.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import br.com.ambientinformatica.fatesg.api.entidade.Colaborador;
import br.com.ambientinformatica.fatesg.api.entidade.Disciplina;

@Entity
public class Questao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "questao_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "questao_seq", sequenceName = "questao_seq", allocationSize = 1, initialValue = 1)
	private Long id;

	private String enunciado;

	private String assunto;

	private char resposta;

	@ManyToOne
	private Colaborador professor;

	@ManyToOne
	private Disciplina disciplina;

	@Enumerated(EnumType.STRING)
	private EnumEstado estado;

	@Enumerated(EnumType.STRING)
	private EnumDificuldade dificuldade;

	private Boolean objetiva = true;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "questao_id")
	private List<Item> itens = new ArrayList<Item>();

	// Metodos
	public void addItem(Item item) throws Exception{
			if (!itens.contains(item)) {
				this.itens. add(item);
			}else{
				throw new Exception("Questão já contem este item!");
			}
		
	}

	public void removerItem(Item item) {
		if (itens.contains(item)) {
			this.itens.remove(item);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public char getResposta() {
		return resposta;
	}

	public void setResposta(char resposta) {
		this.resposta = resposta;
	}

	public Colaborador getProfessor() {
		return professor;
	}

	public void setProfessor(Colaborador professor) {
		this.professor = professor;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public EnumEstado getEstado() {
		return estado;
	}

	public void setEstado(EnumEstado estado) {
		this.estado = estado;
	}

	public EnumDificuldade getDificuldade() {
		return dificuldade;
	}

	public void setDificuldade(EnumDificuldade dificuldade) {
		this.dificuldade = dificuldade;
	}

	public Boolean getObjetiva() {
		return objetiva;
	}

	public void setObjetiva(Boolean objetiva) {
		this.objetiva = objetiva;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assunto == null) ? 0 : assunto.hashCode());
		result = prime * result
				+ ((dificuldade == null) ? 0 : dificuldade.hashCode());
		result = prime * result
				+ ((disciplina == null) ? 0 : disciplina.hashCode());
		result = prime * result
				+ ((enunciado == null) ? 0 : enunciado.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((itens == null) ? 0 : itens.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Questao other = (Questao) obj;
		if (assunto == null) {
			if (other.assunto != null)
				return false;
		} else if (!assunto.equals(other.assunto))
			return false;
		if (dificuldade != other.dificuldade)
			return false;
		if (disciplina == null) {
			if (other.disciplina != null)
				return false;
		} else if (!disciplina.equals(other.disciplina))
			return false;
		if (enunciado == null) {
			if (other.enunciado != null)
				return false;
		} else if (!enunciado.equals(other.enunciado))
			return false;
		if (estado != other.estado)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (itens == null) {
			if (other.itens != null)
				return false;
		} else if (!itens.equals(other.itens))
			return false;
		return true;
	}
}