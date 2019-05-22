package ru.fssprus.r82.utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import ru.fssprus.r82.entity.Answer;
import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.entity.Test;
import ru.fssprus.r82.entity.User;
import ru.fssprus.r82.service.AnswerService;
import ru.fssprus.r82.service.QuestionService;
import ru.fssprus.r82.service.TestService;
import ru.fssprus.r82.service.UserService;

public class TestingProcess {
	private int curQuestionIndex;
	private List<Specification> specs;
	private List<Question> questions;
	private List<Integer> correctAnswers;
	private List<Integer> choises;
	private List<Boolean> correctUserAnswersList;
	private Map<Integer, List<Answer>> answersMap;
	private int correctAnswersAmount;

	private QuestionService questService = new QuestionService();
	private AnswerService answerService = new AnswerService();
	private UserService userService = new UserService();
	
	private String userName;
	private String userSurname;
	private String userSecondName;
	private User user;
	
	private String level;
	
	public TestingProcess(List<Specification> specs, List<Integer> amtQuestions, String level) {
		this.specs = specs;
		this.level = level;
		loadQuestionsFromDB(specs, amtQuestions);
		initLists();
		loadAnswersForQuests();
		test();
	}
	
	private void test() {
		
	}

	private void initLists() {
		correctAnswers = new ArrayList<Integer>(questions.size());
		choises = new ArrayList<Integer>(Collections.nCopies(questions.size(), -1));
		correctUserAnswersList = new ArrayList<Boolean>(Collections.nCopies(questions.size(), false));
		answersMap = new HashMap<Integer, List<Answer>>();
	}

	private void loadQuestionsFromDB(List<Specification> specs, List<Integer> amtQuestions) {
		if (questions == null)
			questions = new ArrayList<Question>();
		
		for(int i = 0; i < specs.size(); i++) {
			List<Question> allQuestionsList = questService.getAllBySpecificationAndLevel(specs.get(i), QuestionLevel.valueOf(level));
			Random rnd = new Random();
			Set<Integer> randomIndexesSet = new HashSet<Integer>();
			
			do {
				randomIndexesSet.add(rnd.nextInt(allQuestionsList.size()));
				
			} while(randomIndexesSet.size() < amtQuestions.get(i));
			
			randomIndexesSet.forEach((n) -> questions.add(allQuestionsList.get(n)));
			
			Collections.shuffle(questions);
		}
	}

	public void countCorrectAnswers() {
		int i = 0;
		for (Boolean ans : correctUserAnswersList)
			if (ans)
				i++;
		correctAnswersAmount = i;
	}
	
	public String getMarkOneToFive() {
		return String.valueOf(
				MarkCounter.countInOneToFive(questions.size(), correctAnswersAmount));
	}
	
	public String getMarkLetter() {
		return String.valueOf(
				MarkCounter.countMarkInECTS(questions.size(), correctAnswersAmount));
	}
	
	public String getMarkText() {
		return String.valueOf(
				MarkCounter.countInWords(questions.size(), correctAnswersAmount));
	}
	
	public String getMarkPercent() {
		return String.valueOf(
				MarkCounter.countMarkInPercent(questions.size(), correctAnswersAmount));
	}
	public Color getMarkColor() {
		return MarkCounter.countInColors(questions.size(), correctAnswersAmount);
	}
	
	public int getCorrectAnswersAmount() {
		return correctAnswersAmount;
	}
	
	public void saveResultsToDB(Specification spec, int questTimeSec) {
		countCorrectAnswers();
		int correctAnswers = correctAnswersAmount;
		
		Test test = new Test();
		test.setCorrectAnswers(correctAnswers);
		test.setDate(new Date());
		test.setScore(MarkCounter.countMarkInPercent(questions.size(), correctAnswers));
		test.setResult(MarkCounter.countInWords(questions.size(), correctAnswers));
		test.setUser(user);
		
		test.setSpecification(specs.get(0));
		test.setLevel(level);
		test.setTestingTime(questTimeSec);
		test.setTotalQuestions(questions.size());
		
		TestService service = new TestService();
		service.add(test);
	}

