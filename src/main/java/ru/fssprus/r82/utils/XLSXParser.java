package ru.fssprus.r82.utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ru.fssprus.r82.entity.Answer;
import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.service.QuestionService;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class XLSXParser {

	private QuestionLevel level;
	private File file;
	private Set<Specification> specs = new HashSet<Specification>();

	public XLSXParser(QuestionLevel level, String specName, File file) {
		this.level = level;
		this.file = file;

		Specification spec = new Specification();
		spec.setName(specName);

		specs.add(spec);
	}

	public void saveQuestionsToDB(HashSet<Question> questions) {
		QuestionService qService = new QuestionService();
		qService.updateSet(questions);
	}
	
	public HashSet<Question> parse() {

		XSSFWorkbook wb = null;
		try {
			OPCPackage pkg = OPCPackage.open(file);
			wb = new XSSFWorkbook(pkg);
		} catch (IOException | InvalidFormatException e) {
			e.printStackTrace();
		}

		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> it = sheet.iterator();

		HashSet<Question> questions = new HashSet<Question>();

		int currRow = 0;
		while (it.hasNext()) {
			if (currRow == 0) {
				it.next();
				currRow++;
				continue;
			}
			currRow++;

			Row row = it.next();
			Iterator<Cell> cells = row.iterator();
			Question question = new Question();
			HashSet<Answer> answers = new HashSet<Answer>();
			Set<QuestionLevel> levels = new HashSet<QuestionLevel>();
			levels.add(level);
			question.setLevels(levels);

			while (cells.hasNext()) {
				Cell cell = cells.next();

				// 2 индекс - формулировка вопроса
				if (cell.getColumnIndex() == 2) {
					String currTitle = (String) cell.getStringCellValue();
					if (currTitle.isEmpty())
						break;
					question.setTitle(currTitle);
				}

				// Начиная с 3 индекса идут ответы и их правильность
				if (cell.getColumnIndex() >= 3) {
					Answer answer = new Answer();
					if ((cell.getColumnIndex()) % 2 == 1) {
						String currAnswer = cell.getStringCellValue();
						if (!currAnswer.isEmpty()) {
							answer = new Answer();
							answer.setTitle(currAnswer);
							answer.setQuestion(question);
						} else {
							break;
						}
					}
					cell = cells.next();

					if (!(answer == null)) {
						boolean isAnswerCorrect = "1".equals(String.valueOf((int) cell.getNumericCellValue()));
						answer.setIsCorrect(isAnswerCorrect);
					}
					answers.add(answer);
				}

			}
			question.setAnswers(answers);
			question.setSpecifications(specs);

			// System.out.println(question);
			questions.add(question);
		}

		return questions;
	}

}
