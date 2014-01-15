package com.andersonlfeitosa.mavendependencyanalyzer.dao;

import java.io.Serializable;

public interface IDao<T> {
	
	void add(T e);
	
	void update(T e);
	
	void delete(T e);
	
	void list(T e);
	
	T get(Serializable key);

}
