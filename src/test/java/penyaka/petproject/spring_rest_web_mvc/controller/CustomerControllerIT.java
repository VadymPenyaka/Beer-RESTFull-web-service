package penyaka.petproject.spring_rest_web_mvc.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import penyaka.petproject.spring_rest_web_mvc.entities.Customer;
import penyaka.petproject.spring_rest_web_mvc.mappers.CustomerMapper;
import penyaka.petproject.spring_rest_web_mvc.model.CustomerDTO;
import penyaka.petproject.spring_rest_web_mvc.repositories.CustomerRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerMapper customerMapper;

    @Rollback
    @Transactional
    @Test
    void deleteByIdFound() {
        Customer customer = customerRepository.findAll().get(0);

        ResponseEntity responseEntity = customerController.deleteCustomerById(customer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(customerRepository.findById(customer.getId()).isEmpty());
    }

    @Test
    void testDeleteNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.deleteCustomerById(UUID.randomUUID());
        });
    }

    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.updateCustomerByID(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void updateExistingBeer() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        customerDTO.setId(null);
        customerDTO.setVersion(null);
        final String customerName = "UPDATED";
        customerDTO.setName(customerName);

        ResponseEntity responseEntity = customerController.updateCustomerByID(customer.getId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(updatedCustomer.getName()).isEqualTo(customerName);
    }

    @Rollback
    @Transactional
    @Test
    void saveNewBeerTest() {
       CustomerDTO customerDTO = CustomerDTO.builder()
               .name("TEST")
               .build();

        ResponseEntity responseEntity = customerController.saveNewBeer(customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Customer customer = customerRepository.findById(savedUUID).get();
        assertThat(customer).isNotNull();
    }

    @Rollback
    @Transactional
    @Test
    void testListAllEmptyList() {
        customerRepository.deleteAll();
        Page<CustomerDTO> dtos = customerController.getAllCustomers(1, 25);

        assertThat(dtos.getContent().size()).isEqualTo(0);
    }

    @Test
    void testListAll() {
        Page<CustomerDTO> dtos = customerController.getAllCustomers(1, 25);

        assertThat(dtos.getContent().size()).isEqualTo(25);
    }

    @Test
    void testGetByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());
        });
    }

    @Test
    void testGetById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());
        assertThat(customerDTO).isNotNull();
    }
}










