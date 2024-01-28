package penyaka.petproject.spring_rest_web_mvc.repositories;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import penyaka.petproject.spring_rest_web_mvc.bootstrap.BootstrapData;
import penyaka.petproject.spring_rest_web_mvc.entities.Beer;
import penyaka.petproject.spring_rest_web_mvc.model.BeerStyle;
import penyaka.petproject.spring_rest_web_mvc.services.BeerCSVServiceImpl;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import({BootstrapData.class, BeerCSVServiceImpl.class})
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void estGetBeerListByNameIsLikeIgnoreCamelCase () {
        Page<Beer> beerList = beerRepository.findAllByNameLikeIgnoreCase("%IPA%", null);

        assertThat(beerList.getContent().size()).isEqualTo(336);
    }

    @Test
    void testSaveBeerNameTooLong() {

        assertThrows(ConstraintViolationException.class, () -> {
            Beer savedBeer = beerRepository.save(Beer.builder()
                    .name("My Beer 0123345678901233456789012334567890123345678901233456789012334567890123345678901233456789")
                    .style(BeerStyle.PALE_ALE)
                    .upc("234234234234")
                    .price(new BigDecimal("11.99"))
                    .build());

            beerRepository.flush();
        });
    }

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                        .name("My Beer")
                        .style(BeerStyle.PALE_ALE)
                        .upc("234234234234")
                        .price(new BigDecimal("11.99"))
                .build());

        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }
}