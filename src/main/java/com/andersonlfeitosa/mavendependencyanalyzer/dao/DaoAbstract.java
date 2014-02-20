package com.andersonlfeitosa.mavendependencyanalyzer.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public abstract class DaoAbstract<T> implements IDao<T> {
	
	private EntityManager entityManager;
	
	public DaoAbstract() {
		entityManager = PersistenceUtil.getInstance().getEntityManager();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public EntityTransaction getTransaction() {
		return entityManager.getTransaction();
	}
	
}