	public String showWrongs() {
		countCorrectAnswers();
		int i = 0;
		String returnValue = "<html style='font-size:14pt; font-family:Times New Roman;'><b>Список вопросов:</b><br /><br />";
		for (Boolean ans : correctUserAnswersList) {
			if (!ans) {
				String question = "<b>Вопрос:</b> " + questions.get(i).getTitle() + "<br /><br />";
				String answerChosen;
				String answerCorrect;
				String delimeter = "-------------------------<br />";
				
				if(choises.get(i) != -1)
					answerChosen = "<b>Выбран ответ:</b> " + answersMap.get(i).get(choises.get(i)).getTitle() + "<br />";
				else
					answerChosen = "Ничего не выбрано<br />";
				
				answerCorrect = "<b>Верный ответ:</b> " + answersMap.get(i).get(correctAnswers.get(i)).getTitle() + "<br />";
				
				returnValue += question + answerChosen + answerCorrect + delimeter;
			}
			i++;
		}
		returnValue+="</html>";

		return returnValue;
	}
	
	private boolean loadAnswersForQuests() {
		for (int i = 0; i < getQuestions().size(); i++) {
			List<Answer> ansList = answerService.getAllByQuestion(0, 20, getQuestions().get(i));
			
			for (int j = 0; j < ansList.size(); j++) {
				if (ansList.get(j).getIsCorrect()) {
					correctAnswers.add(j);
				}
			}
			setAnswersForQuestion(i, ansList);
		}
		return true;
	}

	public int getCurrentQuestionIndex() {
		return curQuestionIndex;
	}

	public void setCurrentQuestionIndex(int index) {
		this.curQuestionIndex = index;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public void addQuestions(List<Question> questions) {
		if (this.questions == null)
			this.questions = new ArrayList<Question>();
		this.questions.addAll(questions);
	}

	public Map<Integer, List<Answer>> getAnswersMap() {
		return answersMap;
	}

	public void setAnswersMap(Map<Integer, List<Answer>> answersMap) {
		this.answersMap = answersMap;
	}

	public void setAnswersForQuestion(int index, List<Answer> answers) {
		answersMap.put(index, answers);
	}

	public List<Integer> getChoises() {
		return choises;
	}

	public void setChoises(List<Integer> choises) {
		this.choises = choises;
	}

	public void setChoise(int number) {
		choises.set(curQuestionIndex, new Integer(number));
		if (correctAnswers.get(curQuestionIndex) == number)
			correctUserAnswersList.set(curQuestionIndex, true);
	}

	public List<Integer> getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(List<Integer> correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	public void fillUserInfo(String userName, String userSurname, String userSecondName) {
		setUserName(userName);
		setUserSurname(userSurname);
		setUserSecondName(userSecondName);
		
		List<User> usersFromDB = userService.getByNameSurnameSecondName(0, 10, userName, userSurname, userSecondName);
		
		if(usersFromDB.size() == 0)
			createNewUser(userName, userSurname, userSecondName);
		else
			user = usersFromDB.get(0);
	}

	private void createNewUser(String userName, String userSurname, String userSecondName) {
		user = new User();
		
		user.setName(userName);
		user.setSecondName(userSecondName);
		user.setSurname(userSurname);
		
		userService.add(user);
	}
	
	public boolean checkUncheckedLeft() {
		for (Integer choise : choises) 
			if(choise == -1) 
				return true;
		return false;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSurname() {
		return userSurname;
	}

	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
	}

	public String getUserSecondName() {
		return userSecondName;
	}

	public void setUserSecondName(String userSecondName) {
		this.userSecondName = userSecondName;
	}

	public int getNextUnansweredIndex() {
		for (int i = 0; i < choises.size(); i++)
			if(choises.get(i) == -1)
				return i;
		
		return -1;
	}

	public int getWrongAmount() {
		int amount = 0;
		for (Boolean isCorrect : correctUserAnswersList)
			if(!isCorrect)
				amount++;
	
		return amount;
	}


}
