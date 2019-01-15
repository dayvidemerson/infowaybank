package com.infowaybr.infowaybank.resources;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infowaybr.infowaybank.models.Bank;

@RunWith(SpringRunner.class)
@WebMvcTest(BankResource.class)
public class BankResourceTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private BankResource bankResource;

	private final String URL = "/api/banks";

	@Test
	public void findAll() throws Exception {
		Bank bank = new Bank();
		bank.setName("International Bank");

		List<Bank> banks = new ArrayList<Bank>();
		banks.add(bank);

		given(bankResource.findAll()).willReturn(banks);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(URL);
		builder = builder.contentType(MediaType.APPLICATION_JSON);

		mvc.perform(builder)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is(bank.getName())));
	}

	@Test
	public void findById() throws Exception {
		Bank bank = new Bank();
		bank.setId(1L);
		bank.setName("International Bank");

		given(bankResource.findById(bank.getId())).willReturn(bank);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(URL + "/" + bank.getId());
		builder = builder.contentType(MediaType.APPLICATION_JSON);

		mvc.perform(builder)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("name", is(bank.getName())));
	}

	@Test
	public void create() throws Exception {
		Bank bank = new Bank();
		bank.setName("National Bank");

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(bank);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(URL);
		builder = builder.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json);
		
		MvcResult result = mvc.perform(builder)
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andReturn();

		System.out.println("-------------");
		System.out.println(result.getResponse().getStatus());
		System.out.println("-------------");
	}

	@Test
	public void update() throws Exception {
		Bank bank = new Bank();
		bank.setId(1L);
		bank.setName("National Bank");

		given(bankResource.findById(bank.getId())).willReturn(bank);

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(bank);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(URL + "/" + bank.getId());
		builder = builder.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json);

		
		mvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
	}

}
