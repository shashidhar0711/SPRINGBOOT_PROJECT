package com.scaler.mslearning.Controller;

import com.scaler.mslearning.Exception.CategoryNotFound;
import com.scaler.mslearning.Exception.ProductNotFound;
import com.scaler.mslearning.Models.Product;
import com.scaler.mslearning.Service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import javax.print.attribute.standard.Media;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;


    @Test
    void TestGetProductById() throws Exception {
        Product product = new Product();
        product.setTitle("Laptop");
        product.setId(1L);

        when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Laptop"));
    }

    @Test
    void TestGetProductById_notFound() throws Exception {
        when(productService.getProductById(1L)).thenThrow(new ProductNotFound("Product Not Found!"));

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void TestCreateProuctSuccess() throws Exception {
        Product input = new Product();
        input.setId(1L);
        input.setTitle("Laptop");

        Product output = new Product();
        output.setId(1L);
        output.setTitle("Laptop");

        when(productService.createProduct(any(Product.class))).thenReturn(output);

        mockMvc.perform(post("/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Laptop"));
    }

    @Test
    void TestDeleteProductSuccess() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Laptop");

        when(productService.deleteProduct(1)).thenReturn(product);

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Laptop"));
    }

    @Test
    void TestDeleteProductException() throws Exception {

        when(productService.deleteProduct(1)).thenThrow(new ProductNotFound("Product is Not Available"));

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void TestGetAllProductSuccess() throws Exception {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setTitle("Laptop");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setTitle("Computer");

        Product[] arrayOfProducts = new Product[2];
        arrayOfProducts[0] = product1;
        arrayOfProducts[1] = product2;

        when(productService.getAllProducts()).thenReturn(arrayOfProducts);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$.[0].title").value("Laptop"))
                .andExpect(jsonPath("$.[1].title").value("Computer"));
    }

    @Test
    void TestGetAllProductEmpty() throws Exception {
        Product[] emptyArray = new Product[0];
        when(productService.getAllProducts()).thenReturn(emptyArray);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }

    @Test
    void TestUpdateProductSuccess() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Laptop");

        when(productService.updateProduct(eq(1L) ,any(Product.class))).thenReturn(product);

        mockMvc.perform(put("/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Laptop"));
    }

    @Test
    void TestUpdateProductException() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Laptop");

        when(productService.updateProduct(eq(1L) ,any(Product.class))).thenThrow(new ProductNotFound("Product is not found!"));

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isNotFound());
    }

}