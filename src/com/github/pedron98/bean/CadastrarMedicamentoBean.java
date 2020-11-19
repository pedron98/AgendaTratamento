package com.github.pedron98.bean;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.github.pedron98.dao.MedicamentoDAO;
import com.github.pedron98.dao.TratamentoDAO;
import com.github.pedron98.enums.TipoMedicamento;
import com.github.pedron98.model.Medicamento;
import com.github.pedron98.model.Tratamento;

@Named
@ViewScoped
public class CadastrarMedicamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Medicamento medicamento;
	private MedicamentoDAO medicamentoDAO;
	private Tratamento tratamento;
	private TratamentoDAO tratamentoDAO;
	private Long tratamentoId;
	private Long usuarioId;
	private String tipoMedicamento;
	
	private Long medicamentoId;
	private String horarioMedicamento;

	public void pegarTipoMedicamento() {
		TipoMedicamento tipo = null;
		if (tipoMedicamento.equals("FITOTERAPICO")) {
			tipo = TipoMedicamento.FITOTERAPICO;
		}
		else {
			if (tipoMedicamento.equals("ALOPATICO")) {
				tipo = TipoMedicamento.ALOPATICO;
			}
			else {
				if (tipoMedicamento.equals("HOMEOPATICO")) {
					tipo = TipoMedicamento.HOMEOPATICO;
				}
				else {
					if (tipoMedicamento.equals("SIMILAR")) {
						tipo = TipoMedicamento.SIMILAR;
					}
					else {
						if (tipoMedicamento.equals("GENERICO")) {
							tipo = TipoMedicamento.GENERICO;
						}
						else {
							if (tipoMedicamento.equals("REFERENCIA")) {
								tipo = TipoMedicamento.REFERENCIA;
							}
							else {
								if (tipoMedicamento.equals("MANIPULADO")) {
									tipo = TipoMedicamento.MANIPULADO;
								}
							}
						}
					}
				}
			}
		}
		medicamento.setTipoMedicamento(tipo);
	}
	
	public void pegarTratamentoEVerificarMedicamento(ComponentSystemEvent e) {			
		if (medicamentoId != 0) {
			medicamento = medicamentoDAO.findById(medicamentoId);
		}
		else {
			tratamento = tratamentoDAO.findById(tratamentoId);
			medicamento.setTratamento(tratamento);
		}
	}
	
	public void parseHorarioToLocalTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		TemporalAccessor parse = formatter.parse(horarioMedicamento);
		LocalTime from = LocalTime.from(parse);
		medicamento.setHorario(from);
	}
	
	@PostConstruct
	public void init() {
		medicamento = new Medicamento();
		medicamentoDAO = new MedicamentoDAO();
		tratamentoDAO = new TratamentoDAO();
		tratamento = new Tratamento();
	}

	public Medicamento getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}

	public String getTipoMedicamento() {	
		return tipoMedicamento;
	}

	public void setTipoMedicamento(String tipoMedicamento) {
		this.tipoMedicamento = tipoMedicamento;
	} 

	public Long getTratamentoId() {
		return tratamentoId;
	}

	public void setTratamentoId(Long tratamentoId) {
		this.tratamentoId = tratamentoId;
	} 

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Tratamento getTratamento() {
		return tratamento;
	}

	public void setTratamento(Tratamento tratamento) {
		this.tratamento = tratamento;
	} 

	public Long getMedicamentoId() {
		return medicamentoId;
	}

	public void setMedicamentoId(Long medicamentoId) {
		this.medicamentoId = medicamentoId;
	} 

	public String getHorarioMedicamento() {
		return horarioMedicamento;
	} 

	public void setHorarioMedicamento(String horarioMedicamento) {
		this.horarioMedicamento = horarioMedicamento;
	}

	public String save() {
		if (medicamentoId != 0) {
			medicamentoDAO.update(medicamento);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Medicamento atualizado com sucesso!", ""));
			return null;
		}
		else {
			if (tratamentoId == 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "É necessário ter um tratamento cadastrado para este medicamento!", ""));
				return null;
			}
			else {				
				medicamentoDAO.save(medicamento);
				return "detalhesTratamento?faces-redirect=true"+
				"id_tratamento="+tratamento.getId();
			}
		}
	}
	
}
