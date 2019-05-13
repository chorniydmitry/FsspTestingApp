package ru.fssprus.r82.dao;

import java.util.List;

import ru.fssprus.r82.entity.Model;

public interface ItemDao<T extends Model> {

	public List<T> getAll();

	public List<T> getAll(int limit);

	public List<T> getAll(int start, int end);

	public T getById(Long id);

	public void add(T model);

	public void update(T model);

	public void remove(T model);

	public void remove(Long id);

}
