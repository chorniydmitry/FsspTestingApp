package ru.fssprus.r82.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.jopendocument.dom.template.statements.ForEach;

import ru.fssprus.r82.entity.Answer;
import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.service.QuestionService;
import ru.fssprus.r82.service.SpecificationService;

public class TestFromODSLoader {
	private QuestionLevel level;
	private String specName;
	private File file;

	public TestFromODSLoader(QuestionLevel level, String specName, File file) {
		this.level = level;
		this.specName = specName;
		this.file = file;
	}

	public void loadQuestions() {
		if (file == null)
			return;

		HashSet<Question> questions;
		try {
			questions = loadQuestionFromFile(file);
			saveQuestionsToDB(questions);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private HashSet<Question> loadQuestionFromFile(File file) throws IOException {
		final Sheet sheet = SpreadSheet.createFromFile(file).getSheet(0);

		HashSet<Question> questions = getQuestionsFromSheet(sheet, specName);

		return questions;
	}

	private HashSet<Question> getQuestionsFromSheet(Sheet sheet, String specification) {
		HashSet<Question> questions = new HashSet<Question>();
		Set<Specification> specs = new HashSet<Specification>();
		Specification spec = new Specification();
		spec.setName(specification);

		specs.add(spec);

		int currRow = 1;
		while (true) {
			String currTitle = (String) sheet.getCellAt(2, currRow).getValue();
			// Если вопрос пустой, выходим из цыкла
			if (currTitle.isEmpty()) {
				break;
			}

			Question question = new Question();
			question.setTitle(currTitle);

			Set<QuestionLevel> levels = new HashSet<QuestionLevel>();
			levels.add(level);
			question.setLevels(levels);

			HashSet<Answer> answers = new HashSet<Answer>();
			// Собираем ответы их правильность (подразумевая, что максимально возможно
			// количество = 5)
			for (int i = 0; i <= 5; i++) {
				int ansIndex = i * 2 + 3;
				String currAnswer = sheet.getCellAt(ansIndex, currRow).getValue().toString();
				boolean isAnswerCorrect = "1".equals(sheet.getCellAt(ansIndex + 1, currRow).getValue().toString());
				// добавляем ответ только если он не пустой

				if (!currAnswer.isEmpty()) {
					Answer answer = new Answer();
					answer.setTitle(currAnswer);
					answer.setIsCorrect(isAnswerCorrect);
					answers.add(answer);
					answer.setQuestion(question);
				}
			}
			question.setAnswers(answers);
			question.setSpecifications(specs);

			questions.add(question);
			currRow++;
		}
		return questions;
	}

	private void saveQuestionsToDB(HashSet<Question> questions) {
		QuestionService qService = new QuestionService();
		qService.updateSet(questions);
	}

}
