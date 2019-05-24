package ru.fssprus.r82.swing.main.questionListDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.service.QuestionService;

public class QuestionListController implements ActionListener {
	private static final int ENTRIES_FOR_PAGE = 25; 
	
	private int currentPage = 0;
	private int totalQuestionsInDb;
	private int totalPages;
	
	private QuestionListDialog questionListDialog;
	
	public QuestionListController(QuestionListDialog dialog) {
		this.questionListDialog = dialog;
		setListeners();
		loadQestionsFromDB();
	}
	
	private void setListeners() {
		questionListDialog.getBtnFilter().addActionListener(this);
		questionListDialog.getBtnNextPage().addActionListener(this);
		questionListDialog.getBtnPreviousPage().addActionListener(this);
		
	}
	
	private void loadQestionsFromDB() {
		QuestionService questionService = new QuestionService();
		//TODO: 
		int amountOfQuestions = questionService.countAll();
		
		questionListDialog.getLblPageInfo().setText("Cтраница " 
				+ (currentPage + 1) + " из " 
				+ countTotalPages(amountOfQuestions));
		
		int start = currentPage * ENTRIES_FOR_PAGE;
		int max = ENTRIES_FOR_PAGE;
		
		
		List<Question> questions = questionService.getAll(start, max);
		questionListDialog.getTabQuestList().addQuestions(questions);
		
	}
	
	private int countTotalPages(int amountOfQuestions) {
		this.totalPages = amountOfQuestions / ENTRIES_FOR_PAGE + 1;
		return totalPages;
	}
		 

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == questionListDialog.getBtnFilter()) 
			doFilterAction();
		if(e.getSource()  == questionListDialog.getBtnNextPage())
			doNextPageAction();
		if(e.getSource() == questionListDialog.getBtnPreviousPage())
			doPreviousPageAction();
		
		
	}

	private void doPreviousPageAction() {
		if(currentPage > 0) {
			currentPage--;
			loadQestionsFromDB();
		}
		
		
	}

	private void doNextPageAction() {
		if((currentPage + 1) < totalPages) {
			currentPage++;
			loadQestionsFromDB();
		}
		
	}

	private void doFilterAction() {
		// TODO Auto-generated method stub
		
	}
	
	

}
