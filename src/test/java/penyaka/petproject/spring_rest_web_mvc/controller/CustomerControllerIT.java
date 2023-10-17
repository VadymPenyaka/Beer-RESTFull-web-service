package penyaka.petproject.spring_rest_web_mvc.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import penyaka.petproject.spring_rest_web_mvc.entity.Customer;
import penyaka.petproject.spring_rest_web_mvc.exception.NotFoundException;
import penyaka.petproject.spring_rest_web_mvc.mapper.CustomerMapper;
import penyaka.petproject.spring_rest_web_mvc.model.CustomerDTO;
import penyaka.petproject.spring_rest_web_mvc.repository.CustomerRepository;

import java.util.List;
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

    @Test
    @Rollback
    @Transactional
    void deleteCustomerTest() {
        Customer customer = customerRepository.findAll().get(0);

        ResponseEntity responseEntity = customerController.deleteCusromer(customer.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(customerRepository.findById(customer.getId())).isEmpty();
    }

    @Test
    void deleteByIDNoTFoundTest() {
        assertThrows(NotFoundException.class, () ->
                customerController.deleteCusromer(UUID.randomUUID()));
    }

    @Test
    @Rollback
    @Transactional
    void updateByIdTest () {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDto = customerMapper.customerToCustomerDto(customer);
        final String newName = "UPDATED";
        customerDto.setName(newName);
        ResponseEntity responseEntity = customerController.updateCustomer(customer.getId(), customerDto);

        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(updatedCustomer.getName()).isEqualTo(newName);

    }

    @Test
    void updateByIdNotFoundTest () {
        assertThrows(NotFoundException.class, () ->
                customerController.updateCustomer(UUID.randomUUID(), CustomerDTO.builder().build())
        );
    }


    @Test
    @Transactional
    @Rollback
    void getEmptyListOfCustomersTest () {
        customerRepository.deleteAll();
        List<CustomerDTO> listCustomers = customerController.listCustomers();
        assertThat(listCustomers.size()).isEqualTo(0);
    }

    @Test
    @Transactional
    void getListOfCustomersTest () {
        List<CustomerDTO> listCustomers = customerController.listCustomers();
        assertThat(listCustomers.size()).isEqualTo(3);
    }

    @Test
    void getCustomerByID () {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());

        assertThat(customerDTO).isNotNull();
        assertThat(customerDTO.getId()).isEqualTo(customer.getId());
    }

    @Test
    void getCustomerBuIDNotFound () {
        assertThrows(NotFoundException.class, ()->
                customerController.getCustomerById(UUID.randomUUID()));
    }



}