package com.scaler.mslearning.Service;

import com.scaler.mslearning.Exception.CategoryNotFound;
import com.scaler.mslearning.Exception.ProductNotFound;
import com.scaler.mslearning.Models.Category;
import com.scaler.mslearning.Models.Product;
import com.scaler.mslearning.Repository.CategoryRepo;
import com.scaler.mslearning.Repository.ProductRepo;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Primary
@Service
public class SelfProductService implements ProductService{

    private ProductRepo productRepo;
    private CategoryRepo categoryRepo;

    public SelfProductService(ProductRepo productRepo, CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
    }


    @Override
    public Product getProductById(long id) throws ProductNotFound {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if(optionalProduct.isEmpty()) {
            throw new ProductNotFound("Product is not found!");
        }
        return optionalProduct.get();
    }

    @Override
    public Product[] getAllProducts() {
        List<Product> ListOfProducts = productRepo.findAll();

        Integer listSize = ListOfProducts.size();
        Product[] products = new Product[listSize];

        for(int i = 0; i < listSize; i++) {
            products[i] = ListOfProducts.get(i);
        }

        return products;
    }

    public Page<Product> getAllProducts(int pageNumber, int pageSize) {
        // Use pageable interface and pageRequest is the class where implements
        return productRepo.findAll(PageRequest.of(pageNumber, pageSize, Sort.by("price").descending()));
    }

    @Override
    public Product updateProduct(long id, Product product) throws ProductNotFound {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if(optionalProduct.isEmpty()) {
            throw new ProductNotFound("Product not Fouind");
        }
        Product existingProduct = optionalProduct.get();
        existingProduct.setTitle(product.getTitle());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setImage(product.getImage());

        Category category = existingProduct.getCategory();
        Category savedCategory;
        if (category != null && category.getTitle() != null) {
            Optional<Category> existingCategory = categoryRepo.findByTitle(category.getTitle());
            if(existingCategory.isPresent()) {
                savedCategory = categoryRepo.save(category);
            } else {
                savedCategory = existingCategory.get();
            }
        } else {
            throw new RuntimeException("Category should be required!");
        }

        existingProduct.setCategory(savedCategory);

        return productRepo.save(existingProduct);
    }

    @Override
    public Product createProduct(Product product) throws CategoryNotFound {
        Category category = product.getCategory();

        if(category.getId() == null ) {
            Category savedCategory = categoryRepo.save(category);
            product.setCategory(savedCategory);
        } else {
            Optional<Category> existingCategory = categoryRepo.findById(category.getId());
            if(existingCategory.isEmpty()) {
                throw new CategoryNotFound("Category not found!");
            }
            product.setCategory(existingCategory.get());
        }

        Product saveProduct = productRepo.save(product);
        // to return the created product
        Optional<Category> optionalCategory = categoryRepo.findById(category.getId());
        if(optionalCategory.isPresent()) {
            saveProduct.setCategory(optionalCategory.get());
        } else {
            throw new CategoryNotFound("Category not found...");
        }
        return saveProduct;
    }

    @Override
    public Product deleteProduct(long id) throws ProductNotFound {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if(optionalProduct.isEmpty()) {
            throw new ProductNotFound("Product is not Found");
        }
        Product product = optionalProduct.get();
        productRepo.deleteById(id);
        return product;
    }
}
