package com.github.pedron98.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.github.pedron98.annotation.Transacional;
import com.github.pedron98.enums.ErrorCode;
import com.github.pedron98.exception.DAOException;
import com.github.pedron98.model.Medicamento;

public class MedicamentoDAO implements MedicamentoDAOIF, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	@Override
	@Transacional
	public void save(Medicamento medicamento) {
		try {
			em.persist(medicamento);
		}
		catch(RuntimeException ex) {
			throw new DAOException("Erro ao tentar cadastrar o medicamento no banco de dados: "+ex.getMessage(),
					ErrorCode.INTERNAL_SERVER_ERROR.getCode());
		}
		
	}
	
	@Override
	@Transacional
	public void update(Medicamento medicamento) {
		try {
			Medicamento m = em.find(Medicamento.class, medicamento.getId());
			m.setNome(medicamento.getNome());
			em.merge(medicamento);
		}
		catch(NullPointerException ex) {
			throw new DAOException("O medicamento informado não foi encontrado: "+ex.getMessage(),
					ErrorCode.NOT_FOUND.getCode());
		}
		catch(RuntimeException ex) {
			throw new DAOException("Erro ao tentar atualizar o medicamento no banco de dados: "+ex.getMessage(),
					ErrorCode.INTERNAL_SERVER_ERROR.getCode());
		}
	}
	
	@Override
	@Transacional
	public void remove(Medicamento medicamento) {
		try {
			Medicamento m = em.find(Medicamento.class, medicamento.getId());
			em.remove(m);
		}
		catch(IllegalArgumentException ex) {
			throw new DAOException("O medicamento informado não foi encontrado: "+ex.getMessage(),
					ErrorCode.NOT_FOUND.getCode());
		}
		catch(RuntimeException ex) {
			throw new DAOException("Erro ao tentar remover o medicamento do banco de dados: "+ex.getMessage(),
					ErrorCode.INTERNAL_SERVER_ERROR.getCode());
		}
		
	}
	
	@Override
	public List<Medicamento> findAll() {
		try {			
			List<Medicamento> medicamentos = new ArrayList<Medicamento>();
			String jpql = "FROM Medicamento"; 
			TypedQuery<Medicamento> query = em.createQuery(jpql, Medicamento.class);
			medicamentos = query.getResultList();
			return medicamentos;
		}
		catch(RuntimeException ex) {
			throw new DAOException("Erro ao tentar buscar todos os medicamentos do banco de dados: "+ex.getMessage(),
					ErrorCode.INTERNAL_SERVER_ERROR.getCode());
		}
	}
	@Override
	public Medicamento findById(Long id) {
		Medicamento m = null;
		if(id <= 0) {
			throw new DAOException("O id informado deve ser maior do que zero!", ErrorCode.BAD_REQUEST.getCode());
		}
		try {
			m = em.find(Medicamento.class, id);
		}
		catch(RuntimeException ex) {
			throw new DAOException("Erro ao tentar buscar o tratamendo no banco de dados: "+ex.getMessage(),
					ErrorCode.INTERNAL_SERVER_ERROR.getCode());
		}
		if(m == null) {
			throw new DAOException("O medicamento informado não foi encontrado!", ErrorCode.NOT_FOUND.getCode());
		}
		return m;
	}
}
