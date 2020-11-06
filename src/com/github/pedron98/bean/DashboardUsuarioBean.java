package com.github.pedron98.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.github.pedron98.dao.TratamentoDAO;
import com.github.pedron98.dao.UsuarioDAO;
import com.github.pedron98.model.Tratamento;
import com.github.pedron98.model.Usuario;

@Named
@ViewScoped
public class DashboardUsuarioBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private List<Tratamento> tratamentos;
	private TratamentoDAO tratamentoDAO;
	private UsuarioDAO usuarioDAO;
	private Usuario usuario;
	private int usuarioId;
	
	public void pegarUsuarioeTratamentos() {			
		usuario = usuarioDAO.findUsuarioFetchTratamentos(new Long(usuarioId));
		tratamentos = usuario.getTratamentos();
	}
	
	public void remover(Tratamento t) {
		tratamentoDAO.remove(t);
		tratamentos.remove(t);
	}

	@PostConstruct
	public void init() {
		usuario = new Usuario();
		usuarioDAO = new UsuarioDAO();
		tratamentoDAO = new TratamentoDAO();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getUsuarioId() {
		return usuarioId;
	} 

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	} 

	public List<Tratamento> getTratamentos() {
		return tratamentos;
	} 
}
