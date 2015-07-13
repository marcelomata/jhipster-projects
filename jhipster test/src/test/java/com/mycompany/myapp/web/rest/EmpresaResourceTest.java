package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Empresa;
import com.mycompany.myapp.repository.EmpresaRepository;

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
 * Test class for the EmpresaResource REST controller.
 *
 * @see EmpresaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EmpresaResourceTest {

    private static final String DEFAULT_NOME = "SAMPLE_TEXT";
    private static final String UPDATED_NOME = "UPDATED_TEXT";

    @Inject
    private EmpresaRepository empresaRepository;

    private MockMvc restEmpresaMockMvc;

    private Empresa empresa;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmpresaResource empresaResource = new EmpresaResource();
        ReflectionTestUtils.setField(empresaResource, "empresaRepository", empresaRepository);
        this.restEmpresaMockMvc = MockMvcBuilders.standaloneSetup(empresaResource).build();
    }

    @Before
    public void initTest() {
        empresa = new Empresa();
        empresa.setNome(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createEmpresa() throws Exception {
        int databaseSizeBeforeCreate = empresaRepository.findAll().size();

        // Create the Empresa
        restEmpresaMockMvc.perform(post("/api/empresas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(empresa)))
                .andExpect(status().isCreated());

        // Validate the Empresa in the database
        List<Empresa> empresas = empresaRepository.findAll();
        assertThat(empresas).hasSize(databaseSizeBeforeCreate + 1);
        Empresa testEmpresa = empresas.get(empresas.size() - 1);
        assertThat(testEmpresa.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void getAllEmpresas() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresas
        restEmpresaMockMvc.perform(get("/api/empresas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(empresa.getId().intValue())))
                .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));
    }

    @Test
    @Transactional
    public void getEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get the empresa
        restEmpresaMockMvc.perform(get("/api/empresas/{id}", empresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(empresa.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmpresa() throws Exception {
        // Get the empresa
        restEmpresaMockMvc.perform(get("/api/empresas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

		int databaseSizeBeforeUpdate = empresaRepository.findAll().size();

        // Update the empresa
        empresa.setNome(UPDATED_NOME);
        restEmpresaMockMvc.perform(put("/api/empresas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(empresa)))
                .andExpect(status().isOk());

        // Validate the Empresa in the database
        List<Empresa> empresas = empresaRepository.findAll();
        assertThat(empresas).hasSize(databaseSizeBeforeUpdate);
        Empresa testEmpresa = empresas.get(empresas.size() - 1);
        assertThat(testEmpresa.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void deleteEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

		int databaseSizeBeforeDelete = empresaRepository.findAll().size();

        // Get the empresa
        restEmpresaMockMvc.perform(delete("/api/empresas/{id}", empresa.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Empresa> empresas = empresaRepository.findAll();
        assertThat(empresas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
