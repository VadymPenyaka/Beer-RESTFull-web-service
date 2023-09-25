package penyaka.petproject.spring_rest_web_mvc.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import penyaka.petproject.spring_rest_web_mvc.entity.Beer;
import penyaka.petproject.spring_rest_web_mvc.entity.Customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerRepositoryTest {
    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeer () {
        Beer savedBeer = beerRepository
                .save(Beer.builder()
                        .name("New")
                .build());
        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }
}