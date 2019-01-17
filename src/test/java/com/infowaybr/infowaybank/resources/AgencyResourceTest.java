package com.infowaybr.infowaybank.resources;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AgencyResourceTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private BankResource bankResource;

	@MockBean
	private AgencyResource agencyResource;

	private final String URL = "/api/agencies";

	private Bank bank;

	@Before
	public void setUp() {
		bank = new Bank("International Bank");
		bank.setId(1L);
	}

	@Test
	public void findAll() throws Exception {
		Agency agency = new Agency(1, 1232, bank, "Brasil", "PI", "Picos", "Bairro São José", "Rua Luis Nunes");
		agency.setId(1L);

		List<Agency> agencies = new ArrayList<Agency>();
		agencies.add(agency);

		given(agencyResource.findAll()).willReturn(agencies);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(URL);
		builder = builder.contentType(MediaType.APPLICATION_JSON);

		mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].digit", is(agency.getDigit())));
	}

	@Test
	public void findById() throws Exception {
		Agency agency = new Agency(1, 1232, bank, "Brasil", "PI", "Picos", "Bairro São José", "Rua Luis Nunes");
		agency.setId(1L);

		given(agencyResource.findById(agency.getId())).willReturn(agency);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(URL + "/" + agency.getId());
		builder = builder.contentType(MediaType.APPLICATION_JSON);

		mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("digit", is(agency.getDigit())));
	}

	@Test
	public void create() throws Exception {
		Agency agency = new Agency(1, 2222, bank, "Brasil", "PI", "Picos", "Bairro São José", "Rua Luis Nunes");

		given(agencyResource.create(agency)).willReturn(agency);

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(agency);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(URL);
		builder = builder.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(json);

		mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	public void update() throws Exception {
		Agency agency = new Agency(1, 1232, bank, "Brasil", "PI", "Picos", "Bairro São José", "Rua Luis Nunes");
		agency.setId(1L);

		given(agencyResource.update(agency, agency.getId())).willReturn(agency);

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(agency);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(URL + "/" + agency.getId());
		builder = builder.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(json);

		mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
	}
}
