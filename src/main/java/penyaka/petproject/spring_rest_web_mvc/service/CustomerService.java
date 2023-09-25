package penyaka.petproject.spring_rest_web_mvc.service;

import penyaka.petproject.spring_rest_web_mvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    Optional <CustomerDTO> getById(UUID id);
    List<CustomerDTO> getAllCustomers();

    CustomerDTO createNewCustomer (CustomerDTO customer);

    void updateCustomer (UUID id, CustomerDTO customer);

    void delete (UUID id);

    void patchById(UUID id, CustomerDTO customer);
}
