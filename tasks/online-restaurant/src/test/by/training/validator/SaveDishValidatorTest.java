package by.training.validator;

import by.training.user.UserService;
import by.training.user.UserServiceException;
import by.training.user.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.util.Map;
import java.util.Set;

import static by.training.application.ApplicationConstants.DISH_NAME_ATTRIBUTE;
import static by.training.application.ApplicationConstants.DISH_PRICE_ATTRIBUTE;

@RunWith(JUnit4.class)
public class SaveDishValidatorTest {
    @Test
    @SuppressWarnings("unchecked")
    public void shouldSuccessfulValidate() throws ValidationException {
        Map<String, String> mockParams = Mockito.mock(Map.class);
        Mockito.when(mockParams.get(DISH_NAME_ATTRIBUTE)).thenReturn("pizza");
        Mockito.when(mockParams.get(DISH_PRICE_ATTRIBUTE)).thenReturn("185.89");

        Validator dishValidator = new SaveDishValidator();

        ValidationResult validationResult = dishValidator.validate(mockParams);

        Assert.assertTrue(validationResult.isValid());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldFindErrors() throws ValidationException {
        Map<String, String> mockParams = Mockito.mock(Map.class);
        Mockito.when(mockParams.get(DISH_NAME_ATTRIBUTE)).thenReturn("pizza");
        Mockito.when(mockParams.get(DISH_PRICE_ATTRIBUTE)).thenReturn("-89");

        Validator dishValidator = new SaveDishValidator();

        ValidationResult validationResult = dishValidator.validate(mockParams);
        Set<String> validationKeys = validationResult.getErrors().keySet();

        Assert.assertTrue(validationKeys.contains(DISH_PRICE_ATTRIBUTE));
        Assert.assertEquals(1, validationKeys.size());
    }
}
