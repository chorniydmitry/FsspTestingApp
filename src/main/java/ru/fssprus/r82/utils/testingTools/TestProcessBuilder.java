package ru.fssprus.r82.utils.testingTools;

import java.util.List;

import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.entity.User;

public class TestProcessBuilder {
	
	private List<Specification> specifications;
	private QuestionLevel questionLevel;
	private List<Question> questions;
	
	public TestProcessBuilder(List<Specification> specifications, QuestionLevel questionLevel, User user) {
		this.specifications = specifications;
		this.questionLevel = questionLevel;
	}
	
	public buildTest() {
		
	}

}
