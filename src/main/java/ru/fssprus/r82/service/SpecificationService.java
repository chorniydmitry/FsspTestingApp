package ru.fssprus.r82.service;

import java.util.Collection;
import java.util.List;

import ru.fssprus.r82.dao.SpecificationDao;
import ru.fssprus.r82.dao.impl.SpecifiactionDatabaseDao;
import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.entity.Specification;

public class SpecificationService {
	
	//TODO: autowired
	private SpecificationDao specificationDao = new SpecifiactionDatabaseDao();
	
	public List<Specification> getByName(int startPos, int endPos, String title) {
		return specificationDao.getByTitle(startPos, endPos, title);
	}

	public List<Specification> getByQuestion(int startPos, int endPos, Question question) {
		return specificationDao.getByQuestion(startPos, endPos, question);
	}

	public List<Specification> getAll() {
		return specificationDao.getAll();
	}
	
	public Specification getByID(Long id) {
		return specificationDao.getById(id);
	}
	public void update(Specification specificationToUpdate) {
		specificationDao.update(specificationToUpdate);
	}

	public List<Specification> getByName(String specName) {
		return specificationDao.getByTitle(-1, -1, specName);
	}
	
	public void save(Specification spec) {
		specificationDao.add(spec);
	}
	
}
	
