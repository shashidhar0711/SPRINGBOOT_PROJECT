package com.scaler.mslearning.Controller;

import com.scaler.mslearning.Exception.CategoryNotFound;
import com.scaler.mslearning.Exception.ProductNotFound;
import com.scaler.mslearning.Models.Product;
import com.scaler.mslearning.Service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService ps;

    ProductController( ProductService ps) {
        this.ps = ps;
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") long id) throws ProductNotFound {
        return ps.getProductById(id);
    }

    @GetMapping()
    public List<Product> getAllProduct() {
        return List.of(ps.getAllProducts());
    }

    @PutMapping("/{id}")
    public Product putUpdate(@PathVariable("id") long id, @RequestBody Product product) throws ProductNotFound, CategoryNotFound {
        return ps.updateProduct(id,product);
    }

    @DeleteMapping("/{id}")
    public Product deleteProduct(@PathVariable("id") long id) throws ProductNotFound {
        return ps.deleteProduct(id);
    }


    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) throws Exception {
        Product createdProduct = ps.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.OK);
    }
}
