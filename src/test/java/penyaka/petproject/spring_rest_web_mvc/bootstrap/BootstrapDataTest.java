package penyaka.petproject.spring_rest_web_mvc.bootstrap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import penyaka.petproject.spring_rest_web_mvc.repositories.BeerRepository;
import penyaka.petproject.spring_rest_web_mvc.repositories.CustomerRepository;
import penyaka.petproject.spring_rest_web_mvc.services.BeerCSVService;
import penyaka.petproject.spring_rest_web_mvc.services.BeerCSVServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BeerCSVServiceImpl.class)
class BootstrapDataTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerCSVService beerCSVService;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(beerRepository, customerRepository, beerCSVService);
    }

    @Test
    void Testrun() throws Exception {
        bootstrapData.run(null);

        assertThat(beerRepository.count()).isEqualTo(2413);
        assertThat(customerRepository.count()).isEqualTo(100);
    }
}





