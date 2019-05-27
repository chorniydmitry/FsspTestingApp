package ru.fssprus.r82.main;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import javax.swing.SwingUtilities;

import org.flywaydb.core.Flyway;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import ru.fssprus.r82.entity.Answer;
import ru.fssprus.r82.entity.Password;
import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.service.AnswerService;
import ru.fssprus.r82.service.QuestionService;
import ru.fssprus.r82.swing.main.mainFrame.MainFrame;
import ru.fssprus.r82.utils.CryptWithMD5;
import ru.fssprus.r82.utils.HibernateUtil;

public class Application {
	
	private static void appStart() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainFrame();
			}
			
		});
	}
	
	public static void getKeyCodes() {
		Field[] fields = java.awt.event.KeyEvent.class.getDeclaredFields();
		for (Field f : fields) {
		    if (Modifier.isStatic(f.getModifiers())) {	
		        System.out.println(f.getName());
		    } 
		}
	}
	
	public static void test() {
		QuestionService qs = new QuestionService();
		
		List<Question> qlist = qs.getByName("частота");
		System.out.println(qlist.size());
		qlist.forEach((n) -> System.out.println(n.getTitle()));
		
		System.out.println("-----------------------");
		AnswerService as = new AnswerService();
		List<Answer> aList = as.getAllByQuestion(0, 50, qlist.get(0));
		System.out.println(aList.size());
		aList.forEach((n)-> System.out.println(n.getTitle()));
		aList.forEach((n)-> System.out.println(n.getIsCorrect()));
		
		System.out.println("-----------------------");
		AnswerService as2 = new AnswerService();
		List<Answer> aList2 = as2.getAllByQuestion(0, 50, qlist.get(1));
		System.out.println(aList2.size());
		aList2.forEach((n)-> System.out.println(n.getTitle()));
		aList2.forEach((n)-> System.out.println(n.getIsCorrect()));
		
	}
	
	public static void newPassForConfigure() {
		String section = "SETTINGS";
		String password = CryptWithMD5.cryptWithMD5("settingsv2");
		
		Password pass = new Password();
		pass.setSectionName(section);
		pass.setPasswordMD5(password);
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			session.save(pass);
		} catch (HibernateException e) {
			e.printStackTrace();
			HibernateUtil.getSessionFactory().getCurrentSession().close();
		} 
		
	}

	public static void main(String[] args) throws IOException {
		
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		
//		Flyway flyway = Flyway.configure().dataSource(configuration.getProperty("connection.url"), 
//				configuration.getProperty("connection.username"), 
//				configuration.getProperty("connection.password")).load();
//		flyway.migrate();

		//try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			
		//	test();
			
		//	newPassForConfigure();

			appStart();

			
		//} catch (HibernateException e) {
		//	e.printStackTrace();
			//HibernateUtil.getSessionFactory().getCurrentSession().close();
		//} finally {
			//HibernateUtil.getSessionFactory().getCurrentSession().close();
		//}
	}
}
