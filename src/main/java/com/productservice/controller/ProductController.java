package com.productservice.controller;

import com.productservice.model.Product;
import com.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.productservice.service.FileStorageService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private FileStorageService fileStorageService;

    /**
     * POST /api/products
     * Creates a new product.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    /**
     * GET /api/products
     * Retrieves products with filters, sorting, and pagination.
     */
    @GetMapping
    public Page<Product> listProducts(@RequestParam(required = false) String name,
                                      @RequestParam(required = false) String category,
                                      @RequestParam(required = false) String brand,
                                      @RequestParam(required = false) BigDecimal minPrice,
                                      @RequestParam(required = false) BigDecimal maxPrice,
                                      @RequestParam(required = false) List<String> tags,
                                      @RequestParam(defaultValue = "id") String sortBy,
                                      @RequestParam(defaultValue = "ASC") String order,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        Sort sort = Sort.by(Sort.Direction.fromString(order), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findByFilters(name, category, brand, minPrice, maxPrice, tags, pageable);
    }
    
    /**
     * GET /api/products/{id}
     * Retrieves a single product by its unique ID.
     */
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id) {
        return productRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with ID " + id + " not found."));
    }

    /**
     * PUT /api/products/{id}
     * Updates an existing product.
     */
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product productUpdate) {
        return productRepository.findByIdAndIsDeletedFalse(id)
                .map(existingProduct -> {
                    Optional.ofNullable(productUpdate.getName()).ifPresent(existingProduct::setName);
                    Optional.ofNullable(productUpdate.getCategory()).ifPresent(existingProduct::setCategory);
                    Optional.ofNullable(productUpdate.getBrand()).ifPresent(existingProduct::setBrand);
                    Optional.ofNullable(productUpdate.getPrice()).ifPresent(existingProduct::setPrice);
                    Optional.ofNullable(productUpdate.getSku()).ifPresent(existingProduct::setSku);
                    Optional.ofNullable(productUpdate.getTags()).ifPresent(existingProduct::setTags);
                    Optional.ofNullable(productUpdate.getImages()).ifPresent(existingProduct::setImages);
                    Optional.ofNullable(productUpdate.getRelatedProductIds()).ifPresent(existingProduct::setRelatedProductIds);
                    return productRepository.save(existingProduct);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with ID " + id + " not found."));
    }

    /**
     * DELETE /api/products/{id}
     * Soft-deletes a product by setting a flag.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable String id) {
        productRepository.findByIdAndIsDeletedFalse(id)
                .ifPresentOrElse(product -> {
                    product.setIsDeleted(true);
                    productRepository.save(product);
                }, () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with ID " + id + " not found.");
                });
    }

    // ✅ NEW: Upload images for product
    @PostMapping("/{id}/images")
    public Product uploadProductImages(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        Product product = productRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with ID " + id + " not found."));

        try {
            String imageUrl = fileStorageService.saveFile(file);
            if (product.getImages() == null) {
                product.setImages(new ArrayList<>());
            }
            product.getImages().add(imageUrl);
            return productRepository.save(product);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload image.", e);
        }
    }

    /**
     * POST /api/products/{id}/related
     * Defines a list of related product IDs for a specific product.
     */
    @PostMapping("/{id}/related")
    public Product linkRelatedProducts(@PathVariable String id, @RequestBody List<String> relatedProductIds) {
        return productRepository.findById(id)
                .map(product -> {
                    // Here you might add validation to ensure relatedProductIds exist and are not the product itself.
                    product.setRelatedProductIds(relatedProductIds);
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with ID " + id + " not found."));
    }
    
}