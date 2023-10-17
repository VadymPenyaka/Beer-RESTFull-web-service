package penyaka.petproject.spring_rest_web_mvc.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import penyaka.petproject.spring_rest_web_mvc.entity.Beer;
import penyaka.petproject.spring_rest_web_mvc.exception.NotFoundException;
import penyaka.petproject.spring_rest_web_mvc.mapper.BeerMapper;
import penyaka.petproject.spring_rest_web_mvc.model.BeerDTO;
import penyaka.petproject.spring_rest_web_mvc.repository.BeerRepository;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BeerControllerIT {
    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Test
    void updateBeerNotFoundTest () {
        assertThrows(NotFoundException.class, () ->
                beerController.updateById(UUID.randomUUID(), BeerDTO.builder().build()));

    }

    @Test
    @Rollback
    @Transactional
    void deleteByIdTest () {
        Beer beer = beerRepository.findAll().get(0);

        ResponseEntity responseEntity = beerController.deleteByID(beer.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(beerRepository.findById(beer.getId()).isEmpty());
    }

    @Test
    @Rollback
    @Transactional
    void updateBeerTest () {
        Beer beer = beerRepository.findAll().get(0);

        BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);
        final String beerName = "UPDATED";
        beerDTO.setName(beerName);
        ResponseEntity responseEntity = beerController.updateById(beer.getId(), beerDTO);

        Beer updatedBeer = beerRepository.findById(beer.getId()).get();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(updatedBeer.getName()).isEqualTo(beerName);
    }

    @Test
    @Rollback
    @Transactional
    void createBeerTest() {
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("New Beer")
                .build();

        ResponseEntity responseEntity =beerController.create(beerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Beer beer =beerRepository.findById(savedUUID).get();
        assertThat(beer).isNotNull();
    }

    @Test
    void testGerBeerById() {
        Beer beer = beerRepository.findAll().get(0);

        BeerDTO beerDTO = beerController.gerBeerById(beer.getId());

        assertThat(beerDTO).isNotNull();
        assertThat(beerDTO.getId()).isEqualTo(beer.getId());
    }

    @Test
    void testGerBeerByIdNotFound() {
        assertThrows(NotFoundException.class, ()->{
            beerController.gerBeerById(UUID.randomUUID());
        });
    }

    @Test
    @Transactional
    void testGetListOfBeers() {
        List<BeerDTO> beerList = beerController.listBeers();

        assertThat(beerList.size()).isEqualTo(3);
    }

    @Test
    @Rollback
    @Transactional
    void testGetEmptyListOfBeers() {
        beerRepository.deleteAll();
        List<BeerDTO> beerList = beerController.listBeers();

        assertThat(beerList.size()).isEqualTo(0);
    }


}