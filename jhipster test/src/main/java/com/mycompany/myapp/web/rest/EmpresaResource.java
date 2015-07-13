package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Empresa;
import com.mycompany.myapp.repository.EmpresaRepository;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Empresa.
 */
@RestController
@RequestMapping("/api")
public class EmpresaResource {

    private final Logger log = LoggerFactory.getLogger(EmpresaResource.class);

    @Inject
    private EmpresaRepository empresaRepository;

    /**
     * POST  /empresas -> Create a new empresa.
     */
    @RequestMapping(value = "/empresas",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Empresa empresa) throws URISyntaxException {
        log.debug("REST request to save Empresa : {}", empresa);
        if (empresa.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new empresa cannot already have an ID").build();
        }
        empresaRepository.save(empresa);
        return ResponseEntity.created(new URI("/api/empresas/" + empresa.getId())).build();
    }

    /**
     * PUT  /empresas -> Updates an existing empresa.
     */
    @RequestMapping(value = "/empresas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Empresa empresa) throws URISyntaxException {
        log.debug("REST request to update Empresa : {}", empresa);
        if (empresa.getId() == null) {
            return create(empresa);
        }
        empresaRepository.save(empresa);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /empresas -> get all the empresas.
     */
    @RequestMapping(value = "/empresas",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Empresa>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Empresa> page = empresaRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/empresas", offset, limit);
        return new ResponseEntity<List<Empresa>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /empresas/:id -> get the "id" empresa.
     */
    @RequestMapping(value = "/empresas/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Empresa> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Empresa : {}", id);
        Empresa empresa = empresaRepository.findOne(id);
        if (empresa == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(empresa, HttpStatus.OK);
    }

    /**
     * DELETE  /empresas/:id -> delete the "id" empresa.
     */
    @RequestMapping(value = "/empresas/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Empresa : {}", id);
        empresaRepository.delete(id);
    }
}
