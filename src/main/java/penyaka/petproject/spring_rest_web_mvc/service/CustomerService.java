package penyaka.petproject.spring_rest_web_mvc.service;

import penyaka.petproject.spring_rest_web_mvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    Optional <CustomerDTO> getById(UUID id);
    List<CustomerDTO> getAllCustomers();

    CustomerDTO createCustomer(CustomerDTO customer);

    Optional<CustomerDTO> updateById(UUID id, CustomerDTO customer);

    boolean deleteByID(UUID id);

    void patchById(UUID id, CustomerDTO customer);
}
