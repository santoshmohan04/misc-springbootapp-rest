package com.example.demo.controller;

import com.example.demo.model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")

public class ProductController {
    private List<Product> products = new ArrayList<>();

    public ProductController() {
        // Initialize with 30 dummy products
        for (int i = 1; i <= 30; i++) {
            products.add(new Product(i, "Product " + i, i * 10.0));
        }
    }

    // GET all products
    @GetMapping
    public List<Product> getAllProducts() {
        return products;
    }

    // GET single product
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // POST create new product
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        product.setId(products.size() + 1);
        products.add(product);
        return product;
    }

    // PUT update product
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable int id, @RequestBody Product updatedProduct) {
        for (Product product : products) {
            if (product.getId() == id) {
                product.setName(updatedProduct.getName());
                product.setPrice(updatedProduct.getPrice());
                return product;
            }
        }
        return null;
    }

    // DELETE product
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable int id) {
        boolean removed = products.removeIf(p -> p.getId() == id);
        return removed ? "Deleted successfully" : "Product not found";
    }
}
