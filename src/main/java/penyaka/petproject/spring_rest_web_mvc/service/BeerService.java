package penyaka.petproject.spring_rest_web_mvc.service;

import penyaka.petproject.spring_rest_web_mvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    List<BeerDTO> getAllBeers();

    Optional<BeerDTO> getBeerById (UUID id);

    BeerDTO save(BeerDTO beer);

    Optional<BeerDTO> updateById(UUID id, BeerDTO beer);

    boolean deleteById(UUID id);

    void patchById (UUID id, BeerDTO beer);

}
