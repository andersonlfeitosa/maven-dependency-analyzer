package com.andersonlfeitosa.mavendependencyanalyzer.dao;

import java.io.Serializable;
import java.util.List;

public interface IDao<T> {
	
	void save(T e);
	
	void update(T e);
	
	void delete(T e);
	
	List<T> list();
	
	T get(Serializable key);

	T find(T filter);

}
