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
import com.github.pedron98.model.Tratamento;

public class TratamentoDAO implements TratamentoDAOIF, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;

	@Override
	@Transacional
	public void save(Tratamento tratamento) {
		try {
			em.persist(tratamento);
		}
		catch(RuntimeException ex) {			
			throw new DAOException("Erro ao tentar cadastrar o tratamento no banco de dados: "+ex.getMessage(),
					ErrorCode.INTERNAL_SERVER_ERROR.getCode());
		}
	}

	@Override
	@Transacional
	public void update(Tratamento tratamento) {
		try {
			Tratamento t = em.find(Tratamento.class, tratamento.getId());
			t.setNome(tratamento.getNome());
			
			em.merge(tratamento);
		}
		catch(NullPointerException ex) {
			throw new DAOException("O tratamento informado não foi encontrado: "+ex.getMessage(),
					ErrorCode.NOT_FOUND.getCode());
		}
		catch(RuntimeException ex) {
			throw new DAOException("Erro ao tentar atualizar o tratamento no banco de dados: "+ex.getMessage(),
					ErrorCode.INTERNAL_SERVER_ERROR.getCode());
		}
	}

	@Override
	@Transacional
	public void remove(Tratamento tratamento) {
		try {
			Tratamento t = em.find(Tratamento.class, tratamento.getId());
			em.remove(t);
		}
		catch(IllegalArgumentException ex) {
			throw new DAOException("O tratamento informado não foi encontrado: "+ex.getMessage(),
					ErrorCode.NOT_FOUND.getCode());
		}
		catch(RuntimeException ex) {
			throw new DAOException("Erro ao tentar remover o tratamento do banco de dados: "+ex.getMessage(),
					ErrorCode.INTERNAL_SERVER_ERROR.getCode());
		}
		
	}

	@Override
	public List<Tratamento> findAll() {
		try {
			List<Tratamento> tratamentos = new ArrayList<Tratamento>();
			String jpql = "FROM Tratamento";
			TypedQuery<Tratamento> query = em.createQuery(jpql, Tratamento.class);
			tratamentos = query.getResultList();
			return tratamentos;
		}
		catch(RuntimeException ex) {
			throw new DAOException("Erro ao tentar buscar todos os tratamentos no banco de dados: "+ex.getMessage(),
					ErrorCode.INTERNAL_SERVER_ERROR.getCode());
		}	
	}

	@Override
	public Tratamento findById(Long id) {
		if (id <= 0) {
			throw new DAOException("O id informado deve ser maior do que zero!", ErrorCode.BAD_REQUEST.getCode());
		}
		try {
			return em.find(Tratamento.class, id);
		}
		catch(RuntimeException ex) {
			throw new DAOException("Erro ao tentar buscar o tratatmento no banco de dados: "+ex.getMessage(),
					ErrorCode.INTERNAL_SERVER_ERROR.getCode());
		}
	}

	@Override
	public Tratamento findTratamentoFetchMedicamentos(Long id) {
		if (id <= 0) {
			throw new DAOException("O id informado deve ser maior do que zero!", ErrorCode.BAD_REQUEST.getCode());
		}
		try {
			String jpql = "SELECT t FROM Tratamento t LEFT JOIN FETCH t.medicamentos WHERE t.id = :id";
			TypedQuery<Tratamento> query = em.createQuery(jpql, Tratamento.class);
			return query.setParameter("id", id).getSingleResult();
		}
		catch(RuntimeException ex) {
			throw new DAOException("Erro ao tentar buscar todos os tratamentos com carregamento de medicamentos no banco de dados: "+ex.getMessage(),
					ErrorCode.INTERNAL_SERVER_ERROR.getCode());
		}
		
	}

}
