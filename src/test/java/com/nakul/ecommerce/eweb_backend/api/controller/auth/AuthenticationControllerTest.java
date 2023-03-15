package com.nakul.ecommerce.eweb_backend.api.controller.auth;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mvc;
    @Test
    @Transactional
    public void testRegister(){



    }
}
