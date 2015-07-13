package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Empresa2;
import com.mycompany.myapp.repository.Empresa2Repository;

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
 * Test class for the Empresa2Resource REST controller.
 *
 * @see Empresa2Resource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Empresa2ResourceTest {

    private static final String DEFAULT_NOME = "SAMPLE_TEXT";
    private static final String UPDATED_NOME = "UPDATED_TEXT";

    @Inject
    private Empresa2Repository empresa2Repository;

    private MockMvc restEmpresa2MockMvc;

    private Empresa2 empresa2;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Empresa2Resource empresa2Resource = new Empresa2Resource();
        ReflectionTestUtils.setField(empresa2Resource, "empresa2Repository", empresa2Repository);
        this.restEmpresa2MockMvc = MockMvcBuilders.standaloneSetup(empresa2Resource).build();
    }

    @Before
    public void initTest() {
        empresa2 = new Empresa2();
        empresa2.setNome(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createEmpresa2() throws Exception {
        int databaseSizeBeforeCreate = empresa2Repository.findAll().size();

        // Create the Empresa2
        restEmpresa2MockMvc.perform(post("/api/empresa2s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(empresa2)))
                .andExpect(status().isCreated());

        // Validate the Empresa2 in the database
        List<Empresa2> empresa2s = empresa2Repository.findAll();
        assertThat(empresa2s).hasSize(databaseSizeBeforeCreate + 1);
        Empresa2 testEmpresa2 = empresa2s.get(empresa2s.size() - 1);
        assertThat(testEmpresa2.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(empresa2Repository.findAll()).hasSize(0);
        // set the field null
        empresa2.setNome(null);

        // Create the Empresa2, which fails.
        restEmpresa2MockMvc.perform(post("/api/empresa2s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(empresa2)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Empresa2> empresa2s = empresa2Repository.findAll();
        assertThat(empresa2s).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllEmpresa2s() throws Exception {
        // Initialize the database
        empresa2Repository.saveAndFlush(empresa2);

        // Get all the empresa2s
        restEmpresa2MockMvc.perform(get("/api/empresa2s"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(empresa2.getId().intValue())))
                .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));
    }

    @Test
    @Transactional
    public void getEmpresa2() throws Exception {
        // Initialize the database
        empresa2Repository.saveAndFlush(empresa2);

        // Get the empresa2
        restEmpresa2MockMvc.perform(get("/api/empresa2s/{id}", empresa2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(empresa2.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmpresa2() throws Exception {
        // Get the empresa2
        restEmpresa2MockMvc.perform(get("/api/empresa2s/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmpresa2() throws Exception {
        // Initialize the database
        empresa2Repository.saveAndFlush(empresa2);

		int databaseSizeBeforeUpdate = empresa2Repository.findAll().size();

        // Update the empresa2
        empresa2.setNome(UPDATED_NOME);
        restEmpresa2MockMvc.perform(put("/api/empresa2s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(empresa2)))
                .andExpect(status().isOk());

        // Validate the Empresa2 in the database
        List<Empresa2> empresa2s = empresa2Repository.findAll();
        assertThat(empresa2s).hasSize(databaseSizeBeforeUpdate);
        Empresa2 testEmpresa2 = empresa2s.get(empresa2s.size() - 1);
        assertThat(testEmpresa2.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void deleteEmpresa2() throws Exception {
        // Initialize the database
        empresa2Repository.saveAndFlush(empresa2);

		int databaseSizeBeforeDelete = empresa2Repository.findAll().size();

        // Get the empresa2
        restEmpresa2MockMvc.perform(delete("/api/empresa2s/{id}", empresa2.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Empresa2> empresa2s = empresa2Repository.findAll();
        assertThat(empresa2s).hasSize(databaseSizeBeforeDelete - 1);
    }
}
