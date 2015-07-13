package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Empresa3;
import com.mycompany.myapp.repository.Empresa3Repository;

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
 * Test class for the Empresa3Resource REST controller.
 *
 * @see Empresa3Resource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Empresa3ResourceTest {

    private static final String DEFAULT_NOME = "SAMPLE_TEXT";
    private static final String UPDATED_NOME = "UPDATED_TEXT";

    @Inject
    private Empresa3Repository empresa3Repository;

    private MockMvc restEmpresa3MockMvc;

    private Empresa3 empresa3;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Empresa3Resource empresa3Resource = new Empresa3Resource();
        ReflectionTestUtils.setField(empresa3Resource, "empresa3Repository", empresa3Repository);
        this.restEmpresa3MockMvc = MockMvcBuilders.standaloneSetup(empresa3Resource).build();
    }

    @Before
    public void initTest() {
        empresa3 = new Empresa3();
        empresa3.setNome(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createEmpresa3() throws Exception {
        int databaseSizeBeforeCreate = empresa3Repository.findAll().size();

        // Create the Empresa3
        restEmpresa3MockMvc.perform(post("/api/empresa3s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(empresa3)))
                .andExpect(status().isCreated());

        // Validate the Empresa3 in the database
        List<Empresa3> empresa3s = empresa3Repository.findAll();
        assertThat(empresa3s).hasSize(databaseSizeBeforeCreate + 1);
        Empresa3 testEmpresa3 = empresa3s.get(empresa3s.size() - 1);
        assertThat(testEmpresa3.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(empresa3Repository.findAll()).hasSize(0);
        // set the field null
        empresa3.setNome(null);

        // Create the Empresa3, which fails.
        restEmpresa3MockMvc.perform(post("/api/empresa3s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(empresa3)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Empresa3> empresa3s = empresa3Repository.findAll();
        assertThat(empresa3s).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllEmpresa3s() throws Exception {
        // Initialize the database
        empresa3Repository.saveAndFlush(empresa3);

        // Get all the empresa3s
        restEmpresa3MockMvc.perform(get("/api/empresa3s"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(empresa3.getId().intValue())))
                .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));
    }

    @Test
    @Transactional
    public void getEmpresa3() throws Exception {
        // Initialize the database
        empresa3Repository.saveAndFlush(empresa3);

        // Get the empresa3
        restEmpresa3MockMvc.perform(get("/api/empresa3s/{id}", empresa3.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(empresa3.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmpresa3() throws Exception {
        // Get the empresa3
        restEmpresa3MockMvc.perform(get("/api/empresa3s/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmpresa3() throws Exception {
        // Initialize the database
        empresa3Repository.saveAndFlush(empresa3);

		int databaseSizeBeforeUpdate = empresa3Repository.findAll().size();

        // Update the empresa3
        empresa3.setNome(UPDATED_NOME);
        restEmpresa3MockMvc.perform(put("/api/empresa3s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(empresa3)))
                .andExpect(status().isOk());

        // Validate the Empresa3 in the database
        List<Empresa3> empresa3s = empresa3Repository.findAll();
        assertThat(empresa3s).hasSize(databaseSizeBeforeUpdate);
        Empresa3 testEmpresa3 = empresa3s.get(empresa3s.size() - 1);
        assertThat(testEmpresa3.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void deleteEmpresa3() throws Exception {
        // Initialize the database
        empresa3Repository.saveAndFlush(empresa3);

		int databaseSizeBeforeDelete = empresa3Repository.findAll().size();

        // Get the empresa3
        restEmpresa3MockMvc.perform(delete("/api/empresa3s/{id}", empresa3.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Empresa3> empresa3s = empresa3Repository.findAll();
        assertThat(empresa3s).hasSize(databaseSizeBeforeDelete - 1);
    }
}
