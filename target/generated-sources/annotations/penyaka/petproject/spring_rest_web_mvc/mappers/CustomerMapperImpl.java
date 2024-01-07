package penyaka.petproject.spring_rest_web_mvc.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import penyaka.petproject.spring_rest_web_mvc.entities.Customer;
import penyaka.petproject.spring_rest_web_mvc.model.CustomerDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-07T23:33:39+0200",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 19.0.2 (Amazon.com Inc.)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public Customer customerDtoToCustomer(CustomerDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Customer.CustomerBuilder customer = Customer.builder();

        customer.id( dto.getId() );
        customer.name( dto.getName() );
        customer.version( dto.getVersion() );
        customer.createDate( dto.getCreateDate() );
        customer.updateDate( dto.getUpdateDate() );

        return customer.build();
    }

    @Override
    public CustomerDTO customerToCustomerDto(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerDTO.CustomerDTOBuilder customerDTO = CustomerDTO.builder();

        customerDTO.id( customer.getId() );
        customerDTO.name( customer.getName() );
        customerDTO.version( customer.getVersion() );
        customerDTO.createDate( customer.getCreateDate() );
        customerDTO.updateDate( customer.getUpdateDate() );

        return customerDTO.build();
    }
}
