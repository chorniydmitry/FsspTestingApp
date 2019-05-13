package ru.fssprus.r82.main;

import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ru.fssprus.r82.dao.impl.QuestionDatabaseDao;
import ru.fssprus.r82.dao.impl.SpecifiactionDatabaseDao;
import ru.fssprus.r82.entity.Answer;
import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.service.AnswerService;
import ru.fssprus.r82.service.QuestionService;
import ru.fssprus.r82.service.SpecificationService;
import ru.fssprus.r82.swing.main.mainFrame.MainFrame;
import ru.fssprus.r82.swing.main.resultingDialog.ResultingController;
import ru.fssprus.r82.swing.main.resultingDialog.ResultingDialog;
import ru.fssprus.r82.swing.main.settings.SettingsController;
import ru.fssprus.r82.swing.main.settings.SettingsDialog;
import ru.fssprus.r82.utils.TestFromODSLoader;
import ru.fssprus.r82.utils.TestingProcess;

public class Application {
	
	public static void testing(Session session) {
		Question question = new Question();
		Specification spec = new Specification();
		Set<Answer> answers = new HashSet<Answer>();

		Answer answer1 = new Answer();
		answer1.setIsCorrect(false);
		answer1.setTitle("15");
		answer1.setQuestion(question);

		Answer answer2 = new Answer();
		answer2.setIsCorrect(true);
		answer2.setTitle("25");
		answer2.setQuestion(question);

		answers.add(answer1);
		answers.add(answer2);

		spec.setName("Arifmetics");

		Set<Specification> specs = new HashSet<Specification>();
		specs.add(spec);

		question.setAnswers(answers);
		question.setSpecifications(specs);
		question.setTitle("5*5=?");
		
		Transaction tx = session.beginTransaction();
		session.save(question);
		tx.commit();

		System.out.println("--------------------");

		Question questFromDB = session.get(Question.class, 1L);

		System.out.println(questFromDB.getId());
		System.out.println(questFromDB.getTitle());

		System.out.println("Choose correct answer: ");

		for (Answer ans : questFromDB.getAnswers()) {
			System.out.println(ans.getTitle());
		}
		session.close();
	}
	
	@SuppressWarnings("unused")
	private static void testing2() {
		QuestionDatabaseDao qdd = new QuestionDatabaseDao();
		Question quest = qdd.getById(67L);
		
		
		
		SpecifiactionDatabaseDao sdd = new SpecifiactionDatabaseDao();
		List<Specification> specificationList = sdd.getByQuestion(0, 10, quest);
		
		System.out.println(quest.getTitle());
		for (Specification specification : specificationList) {
			System.out.println(specification.getName());
		}
		
	}
	
