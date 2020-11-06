package com.github.pedron98.dao;

import com.github.pedron98.model.Tratamento;

public interface TratamentoDAOIF extends GenericDAOIF<Tratamento> {

	Tratamento findTratamentoFetchMedicamentos(Long id);
	
}
