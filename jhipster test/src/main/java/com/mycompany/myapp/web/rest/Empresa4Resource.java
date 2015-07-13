package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Empresa4;
import com.mycompany.myapp.repository.Empresa4Repository;
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
 * REST controller for managing Empresa4.
 */
@RestController
@RequestMapping("/api")
public class Empresa4Resource {

    private final Logger log = LoggerFactory.getLogger(Empresa4Resource.class);

    @Inject
    private Empresa4Repository empresa4Repository;

    /**
     * POST  /empresa4s -> Create a new empresa4.
     */
    @RequestMapping(value = "/empresa4s",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Empresa4 empresa4) throws URISyntaxException {
        log.debug("REST request to save Empresa4 : {}", empresa4);
        if (empresa4.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new empresa4 cannot already have an ID").build();
        }
        empresa4Repository.save(empresa4);
        return ResponseEntity.created(new URI("/api/empresa4s/" + empresa4.getId())).build();
    }

    /**
     * PUT  /empresa4s -> Updates an existing empresa4.
     */
    @RequestMapping(value = "/empresa4s",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Empresa4 empresa4) throws URISyntaxException {
        log.debug("REST request to update Empresa4 : {}", empresa4);
        if (empresa4.getId() == null) {
            return create(empresa4);
        }
        empresa4Repository.save(empresa4);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /empresa4s -> get all the empresa4s.
     */
    @RequestMapping(value = "/empresa4s",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Empresa4> getAll() {
        log.debug("REST request to get all Empresa4s");
        return empresa4Repository.findAll();
    }

    /**
     * GET  /empresa4s/:id -> get the "id" empresa4.
     */
    @RequestMapping(value = "/empresa4s/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Empresa4> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Empresa4 : {}", id);
        Empresa4 empresa4 = empresa4Repository.findOne(id);
        if (empresa4 == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(empresa4, HttpStatus.OK);
    }

    /**
     * DELETE  /empresa4s/:id -> delete the "id" empresa4.
     */
    @RequestMapping(value = "/empresa4s/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Empresa4 : {}", id);
        empresa4Repository.delete(id);
    }
}
