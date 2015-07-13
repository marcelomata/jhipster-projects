package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Empresa2;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Empresa2 entity.
 */
public interface Empresa2Repository extends JpaRepository<Empresa2,Long> {

}
