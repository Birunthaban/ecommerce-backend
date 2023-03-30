package com.backend.ecommerce.repository;

import com.backend.ecommerce.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
