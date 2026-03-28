package com.scaler.mslearning.Service;

import com.scaler.mslearning.Dto.FakeStoreRequestDto;
import com.scaler.mslearning.Dto.FakeStoreResponseDto;
import com.scaler.mslearning.Models.Category;
import com.scaler.mslearning.Models.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FakeStoreService implements ProductService{

    private RestTemplate restTemplate;

    public FakeStoreService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductById(long id) {
        FakeStoreResponseDto fakeStoreResponseDto = restTemplate.getForObject("https://fakestoreapi.com/products/"+id, FakeStoreResponseDto.class);
        
        if(fakeStoreResponseDto == null) {
            return null;
        }
        
        return ConvertFakeStoreDtoIntoProduct(fakeStoreResponseDto);
    }

    private Product ConvertFakeStoreDtoIntoProduct(FakeStoreResponseDto fdto) {
        Product p = new Product();
        p.setId(fdto.getId());
        p.setDescription(fdto.getDescription());
        p.setTitle(fdto.getTitle());
        p.setPrice(fdto.getPrice());
        p.setImage(fdto.getImage());

        String incomingCategory = fdto.getCategory();
        if(incomingCategory != null) {
            Category c = new Category();
            c.setTitle(incomingCategory);
            p.setCategory(c);
        }

        return p;
    }

    @Override
    public Product[] getAllProducts() {
        FakeStoreResponseDto[] responseDto = restTemplate.getForObject("https://fakestoreapi.com/products/", FakeStoreResponseDto[].class);

        Product[] products = new Product[responseDto.length];

        for(int i = 0; i < responseDto.length; i++) {
            products[i] = ConvertFakeStoreDtoIntoProduct(responseDto[i]);

        }
        return products;
    }

    @Override
    public Page<Product> getAllProducts(int pageNumber, int pageSize) {
        return null;
    }

    public FakeStoreRequestDto ConvertProductToFakeStoreRequest(Product product) {
        FakeStoreRequestDto fakeStoreRequestDto = new FakeStoreRequestDto();
        fakeStoreRequestDto.setTitle(product.getTitle());
        fakeStoreRequestDto.setDescription(product.getDescription());
        fakeStoreRequestDto.setPrice(product.getPrice());
        fakeStoreRequestDto.setImage(product.getImage());
        fakeStoreRequestDto.setCategory(product.getCategory().getTitle());

        return fakeStoreRequestDto;
    }

    @Override
    public Product updateProduct(long id, Product product) {
        FakeStoreRequestDto requestProduct = ConvertProductToFakeStoreRequest(product);
        restTemplate.put("https://fakestoreapi.com/products/"+id, requestProduct);
        return getProductById(id);
    }

    @Override
    public Product createProduct(Product product) {
        FakeStoreRequestDto requestProduct = ConvertProductToFakeStoreRequest(product);
        FakeStoreResponseDto response = restTemplate.postForObject("https://fakestoreapi.com/products/",requestProduct, FakeStoreResponseDto.class);
        if(response == null) {
            throw new RuntimeException("product is null");
        }

        return ConvertFakeStoreDtoIntoProduct(response);
    }

    @Override
    public Product deleteProduct(long id){
        Product product = getProductById(id);
        restTemplate.delete("https://fakestoreapi.com/products/"+id);

        return product;


    }


}
