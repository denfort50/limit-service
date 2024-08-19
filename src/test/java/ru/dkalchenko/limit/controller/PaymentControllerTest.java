package ru.dkalchenko.limit.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void whenReduceLimitThenSuccess() throws Exception {
        // given
        MockHttpServletRequestBuilder requestBuilder = post("/reduce")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "id":  1,
                            "sum":  160.75
                        }
                        """);
        // when
        mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "message": "Лимит уменьшен на сумму: 160.75"
                                }
                                """)
                );
    }

    @Test
    @Transactional
    public void whenReduceLimitThenFail() throws Exception {
        // given
        MockHttpServletRequestBuilder requestBuilder = post("/reduce")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "id":  2,
                            "sum":  16000.75
                        }
                        """);
        // when
        mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().isConflict(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "code": 409,
                                    "message": "Указанная сумма превышает лимит"
                                }
                                """)
                );
    }

    @Test
    public void whenChangeDefaultLimitThenSuccess() throws Exception {
        // given
        MockHttpServletRequestBuilder requestBuilder = post("/default")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "sum":  20000
                        }
                        """);
        // when
        mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "message": "Стандартное значение лимита успешно изменено"
                                }
                                """)
                );
    }

    @Test
    public void whenChangeDefaultLimitThenFail() throws Exception {
        // given
        MockHttpServletRequestBuilder requestBuilder = post("/default")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "sum":  -1000
                        }
                        """);
        // when
        mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().isBadRequest()
                );
    }
}
