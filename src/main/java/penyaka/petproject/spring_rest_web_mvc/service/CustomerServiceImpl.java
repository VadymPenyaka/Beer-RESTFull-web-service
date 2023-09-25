package penyaka.petproject.spring_rest_web_mvc.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import penyaka.petproject.spring_rest_web_mvc.model.CustomerDTO;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {
    private Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        CustomerDTO customer1 = CustomerDTO
                .builder()
                .name("1")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
        CustomerDTO customer2 = CustomerDTO
                .builder()
                .name("2")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
    }

    @Override
    public Optional <CustomerDTO> getById(UUID id) {
        return Optional.of(customerMap.get(id));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customer) {
        CustomerDTO savedCustomer = CustomerDTO.builder()
                .name(customer.getName())
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .version(customer.getVersion())
                .build();
        customerMap.put(savedCustomer.getId(), savedCustomer);

        return savedCustomer;
    }

    @Override
    public void updateCustomer(UUID id, CustomerDTO customer) {
        CustomerDTO existing = customerMap.get(id);
        existing.setName(customer.getName());
        existing.setCreatedDate(customer.getCreatedDate());
        existing.setLastModifiedDate(LocalDateTime.now());
        existing.setVersion(customer.getVersion());

        customerMap.put(existing.getId(), existing);
    }

    @Override
    public void delete(UUID id) {
        customerMap.remove(id);
    }

    @Override
    public void patchById(UUID id, CustomerDTO customer) {
        CustomerDTO existing = customerMap.get(id);

        if (StringUtils.hasText(customer.getName()))
            existing.setName(customer.getName());
        if (customer.getVersion() != null)
            existing.setVersion(customer.getVersion());

        existing.setLastModifiedDate(LocalDateTime.now());

        customerMap.put(existing.getId(), existing);
    }
}
