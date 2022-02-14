package com.company.books;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
class ApplicationTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	void contextLoads() throws Exception {

		MvcResult result = mockMvc.perform(
		MockMvcRequestBuilders.get("/books/"))
		.andExpect(MockMvcResultMatchers.view().name("books-list"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("books"))
		.andDo(MockMvcResultHandlers.print()).andReturn();
		assertNotNull(Objects.requireNonNull(result.getModelAndView()).getModel().get("books"));
	}

}
