package ru.fssprus.r82.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.fssprus.r82.dao.QuestionDao;
import ru.fssprus.r82.dao.impl.QuestionDatabaseDao;
import ru.fssprus.r82.entity.Answer;
import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.entity.Specification;

public class QuestionService {
	// TODO: autowired
	private QuestionDao questionDao = new QuestionDatabaseDao();

	public List<Question> getByName(int startPos, int endPos, String name) {
		return questionDao.getByTitle(startPos, endPos, name);
	}

	public List<Question> getByAnswer(int startPos, int endPos, Answer answer) {
		return questionDao.getByAnswer(startPos, endPos, answer);
	}

	public List<Question> getBySpecification(int startPos, int endPos, Specification spec) {
		return questionDao.getBySpecification(startPos, endPos, spec);
	}

	public List<Question> getByIDsList(Set<Long> list) {
		return questionDao.getByIds(list);
	}

	public int getItemsCountBySpecification(Specification spec) {
		return questionDao.countItemsBySpecification(spec);
	}

	public int getAmountOfItems() {
		return questionDao.getAmountOfItems();
	}

	public Question getById(Long id) {
		return questionDao.getById(id);
	}

	public List<Question> getAllBySpecification(Specification specification) {
		return questionDao.getAllBySpecification(specification);
	}

	public void save(Question questionToSave) {
		questionDao.add(questionToSave);
	}

	public List<Question> getByNameAndSpecification(String name, Specification spec) {
		return questionDao.getByNameAndSpecification(name, spec);
	}

	public List<Question> getByNameSpecificationAndLevel(String name, Set<Specification> specs,
			Set<QuestionLevel> lvls) {
		return questionDao.getByNameSpecificationAndLevel(name, specs, lvls);

	}

	public List<Question> getByName(String title) {
		return questionDao.getAllByTitle(title);
	}

	public void updateSet(HashSet<Question> questions) {
		for (Question question : questions)
			update(question);
	}

	public void update(Question question) {
		List<Question> questionsFound = questionDao.getAllByTitle(question.getTitle());
		// Если в БД уже есть вопрос с такой формулировкой
		if (questionsFound.size() > 0) {

			for (QuestionLevel lvl : questionsFound.get(0).getLevels()) {
				// Смотрим есть добавлена ли такая сложность для этого вопроса
				for (QuestionLevel level : question.getLevels())
					// Если добавлена - ничего не делаем
					if (level == lvl)
						break;
					// Если нет - добавляем сложность к вопросу и обновляем его
					else {
						questionsFound.get(0).getLevels().add(level);
						questionDao.update(questionsFound.get(0));
					}
			}
			// Если вопроса с такой формулировкой нет - сохраняем его в БД
		} else {
			SpecificationService sService = new SpecificationService();

			String specName = question.getSpecifications().iterator().next().getName().toString();

			Set<Specification> ss = new HashSet<Specification>(sService.getByName(specName));

			if (sService.getByName(specName).size() == 0) {
				Specification spec = new Specification();
				spec.setName(specName);
				ss.add(spec);
			}

			question.setSpecifications(ss);
			save(question);
		}
	}
}
