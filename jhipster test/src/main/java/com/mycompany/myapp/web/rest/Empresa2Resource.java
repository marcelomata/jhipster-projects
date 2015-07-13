package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Empresa2;
import com.mycompany.myapp.repository.Empresa2Repository;
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
 * REST controller for managing Empresa2.
 */
@RestController
@RequestMapping("/api")
public class Empresa2Resource {

    private final Logger log = LoggerFactory.getLogger(Empresa2Resource.class);

    @Inject
    private Empresa2Repository empresa2Repository;

    /**
     * POST  /empresa2s -> Create a new empresa2.
     */
    @RequestMapping(value = "/empresa2s",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Empresa2 empresa2) throws URISyntaxException {
        log.debug("REST request to save Empresa2 : {}", empresa2);
        if (empresa2.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new empresa2 cannot already have an ID").build();
        }
        empresa2Repository.save(empresa2);
        return ResponseEntity.created(new URI("/api/empresa2s/" + empresa2.getId())).build();
    }

    /**
     * PUT  /empresa2s -> Updates an existing empresa2.
     */
    @RequestMapping(value = "/empresa2s",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Empresa2 empresa2) throws URISyntaxException {
        log.debug("REST request to update Empresa2 : {}", empresa2);
        if (empresa2.getId() == null) {
            return create(empresa2);
        }
        empresa2Repository.save(empresa2);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /empresa2s -> get all the empresa2s.
     */
    @RequestMapping(value = "/empresa2s",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Empresa2> getAll() {
        log.debug("REST request to get all Empresa2s");
        return empresa2Repository.findAll();
    }

    /**
     * GET  /empresa2s/:id -> get the "id" empresa2.
     */
    @RequestMapping(value = "/empresa2s/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Empresa2> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Empresa2 : {}", id);
        Empresa2 empresa2 = empresa2Repository.findOne(id);
        if (empresa2 == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(empresa2, HttpStatus.OK);
    }

    /**
     * DELETE  /empresa2s/:id -> delete the "id" empresa2.
     */
    @RequestMapping(value = "/empresa2s/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Empresa2 : {}", id);
        empresa2Repository.delete(id);
    }
}
