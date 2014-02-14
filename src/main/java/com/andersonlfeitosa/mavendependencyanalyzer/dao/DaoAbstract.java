package com.andersonlfeitosa.mavendependencyanalyzer.dao;

import java.io.Closeable;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public abstract class DaoAbstract<T> implements IDao<T>, Closeable {
	
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
	
	@Override
	public void close() throws IOException {
		entityManager.close();
	}
	
}
