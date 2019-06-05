package ru.fssprus.r82.service;

import java.util.ArrayList;
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

	public Question getById(Long id) {
		return questionDao.getById(id);
	}

	public List<Question> getByName(int startPos, int endPos, String name) {
		return questionDao.getByTitle(startPos, endPos, name);
	}

	public List<Question> getByName(String title) {
		return questionDao.getByTitle(-1, -1, title);
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

	public List<Question> getAllBySpecification(Specification specification) {
		return questionDao.getBySpecification(-1, -1, specification);
	}

	public List<Question> getAllBySpecificationAndLevel(Specification specification, QuestionLevel level) {
		return questionDao.getBySpecificationAndLevel(-1, -1, specification, level);
	}

	public List<Question> getByNameAndSpecification(String name, Specification spec) {
		return questionDao.getByNameAndSpecification(name, spec);
	}

	public List<Question> getByNameSpecificationAndLevel(String name, Set<Specification> specs,
			Set<QuestionLevel> lvls) {
		return questionDao.getByNameSpecificationAndLevel(name, specs, lvls);
	}
	
	public int countBySpecificationAndLevel(Specification spec, QuestionLevel level) {
		return questionDao.countBySpecificationAndLevel(spec, level);
	}

	public List<Question> getAll(int startPos, int endPos) {
		return questionDao.getAll(startPos, endPos);
	}

	public void updateSet(HashSet<Question> questions) {
		for (Question question : questions)
			update(question);
	}

	public void save(Question questionToSave) {
		SpecificationService service = new SpecificationService();

		if (service.getByName(questionToSave.getSpecification().getName()).size() == 0)
			service.save(questionToSave.getSpecification());
		
		questionDao.add(questionToSave);
	}

	public void update(Long id, Question questionModified) {
		Question question = questionDao.getById(id);
		AnswerService ansService = new AnswerService();
		if (question == null)
			question = new Question();
				
		question.setLevels(questionModified.getLevels());
		question.getAnswers().forEach((n)-> ansService.delete(n));
		question.setAnswers(new HashSet<Answer>(questionModified.getAnswers()));
		question.setSpecification(questionModified.getSpecification());
		question.setTitle(questionModified.getTitle());

		questionDao.update(question);
	}

	public void update(Question question) {
		List<Question> questionsFound = questionDao.getByTitle(-1, -1, question.getTitle());

		int AnsOverlaps = 0;
		// Если в БД уже есть вопрос с такой формулировкой
		if (questionsFound.size() > 0) {

			for (Answer answerFound : questionsFound.get(0).getAnswers()) {
				for (Answer answerQuest : question.getAnswers()) {
					if (answerQuest.getTitle().equals(answerFound.getTitle()))
						AnsOverlaps++;
				}
			}
		}

		if (AnsOverlaps == question.getAnswers().size()) {
			ArrayList<QuestionLevel> levels = new ArrayList<QuestionLevel>();
			levels.addAll(questionsFound.get(0).getLevels());
			for (QuestionLevel lvl : levels) {
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
			String specName = question.getSpecification().getName();

			// Общие вопросы одинаковы для всех сложностей
			if (specName.toUpperCase().equals("ОБЩИЕ")) {
				HashSet<QuestionLevel> levels = new HashSet<QuestionLevel>();
				levels.add(QuestionLevel.Базовый);
				levels.add(QuestionLevel.Продвинутый);
				levels.add(QuestionLevel.Стандартный);
				levels.add(QuestionLevel.Резерв);
				question.setLevels(levels);
			}

			Specification spec = null;
			if (sService.getByName(specName).size() > 0)
				spec = sService.getByName(specName).get(0);
			else {
				spec = new Specification();
				spec.setName(specName);
			}

			question.setSpecification(spec);
			save(question);
		}
	}

	public int countAll() {
		return questionDao.getAmountOfItems();

	}

	public List<Question> getAllByNameSpecListLvlListAndId(String name, Set<Specification> specs,
			Set<QuestionLevel> levels, Long id) {
		return questionDao.getByNameSpecListLvlListAndID(-1, -1, name, specs, levels, id);

	}

	public List<Question> getByNameSpecListLvlListAndId(int startPos, int endPos, String name, Set<Specification> specs,
			Set<QuestionLevel> levels, Long id) {
		return questionDao.getByNameSpecListLvlListAndID(startPos, endPos, name, specs, levels, id);

	}

	public int countByNameSpecListLvlListAndId(String name, Set<Specification> specs, Set<QuestionLevel> levels,
			Long id) {
		return questionDao.countByNameSpecListLvlListAndID(name, specs, levels, id);
	}

	public void delete(Question question) {
		questionDao.remove(question);
	}

}
