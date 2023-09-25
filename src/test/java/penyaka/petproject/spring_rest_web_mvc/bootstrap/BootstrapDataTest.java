package penyaka.petproject.spring_rest_web_mvc.bootstrap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import penyaka.petproject.spring_rest_web_mvc.repository.BeerRepository;
import penyaka.petproject.spring_rest_web_mvc.repository.CustomerRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BootstrapDataTest {
    @Autowired
    BeerRepository beerRepository;
    @Autowired
    CustomerRepository customerRepository;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp () {
       bootstrapData = new BootstrapData(beerRepository, customerRepository);
    }

    @Test
    void testRun() throws Exception {
        bootstrapData.run(null);

        assertThat(beerRepository.count()).isEqualTo(3);
        assertThat(customerRepository.count()).isEqualTo(3);
    }
}