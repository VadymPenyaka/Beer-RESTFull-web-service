package penyaka.petproject.spring_rest_web_mvc.services;
import org.springframework.data.domain.Page;
import penyaka.petproject.spring_rest_web_mvc.model.BeerDTO;
import penyaka.petproject.spring_rest_web_mvc.model.BeerStyle;

import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    Page<BeerDTO> getAllBeers(String beerName, BeerStyle beerStyle, Boolean getAmount, Integer pageNumber, Integer pageSize);

    Optional<BeerDTO> getBeerById(UUID id);

    BeerDTO saveNewBeer(BeerDTO beer);

    Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer);

    Boolean deleteBeerById(UUID beerId);

    Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer);
}
