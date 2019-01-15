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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infowaybr.infowaybank.models.Agency;
import com.infowaybr.infowaybank.models.Bank;

@RunWith(SpringRunner.class)
@WebMvcTest(AgencyResource.class)
public class AgencyResourceTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private BankResource bankResource;

	@MockBean
	private AgencyResource agencyResource;

	private final String URL = "/api/agencies";

	@Test
	public void findAll() throws Exception {
		Bank bank = new Bank();
        bank.setId(1L);
        bank.setName("International Bank");

        given(bankResource.findById(bank.getId())).willReturn(bank);

		Agency agency = new Agency();
		agency.setId(1L);
		agency.setDigit(1);
		agency.setNumber(1232);
		agency.setCountry("Brasil");
		agency.setState("PI");
		agency.setCity("Picos");
		agency.setDistrict("Bairro São José");
		agency.setStreet("Rua Luis Nunes");
		agency.setBank(bank);
	
		List<Agency> agencies = new ArrayList<Agency>();
		agencies.add(agency);

		given(agencyResource.findAll()).willReturn(agencies);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(URL);
		builder = builder.contentType(MediaType.APPLICATION_JSON);

		mvc.perform(builder)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].digit", is(agency.getDigit())));
	}

	@Test
	public void findById() throws Exception {
		Bank bank = new Bank();
        bank.setId(1L);
        bank.setName("International Bank");

        given(bankResource.findById(bank.getId())).willReturn(bank);

		Agency agency = new Agency();
		agency.setId(1L);
		agency.setDigit(1);
		agency.setNumber(1232);
		agency.setCountry("Brasil");
		agency.setState("PI");
		agency.setCity("Picos");
		agency.setDistrict("Bairro São José");
		agency.setStreet("Rua Luis Nunes");
		agency.setBank(bank);

		given(agencyResource.findById(agency.getId())).willReturn(agency);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(URL + "/" + agency.getId());
		builder = builder.contentType(MediaType.APPLICATION_JSON);

		mvc.perform(builder)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("digit", is(agency.getDigit())));
	}

	@Test
	public void create() throws Exception {
		Bank bank = new Bank();
        bank.setId(1L);
        bank.setName("International Bank");

        given(bankResource.findById(bank.getId())).willReturn(bank);

		Agency agency = new Agency();
		agency.setDigit(1);
		agency.setNumber(2222);
		agency.setCountry("Brasil");
		agency.setState("PI");
		agency.setCity("Picos");
		agency.setDistrict("Bairro São José");
		agency.setStreet("Rua Luis Nunes");
		agency.setComplement("2º Andar");
		agency.setBank(bank);
		
		given(agencyResource.create(agency)).willReturn(agency);

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(agency);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(URL);
		builder = builder.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json);
		
		mvc.perform(builder)
			.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	public void update() throws Exception {
		Bank bank = new Bank();
        bank.setId(1L);
        bank.setName("International Bank");

        given(bankResource.findById(bank.getId())).willReturn(bank);

		Agency agency = new Agency();
		agency.setId(1L);
		agency.setDigit(1);
		agency.setNumber(1232);
		agency.setCountry("Brasil");
		agency.setState("PI");
		agency.setCity("Picos");
		agency.setDistrict("Bairro São José");
		agency.setStreet("Rua Luis Nunes");
		agency.setComplement("2º Andar");
		agency.setBank(bank);

		given(agencyResource.update(agency, agency.getId())).willReturn(agency);

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(agency);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(URL + "/" + bank.getId());
		builder = builder.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json);

		mvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
	}

}
