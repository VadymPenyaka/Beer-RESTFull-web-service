package penyaka.petproject.spring_rest_web_mvc.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import penyaka.petproject.spring_rest_web_mvc.entities.Beer;

import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
