package com.productservice.repository;

import com.productservice.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {
    
    @Query("{ 'is_deleted' : false, " +
            "'$and' : [ " +
            "  ?#{ [0] == null || [0].length() == 0 ? { $expr: true } : {'name': {$regex: [0], $options: 'i'}} }, " +
            "  ?#{ [1] == null || [1].length() == 0 ? { $expr: true } : {'category': {$regex: [1], $options: 'i'}} }, " +
            "  ?#{ [2] == null || [2].length() == 0 ? { $expr: true } : {'brand': {$regex: [2], $options: 'i'}} }, " +
            "  ?#{ [3] == null ? { $expr: true } : {'price': {$gte: [3]}} }, " +
            "  ?#{ [4] == null ? { $expr: true } : {'price': {$lte: [4]}} }, " +
            "  ?#{ [5] == null || [5].isEmpty() ? { $expr: true } : {'tags': {'$all': [5]}} } " +
            "]}")
    Page<Product> findByFilters(String name, 
                                String category,
                                String brand,
                                BigDecimal minPrice,
                                BigDecimal maxPrice,
                                List<String> tags,
                                Pageable pageable);

    List<Product> findByIsDeletedFalse();

    Optional<Product> findByIdAndIsDeletedFalse(String id);
}