	@SuppressWarnings("unused")
	private static void appStart() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new MainFrame();
			}
			
		});
	}
	
	@SuppressWarnings("unused")
	private static void testing3() {
		QuestionService service = new QuestionService();
		Set<Long> ids = new HashSet<Long>();
		ids.add(new Long(3));
		ids.add(new Long(9));
		ids.add(new Long(7));
		ids.add(new Long(2));
		ids.add(new Long(99));
		
		List<Question> quests = service.getByIDsList(ids);
		for(Question q: quests) {
			System.out.println(q.getTitle());
		}
	}
	
	@SuppressWarnings("unused")
	private static void testingQuestionCount() {
		QuestionService service = new QuestionService();
		SpecificationService specService = new SpecificationService();
		Specification spec = specService.getByID(3L);
		int count = service.getItemsCountBySpecification(spec);
		
		System.out.println("Count of items: " + count);
		
	}
	
	@SuppressWarnings("unused")
	private static void testingGetQuestionsBySpecification() {
		QuestionService questService = new QuestionService();
		SpecificationService specService = new SpecificationService();
		
		Specification spec = specService.getByID(2L);
		
		List<Question> quest = questService.getBySpecification(0, 10, spec);
		
		for (Question question : quest) {
			System.out.println(question.getTitle());
		}
	}
	
	@SuppressWarnings("unused")
	private static void testingRandomSetOfQuestions() {
		Map<Question, List<Answer>> questAnswerMap = new HashMap<Question,List<Answer>>();
		
		SpecificationService spService = new SpecificationService();
		Specification spec = spService.getByID(1L);
		
		QuestionService service = new QuestionService();
		AnswerService ansService = new AnswerService();
		
		int maxAmountOfQuestions = service.getItemsCountBySpecification(spec);
		int amountOfQuestions = 20;
		
		Random rnd = new Random();
		
		Set<Long> randomIds = new HashSet<Long>();
		for(int i = 0; i <= amountOfQuestions; i++) 
			randomIds.add((long)rnd.nextInt(maxAmountOfQuestions));
		
		List<Question> questions = service.getByIDsList(randomIds);
		
		
		for (Question question : questions) {
			List<Answer> ansList = ansService.getAllByQuestion(0, 10, question);
			questAnswerMap.put(question, ansList);
		}
		
		
		///
		
	       for(Map.Entry<Question, List<Answer>> item : questAnswerMap.entrySet()){
	           
	           System.out.println("Question\n" + item.getKey().getTitle());
	           for (Answer answer : item.getValue()) {
	        	   	System.out.println(answer.getTitle() + " is correct?: "+answer.getIsCorrect());
	           
	           }
	        	   
			}
	       }
		
		//questions.forEach((n)->System.out.println(n.getTitle()));
		
		
	public static void testCorrectAnswersByQuestiosSet() {
		QuestionService qservice = new QuestionService();
		AnswerService aservice = new AnswerService();
		
		List<Question> questions = new ArrayList<Question>();
		Question q1 = qservice.getById(12L);
		Question q2 = qservice.getById(7L);
		Question q3 = qservice.getById(5L);
		Question q4 = qservice.getById(8L);
		questions.add(q1);
		questions.add(q2);
		questions.add(q3);
		questions.add(q4);
		
		Set<Question> questSet = new HashSet<Question>(questions);
		
		List<Answer> answers = aservice.getCorrectByQuestionSet(0, 20, questSet);
		
		for (Answer ans : answers) {
			System.out.println(ans.getTitle());
			
		}
	}
	
	public static void getKeyCodes() {
		Field[] fields = java.awt.event.KeyEvent.class.getDeclaredFields();
		for (Field f : fields) {
		    if (Modifier.isStatic(f.getModifiers())) {
		        System.out.println(f.getName());
		    } 
		}
	}
	
	public static void resultingDialogTest() {
		ResultingDialog dialog = new ResultingDialog(400,250);
    	dialog.setCaptions(10,
    			20,
    			"asdasd",
    			"adsasd",
    			"не удовлетворительно", 
    			"A");
    	
    	dialog.setMarkColor(Color.GREEN);
    	
    	new ResultingController(dialog, new TestingProcess(null, null));
	}

	public static void loadFromODS() {
		new TestFromODSLoader().doOpenODS();
	}
	
	
	public static void testMultSpecsTest() {
		SpecificationService specificationService = new SpecificationService();
		List<Specification> specList = specificationService.getByName(0, 1, "ОБЩИЕ");
		specList.add(specificationService.getByName(0, 1, "Информатизация").get(0));
		
		List<Integer> amtForSpecs = new ArrayList<Integer>();
		amtForSpecs.add(10);
		amtForSpecs.add(5);
		
		TestingProcess process = new TestingProcess(specList, amtForSpecs);
		
		List<Question> questionsList = process.getQuestions();
		
		questionsList.forEach((n) -> System.out.println(n.getSpecifications().toString() +" "+ n.getTitle()));
	}
	
	public static void testSettingsDialog() {
		
		SettingsDialog sd = new SettingsDialog(800, 600);
		new SettingsController(sd);

	}

	
	public static void main(String[] args) throws IOException {
	//	try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			//new CSVLoader().doOpenCSV();
			//testing(session);
			//testing2();
			//getKeyCodes();
			//resultingDialogTest();
			//loadFromODS();
			appStart();
		//	testSettingsDialog();
			//testMultSpecsTest();
			//testCorrectAnswersByQuestiosSet();
			//testing3();
			//testingQuestionCount();
			//testingGetQuestionsBySpecification();
			//testingRandomSetOfQuestions();
			
			
//		} catch (HibernateException e) {
//			e.printStackTrace();
//		}
	}
}
