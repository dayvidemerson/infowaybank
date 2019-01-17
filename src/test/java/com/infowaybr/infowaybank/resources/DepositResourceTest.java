package com.infowaybr.infowaybank.resources;

import static org.mockito.BDDMockito.given;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infowaybr.infowaybank.models.Agency;
import com.infowaybr.infowaybank.models.Bank;
import com.infowaybr.infowaybank.models.BankAccount;
import com.infowaybr.infowaybank.models.Deposit;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DepositResourceTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private BankResource bankResource;

	@MockBean
	private AgencyResource agencyResource;

	@MockBean
	private BankAccountResource bankAccountResource;

	@MockBean
	private DepositResource depositResource;

	private final String URL = "/api/deposits";

	private Bank bank;

	private Agency agency;

	private BankAccount bankAccount;

	@Before
	public void setUp() {
		bank = new Bank("International Bank");
		bank.setId(1L);

		agency = new Agency(1, 1232, bank, "Brasil", "PI", "Picos", "Bairro São José", "Rua Luis Nunes");
		agency.setId(1L);

		bankAccount = new BankAccount("Dayvid Emerson", 1, 1234, "123", agency);
		bankAccount.setId(1L);
	}

	@Test
	public void create() throws Exception {
		Deposit deposit = new Deposit(100.5, bankAccount);
		deposit.setCreated(new Date());
		
		given(depositResource.create(deposit)).willReturn(deposit);

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(deposit);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(URL);
		builder = builder.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(json);

		mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isCreated());
	}
}
