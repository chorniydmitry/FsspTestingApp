package ru.fssprus.r82.validation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ru.fssprus.r82.utils.AppConstants;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
/**
 * @author Chernyj Dmitry
 *
 */
@Constraint(validatedBy = {QuestionValidator.class})
@Target({TYPE})
@Retention(RUNTIME)
public @interface QuestionCheck {
	String message() default "Не верно заполнен вопрос для добавления в базу данных!\n\n"
			+ "ТЕКСТ ВОПРОСА: " + "Длина текста вопроса должна быть больше " + AppConstants.QUESTION_TEXT_MIN_LENGTH
			+ " символов.\n\n" + "СПИСОК ОТВЕТОВ:\n"
			+ " - минимальное количество ответов должно быть больше или равно " + AppConstants.MIN_ANSWERS_AMOUNT
			+ ";\n" + " - как минимум один из ответов должен быть помечен как верный; \n"
			+ " - не заполненный вопрос не должен быть помечен как верный.\n\n" + "СЛОЖНОСТЬ ВОПРОСА: "
			+ "Для вопроса должна быть выбрана как минимум одна сложность.\n\n" + "СПЕЦИАЛИЗАЦИЯ ВОПРОСА: "
			+ "Специализация должна быть выбрана из списка или заполнена.\n\n"
			+ "---------------------------------------\n"
			+ "Отредактируйте введенные данные, чтобы они удовлетворяли условиям, описанным выше"
			+ " и повторите попытку!";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default{};
}
