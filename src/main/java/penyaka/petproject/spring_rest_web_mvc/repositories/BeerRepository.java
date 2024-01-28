package penyaka.petproject.spring_rest_web_mvc.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import penyaka.petproject.spring_rest_web_mvc.entities.Beer;
import penyaka.petproject.spring_rest_web_mvc.model.BeerStyle;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
    Page<Beer> findAllByNameLikeIgnoreCase (String beerName, Pageable pageable);

    Page<Beer> findAllByStyle (BeerStyle beerStyle, Pageable pageable);

    Page<Beer> findAllByNameLikeIgnoreCaseAndStyle (String beerName, BeerStyle beerStyle, Pageable pageable);
}
