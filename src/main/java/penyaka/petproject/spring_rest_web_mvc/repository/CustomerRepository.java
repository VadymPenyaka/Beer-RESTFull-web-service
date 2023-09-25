package penyaka.petproject.spring_rest_web_mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import penyaka.petproject.spring_rest_web_mvc.entity.Customer;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

}
