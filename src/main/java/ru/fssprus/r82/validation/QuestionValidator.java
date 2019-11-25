package ru.fssprus.r82.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ru.fssprus.r82.entity.User;

/**
 * @author Chernyj Dmitry
 *
 */
public class QuestionValidator implements ConstraintValidator<QuestionCheck, User>{

	@Override
	public boolean isValid(User value, ConstraintValidatorContext context) {
		return false;
	}

}
