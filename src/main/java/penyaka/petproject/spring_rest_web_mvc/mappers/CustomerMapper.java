package penyaka.petproject.spring_rest_web_mvc.mappers;

import org.mapstruct.Mapper;
import penyaka.petproject.spring_rest_web_mvc.entities.Customer;
import penyaka.petproject.spring_rest_web_mvc.model.CustomerDTO;

/**
 * Created by jt, Spring Framework Guru.
 */
@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDto(Customer customer);

}
