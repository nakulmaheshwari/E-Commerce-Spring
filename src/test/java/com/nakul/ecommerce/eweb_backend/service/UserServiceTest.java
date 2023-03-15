package com.nakul.ecommerce.eweb_backend.service;

import com.nakul.ecommerce.eweb_backend.api.model.LoginBody;
import com.nakul.ecommerce.eweb_backend.api.model.PasswordResetBody;
import com.nakul.ecommerce.eweb_backend.api.model.RegistrationBody;
import com.nakul.ecommerce.eweb_backend.entities.LocalUser;
import com.nakul.ecommerce.eweb_backend.entities.dao.LocalUserDAO;
import com.nakul.ecommerce.eweb_backend.exception.EmailFailureException;
import com.nakul.ecommerce.eweb_backend.exception.EmailNotFoundException;
import com.nakul.ecommerce.eweb_backend.exception.UserAlreadyExistsException;
import jakarta.mail.Message;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import jakarta.mail.MessagingException;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    /** The JWT Service. */
    @Autowired
    private JWTService jwtService;
    /** The Local User DAO. */
    @Autowired
    private LocalUserDAO localUserDAO;
    /** The encryption Service. */
    @Autowired
    private EncryptionService encryptionService;
    /** The Verification Token DAO. */



    @Test
    @Transactional
    public void testRegisterUser(){
        RegistrationBody body = new RegistrationBody();
        body.setUsername("UserA");
        body.setEmail("UserServiceTest$testRegisterUser@junit.com");
        body.setFirstName("FirstName");
        body.setLastName("LastName");
        body.setPassword("MySecretPassword123");

         Assertions.assertThrows(UserAlreadyExistsException.class,
                () -> userService.registerUser(body), "Username should already be in use.");


        body.setUsername("UserServiceTest$testRegisterUser");
        body.setEmail("UserA@junit.com");
        Assertions.assertThrows(UserAlreadyExistsException.class,
                () -> userService.registerUser(body), "Email should already be in use.");
        body.setEmail("UserServiceTest$testRegisterUser@junit.com");
        Assertions.assertDoesNotThrow(() -> userService.registerUser(body),
                "User should register successfully.");
    }

    @Test
    @Transactional
    public void testLoginUser() throws EmailFailureException {
        LoginBody body = new LoginBody();
        body.setUsername("UserA-NotExists");
        body.setPassword("PasswordA123-BadPassword");
        Assertions.assertNull(userService.loginUser(body), "The user should not exist.");
        body.setUsername("UserA");
        Assertions.assertNull(userService.loginUser(body), "The password should be incorrect.");
        body.setPassword("PasswordA123");
        Assertions.assertNotNull(userService.loginUser(body), "The user should login successfully.");
        body.setUsername("UserB");
        body.setPassword("PasswordB123");
        Assertions.assertNotNull(userService.loginUser(body), "The user should login successfully.");

    }

    @Test
    @Transactional
    public void testForgotPassword() throws MessagingException {
        Assertions.assertThrows(EmailNotFoundException.class,
                () -> userService.forgotPassword("UserNotExist@junit.com"));
        Assertions.assertDoesNotThrow(() -> userService.forgotPassword(
                "UserA@junit.com"), "Non existing email should be rejected.");
        Assertions.assertEquals("UserA@junit.com",
                greenMailExtension.getReceivedMessages()[0]
                        .getRecipients(Message.RecipientType.TO)[0].toString(), "Password " +
                        "reset email should be sent.");
    }

    public void testResetPassword() {
        LocalUser user = localUserDAO.findByUsernameIgnoreCase("UserA").get();
        String token = jwtService.generatePasswordResetJWT(user);
        PasswordResetBody body = new PasswordResetBody();
        body.setToken(token);
        body.setPassword("Password123456");
        userService.resetPassword(body);
        user = localUserDAO.findByUsernameIgnoreCase("UserA").get();
        Assertions.assertTrue(encryptionService.verifyPassword("Password123456",
                user.getPassword()), "Password change should be written to DB.");
    }


}
