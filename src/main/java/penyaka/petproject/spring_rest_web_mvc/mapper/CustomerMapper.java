package penyaka.petproject.spring_rest_web_mvc.mapper;

import org.mapstruct.Mapper;
import penyaka.petproject.spring_rest_web_mvc.entity.Customer;
import penyaka.petproject.spring_rest_web_mvc.model.CustomerDTO;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer (CustomerDTO dto);
    CustomerDTO customerToCustomerDto (Customer customer);
}
