package penyaka.petproject.spring_rest_web_mvc.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import penyaka.petproject.spring_rest_web_mvc.entity.Customer;
import penyaka.petproject.spring_rest_web_mvc.model.CustomerDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-26T15:37:24+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.2 (Amazon.com Inc.)"
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
        customer.version( dto.getVersion() );
        customer.name( dto.getName() );
        customer.createdDate( dto.getCreatedDate() );
        customer.lastModifiedDate( dto.getLastModifiedDate() );

        return customer.build();
    }

    @Override
    public CustomerDTO customerToCustomerDto(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerDTO.CustomerDTOBuilder customerDTO = CustomerDTO.builder();

        customerDTO.id( customer.getId() );
        customerDTO.version( customer.getVersion() );
        customerDTO.name( customer.getName() );
        customerDTO.createdDate( customer.getCreatedDate() );
        customerDTO.lastModifiedDate( customer.getLastModifiedDate() );

        return customerDTO.build();
    }
}
