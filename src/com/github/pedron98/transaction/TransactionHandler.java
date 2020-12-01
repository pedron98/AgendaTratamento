package com.github.pedron98.transaction;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

import com.github.pedron98.annotation.Transacional;

@Interceptor
@Transacional
public class TransactionHandler {

	@Inject
	private EntityManager em;
	
	@AroundInvoke
	public Object invokeAction(InvocationContext context) {
		em.getTransaction().begin();
		
		try {			
			Object result = context.proceed();
			em.getTransaction().commit();
			
			return result;
		}
		catch(Exception ex) {
			em.getTransaction().rollback();
			throw new RuntimeException();
		}
	}
	
}
