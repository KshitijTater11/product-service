package com.productservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String id;
    
    private String name;
    
    private String category;
    
    private String brand;
    
    private BigDecimal price;
    
    private String description;
    
    private String sku;
    
    @Field("created_by")
    private String createdBy;
    
    private List<String> tags = new ArrayList<>();
    private List<String> images = new ArrayList<>();
    
    // ✅ FIX: Added missing related product IDs field
    @Field("related_product_ids")
    private List<String> relatedProductIds = new ArrayList<>();
    
    @Field("is_deleted")
    private Boolean isDeleted = false;
    
    public Boolean getIsDeleted() {
        return isDeleted != null ? isDeleted : false;
    }
    
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted != null ? isDeleted : false;
    }
}
