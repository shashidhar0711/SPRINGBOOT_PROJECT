package com.scaler.mslearning.Service;

import com.scaler.mslearning.Exception.CategoryNotFound;
import com.scaler.mslearning.Exception.ProductNotFound;
import com.scaler.mslearning.Models.Product;

public interface ProductService {

    public Product getProductById(long id) throws ProductNotFound;

    public Product[] getAllProducts();

    public Product updateProduct(long id, Product product) throws ProductNotFound, CategoryNotFound;

    public Product createProduct(Product product) throws CategoryNotFound;

    public Product deleteProduct(long id) throws ProductNotFound;
}
