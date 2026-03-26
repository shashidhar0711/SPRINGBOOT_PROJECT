package com.scaler.mslearning.Repository;

import com.fasterxml.jackson.annotation.OptBoolean;
import com.scaler.mslearning.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Product save(Product product);

    Optional<Product> findById(long id);

    List<Product> findAll();

}
