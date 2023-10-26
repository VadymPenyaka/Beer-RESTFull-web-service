package penyaka.petproject.spring_rest_web_mvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import penyaka.petproject.spring_rest_web_mvc.exception.NotFoundException;
import penyaka.petproject.spring_rest_web_mvc.model.CustomerDTO;
import penyaka.petproject.spring_rest_web_mvc.service.CustomerService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    public final static String CUSTOMER_PATH = "/api/v1/customer";
    public final static String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{id}";

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity patchCustomerById (@PathVariable("id") UUID id, @Validated @RequestBody CustomerDTO customer) {
        customerService.patchById(id, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity deleteCusromer (@PathVariable("id") UUID id) {
        if (!customerService.deleteByID(id))
            throw new NotFoundException();
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity updateCustomer (@PathVariable("id") UUID id, @Validated @RequestBody CustomerDTO customer) {
        if(customerService.updateById(id, customer).isEmpty())
            throw new NotFoundException();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity<CustomerDTO> createNewCustomer (@Validated @RequestBody CustomerDTO customer) {
        CustomerDTO savedCustomer = customerService.createCustomer(customer);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", CUSTOMER_PATH + "/" + savedCustomer.getId().toString());

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

//    @RequestMapping(value = "{customerId}", method = RequestMethod.GET)
    @GetMapping(CUSTOMER_PATH_ID)
    public CustomerDTO getCustomerById (@PathVariable("id") UUID id) {
        return customerService.getById(id).orElseThrow(NotFoundException::new);
    }

    @GetMapping(CUSTOMER_PATH)
    public List<CustomerDTO> listCustomers () {
        return customerService.getAllCustomers();
    }
}
