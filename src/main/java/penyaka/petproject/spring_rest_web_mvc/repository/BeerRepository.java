package penyaka.petproject.spring_rest_web_mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import penyaka.petproject.spring_rest_web_mvc.entity.Beer;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {

}
