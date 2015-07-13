package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Empresa3;
import com.mycompany.myapp.repository.Empresa3Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Empresa3.
 */
@RestController
@RequestMapping("/api")
public class Empresa3Resource {

    private final Logger log = LoggerFactory.getLogger(Empresa3Resource.class);

    @Inject
    private Empresa3Repository empresa3Repository;

    /**
     * POST  /empresa3s -> Create a new empresa3.
     */
    @RequestMapping(value = "/empresa3s",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Empresa3 empresa3) throws URISyntaxException {
        log.debug("REST request to save Empresa3 : {}", empresa3);
        if (empresa3.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new empresa3 cannot already have an ID").build();
        }
        empresa3Repository.save(empresa3);
        return ResponseEntity.created(new URI("/api/empresa3s/" + empresa3.getId())).build();
    }

    /**
     * PUT  /empresa3s -> Updates an existing empresa3.
     */
    @RequestMapping(value = "/empresa3s",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Empresa3 empresa3) throws URISyntaxException {
        log.debug("REST request to update Empresa3 : {}", empresa3);
        if (empresa3.getId() == null) {
            return create(empresa3);
        }
        empresa3Repository.save(empresa3);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /empresa3s -> get all the empresa3s.
     */
    @RequestMapping(value = "/empresa3s",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Empresa3> getAll() {
        log.debug("REST request to get all Empresa3s");
        return empresa3Repository.findAll();
    }

    /**
     * GET  /empresa3s/:id -> get the "id" empresa3.
     */
    @RequestMapping(value = "/empresa3s/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Empresa3> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Empresa3 : {}", id);
        Empresa3 empresa3 = empresa3Repository.findOne(id);
        if (empresa3 == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(empresa3, HttpStatus.OK);
    }

    /**
     * DELETE  /empresa3s/:id -> delete the "id" empresa3.
     */
    @RequestMapping(value = "/empresa3s/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Empresa3 : {}", id);
        empresa3Repository.delete(id);
    }
}
