package ru.fssprus.r82.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import ru.fssprus.r82.entity.Answer;
import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.service.QuestionService;

public class TestFromODSLoader {

	public void doOpenODS() {
		File csv = selectODSFileToOpen();

		if (csv == null)
			return;

		HashSet<Question> questions;
		try {
			questions = loadQuestionFromFile(csv);
			saveQuestionsToDB(questions);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private HashSet<Question> loadQuestionFromFile(File file) throws IOException {
		final Sheet sheet = SpreadSheet.createFromFile(file).getSheet(0);

		String fileName = file.getName();
		String spec = fileName.substring(0, (fileName.length() - 4));

		HashSet<Question> questions = getQuestionsFromSheet(sheet, spec);

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
			

			HashSet<Answer> answers = new HashSet<Answer>();
			// Собираем ответы их правильность (подразумевая, что максимально возможно
			// количество=5)
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
		HashSet<Question> questionsToSave = new HashSet<Question>();
		for (Question question : questions) {
			String title = question.getTitle();
			if(qService.getByName(0, 5, title).size() == 0)
				questionsToSave.add(question);
		}
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			for (Question question : questionsToSave) {
				Transaction tx = session.beginTransaction();
				session.save(question);
				tx.commit();
			}
			session.close();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	private File selectFileOption(int option, JFileChooser fileChooser, String extension) {
		File file = null;

		if (option == JFileChooser.APPROVE_OPTION) {
			if (fileChooser.getSelectedFile().toString().endsWith(extension)
					|| fileChooser.getSelectedFile().toString().endsWith(extension.toUpperCase()))
				file = fileChooser.getSelectedFile();
			else
				file = new File(fileChooser.getSelectedFile() + extension);
		}
		return file;
	}

	private File selectODSFileToOpen() {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("ODS FILES", "ods", "ods");
		fileChooser.setFileFilter(filter);
		return selectFileOption(fileChooser.showOpenDialog(null), fileChooser, ".ods");
	}

}
