package penyaka.petproject.spring_rest_web_mvc.services;

import org.springframework.data.domain.Page;
import penyaka.petproject.spring_rest_web_mvc.model.CustomerDTO;

import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    Optional<CustomerDTO> getCustomerById(UUID uuid);

    Page<CustomerDTO> getAllCustomers(Integer pageNumber, Integer pageSize);

    CustomerDTO saveNewCustomer(CustomerDTO customer);

    Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer);

    Boolean deleteCustomerById(UUID customerId);

    Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO customer);
}
