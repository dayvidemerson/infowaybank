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
import org.springframework.security.authentication.AuthenticationManager;
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
import com.infowaybr.infowaybank.models.Withdrawal;
import com.infowaybr.infowaybank.security.JwtTokenProvider;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WithdrawalResourceTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@MockBean
	private BankResource bankResource;

	@MockBean
	private AgencyResource agencyResource;

	@MockBean
	private BankAccountResource bankAccountResource;

	@MockBean
	private WithdrawalResource withdrawalResource;

	private final String URL = "/api/withdrawals";

	private Bank bank;

	private Agency agency;

	private BankAccount bankAccount;

	@Before
	public void setUp() {
		bank = new Bank("International Bank");
		bank.setId(1L);

		agency = new Agency(1, 1111, bank, "Brasil", "PI", "Picos", "Bairro São José", "Rua Luis Nunes");
		agency.setId(1L);

		bankAccount = new BankAccount("Dayvid Emerson", 1, 1111, "1234", agency);
		bankAccount.setId(1L);
	}

	@Test
	@WithMockUser
	public void whenBalanceIsInsufficient() throws Exception {
		Withdrawal withdrawal = new Withdrawal(100.5, bankAccount);
		withdrawal.setCreated(new Date());

		given(withdrawalResource.create(withdrawal)).willReturn(withdrawal);

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(withdrawal);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(URL);
		builder = builder.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(json);

		mvc.perform(builder)
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
}
