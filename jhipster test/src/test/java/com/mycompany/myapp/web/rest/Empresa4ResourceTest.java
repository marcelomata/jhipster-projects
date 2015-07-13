package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Empresa4;
import com.mycompany.myapp.repository.Empresa4Repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Empresa4Resource REST controller.
 *
 * @see Empresa4Resource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Empresa4ResourceTest {

    private static final String DEFAULT_NOME = "SAMPLE_TEXT";
    private static final String UPDATED_NOME = "UPDATED_TEXT";

    @Inject
    private Empresa4Repository empresa4Repository;

    private MockMvc restEmpresa4MockMvc;

    private Empresa4 empresa4;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Empresa4Resource empresa4Resource = new Empresa4Resource();
        ReflectionTestUtils.setField(empresa4Resource, "empresa4Repository", empresa4Repository);
        this.restEmpresa4MockMvc = MockMvcBuilders.standaloneSetup(empresa4Resource).build();
    }

    @Before
    public void initTest() {
        empresa4 = new Empresa4();
        empresa4.setNome(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createEmpresa4() throws Exception {
        int databaseSizeBeforeCreate = empresa4Repository.findAll().size();

        // Create the Empresa4
        restEmpresa4MockMvc.perform(post("/api/empresa4s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(empresa4)))
                .andExpect(status().isCreated());

        // Validate the Empresa4 in the database
        List<Empresa4> empresa4s = empresa4Repository.findAll();
        assertThat(empresa4s).hasSize(databaseSizeBeforeCreate + 1);
        Empresa4 testEmpresa4 = empresa4s.get(empresa4s.size() - 1);
        assertThat(testEmpresa4.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(empresa4Repository.findAll()).hasSize(0);
        // set the field null
        empresa4.setNome(null);

        // Create the Empresa4, which fails.
        restEmpresa4MockMvc.perform(post("/api/empresa4s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(empresa4)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Empresa4> empresa4s = empresa4Repository.findAll();
        assertThat(empresa4s).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllEmpresa4s() throws Exception {
        // Initialize the database
        empresa4Repository.saveAndFlush(empresa4);

        // Get all the empresa4s
        restEmpresa4MockMvc.perform(get("/api/empresa4s"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(empresa4.getId().intValue())))
                .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));
    }

    @Test
    @Transactional
    public void getEmpresa4() throws Exception {
        // Initialize the database
        empresa4Repository.saveAndFlush(empresa4);

        // Get the empresa4
        restEmpresa4MockMvc.perform(get("/api/empresa4s/{id}", empresa4.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(empresa4.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmpresa4() throws Exception {
        // Get the empresa4
        restEmpresa4MockMvc.perform(get("/api/empresa4s/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmpresa4() throws Exception {
        // Initialize the database
        empresa4Repository.saveAndFlush(empresa4);

		int databaseSizeBeforeUpdate = empresa4Repository.findAll().size();

        // Update the empresa4
        empresa4.setNome(UPDATED_NOME);
        restEmpresa4MockMvc.perform(put("/api/empresa4s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(empresa4)))
                .andExpect(status().isOk());

        // Validate the Empresa4 in the database
        List<Empresa4> empresa4s = empresa4Repository.findAll();
        assertThat(empresa4s).hasSize(databaseSizeBeforeUpdate);
        Empresa4 testEmpresa4 = empresa4s.get(empresa4s.size() - 1);
        assertThat(testEmpresa4.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void deleteEmpresa4() throws Exception {
        // Initialize the database
        empresa4Repository.saveAndFlush(empresa4);

		int databaseSizeBeforeDelete = empresa4Repository.findAll().size();

        // Get the empresa4
        restEmpresa4MockMvc.perform(delete("/api/empresa4s/{id}", empresa4.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Empresa4> empresa4s = empresa4Repository.findAll();
        assertThat(empresa4s).hasSize(databaseSizeBeforeDelete - 1);
    }
}
