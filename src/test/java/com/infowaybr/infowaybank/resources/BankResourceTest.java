package com.infowaybr.infowaybank.resources;

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

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.infowaybr.infowaybank.models.Bank;

@RunWith(SpringRunner.class)
@WebMvcTest(BankResource.class)
public class BankResourceTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private BankResource bankResource;

	@Test
	public void findAll() throws Exception {
		Bank bank = new Bank();
		bank.setName("International Bank");

		List<Bank> banks = new ArrayList<Bank>();
		banks.add(bank);

		given(bankResource.findAll()).willReturn(banks);

		mvc.perform(get("/banks").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].name", is(bank.getName())));
	}

	@Test
	public void findById() throws Exception {
		Bank bank = new Bank();
		bank.setId(1L);
		bank.setName("International Bank");

		given(bankResource.findById(bank.getId())).willReturn(bank);

		mvc.perform(get("/banks/" + bank.getId()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("name", is(bank.getName())));
	}

}
