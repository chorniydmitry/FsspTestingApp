package ru.fssprus.r82.utils;

public class AppConstants {
	public static final int BASE_QUESTS = Integer.parseInt(ApplicationConfiguration.getItem("base.num"));
	public static final int STANDART_QUESTS = Integer.parseInt(ApplicationConfiguration.getItem("standart.num"));
	public static final int ADVANCED_QUESTS = Integer.parseInt(ApplicationConfiguration.getItem("advanced.num"));
	public static final int RESERVE_QUESTS = Integer.parseInt(ApplicationConfiguration.getItem("reserve.num"));

	
	public static final int BASE_TIME = Integer.parseInt(ApplicationConfiguration.getItem("base.time"));
	public static final int STANDART_TIME = Integer.parseInt(ApplicationConfiguration.getItem("standart.time"));
	public static final int ADVANCED_TIME = Integer.parseInt(ApplicationConfiguration.getItem("advanced.time"));
	public static final int RESERVE_TIME = Integer.parseInt(ApplicationConfiguration.getItem("reserve.time"));
	
	public static final int BASE_COMMONS = Integer.parseInt(ApplicationConfiguration.getItem("base.common.percent"));
	public static final int STANDART_COMMONS = Integer.parseInt(ApplicationConfiguration.getItem("standart.common.percent"));
	public static final int ADVANCED_COMMONS = Integer.parseInt(ApplicationConfiguration.getItem("advanced.common.percent"));
	public static final int RESERVE_COMMONS = Integer.parseInt(ApplicationConfiguration.getItem("reserve.common.percent"));
	

	public static final String SETTINGS_SECTION = "SETTINGS";
	public static final String QUESTION_EDIT_SECTION = "QEDIT";
	public static final String QUESTION_LOAD_SECTION = "QLOAD";
	public static final String MANAGE_PASSWORDS_SECTION = "PASSWORDS";
	public static final String TEST_SECTION = "TEST";
	public static final String STATISTICS_SECTION = "STATISTICS";
	
}
