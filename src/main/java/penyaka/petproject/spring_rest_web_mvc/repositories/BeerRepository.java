package penyaka.petproject.spring_rest_web_mvc.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import penyaka.petproject.spring_rest_web_mvc.entities.Beer;
import penyaka.petproject.spring_rest_web_mvc.model.BeerStyle;

import java.util.List;
import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
    List<Beer> findAllByNameLikeIgnoreCase (String beerName);

    List<Beer> findAllByStyle (BeerStyle beerStyle);

    List<Beer> findAllByNameLikeIgnoreCaseAndStyle (String beerName, BeerStyle beerStyle);
}
