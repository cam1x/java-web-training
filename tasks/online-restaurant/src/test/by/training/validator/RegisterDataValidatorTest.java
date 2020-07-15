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

import static by.training.application.ApplicationConstants.*;

@RunWith(JUnit4.class)
public class RegisterDataValidatorTest {
    private UserService userService;

    @Before
    public void prepare() {
        userService = Mockito.mock(UserServiceImpl.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldSuccessfulValidate() throws UserServiceException, ValidationException {
        Map<String, String> mockParams = Mockito.mock(Map.class);
        Mockito.when(mockParams.get(LOGIN_ATTRIBUTE)).thenReturn("cam1x");
        Mockito.when(mockParams.get(PASSWORD_ATTRIBUTE)).thenReturn("123");
        Mockito.when(mockParams.get(REPEAT_PASSWORD_ATTRIBUTE)).thenReturn("123");
        Mockito.when(mockParams.get(FIRST_NAME_ATTRIBUTE)).thenReturn("Maxim");
        Mockito.when(mockParams.get(LAST_NAME_ATTRIBUTE)).thenReturn("Chechetkin");
        Mockito.when(mockParams.get(EMAIL_ATTRIBUTE)).thenReturn("max1@gmail.com");
        Mockito.when(mockParams.get(PHONE_ATTRIBUTE)).thenReturn("+375447700007");

        Mockito.when(userService.isLoginTaken("cam1x")).thenReturn(false);
        Validator registerValidator = new RegisterDataValidator(userService);

        ValidationResult validationResult = registerValidator.validate(mockParams);

        Assert.assertTrue(validationResult.isValid());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldFindErrors() throws UserServiceException, ValidationException {
        Map<String, String> mockParams = Mockito.mock(Map.class);
        Mockito.when(mockParams.get(LOGIN_ATTRIBUTE)).thenReturn("cam1x");
        Mockito.when(mockParams.get(PASSWORD_ATTRIBUTE)).thenReturn("123");
        Mockito.when(mockParams.get(REPEAT_PASSWORD_ATTRIBUTE)).thenReturn("321");
        Mockito.when(mockParams.get(FIRST_NAME_ATTRIBUTE)).thenReturn("Maxim");
        Mockito.when(mockParams.get(LAST_NAME_ATTRIBUTE)).thenReturn("Chechetkin");
        Mockito.when(mockParams.get(EMAIL_ATTRIBUTE)).thenReturn("max");
        Mockito.when(mockParams.get(PHONE_ATTRIBUTE)).thenReturn("myFormat");

        Mockito.when(userService.isLoginTaken("cam1x")).thenReturn(true);
        Validator registerValidator = new RegisterDataValidator(userService);

        ValidationResult validationResult = registerValidator.validate(mockParams);
        Set<String> validationKeys = validationResult.getErrors().keySet();

        Assert.assertTrue(validationKeys.contains(PASSWORD_ATTRIBUTE));
        Assert.assertTrue(validationKeys.contains(EMAIL_ATTRIBUTE));
        Assert.assertTrue(validationKeys.contains(PHONE_ATTRIBUTE));
        Assert.assertTrue(validationKeys.contains(LOGIN_ATTRIBUTE));
        Assert.assertEquals(4, validationKeys.size());
    }
}
