package com.infowaybr.infowaybank.resources;

import static org.mockito.BDDMockito.given;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infowaybr.infowaybank.models.Agency;
import com.infowaybr.infowaybank.models.Bank;
import com.infowaybr.infowaybank.models.BankAccount;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BankAccountResourceTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private BankResource bankResource;

	@MockBean
	private AgencyResource agencyResource;

	@MockBean
	private BankAccountResource bankAccountResource;

	private final String URL = "/api/bank-accounts";

	private Bank bank;

	private Agency agency;

	@Before
	public void setUp() {
		bank = new Bank("International Bank");
		bank.setId(1L);

		agency = new Agency(1, 1232, bank, "Brasil", "PI", "Picos", "Bairro São José", "Rua Luis Nunes");
		agency.setId(1L);
	}

	@Test
	public void create() throws Exception {
		BankAccount bankAccount = new BankAccount("Dayvid Emerson", 1, 3234, "1234", agency);

		given(bankAccountResource.create(bankAccount)).willReturn(bankAccount);

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(bankAccount);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(URL);
		builder = builder.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(json);

		mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	@WithMockUser
	public void update() throws Exception {
		BankAccount bankAccount = new BankAccount("Dayvid Emerson", 1, 3234, "1234", agency);
		bankAccount.setId(1L);
		
		given(bankAccountResource.update(bankAccount)).willReturn(bankAccount);

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(bankAccount);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(URL);
		builder = builder.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(json);

		mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

}
