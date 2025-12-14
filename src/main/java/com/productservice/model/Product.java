package com.productservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "products")
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
    
    @Field("inventory_count")
    private Integer inventoryCount;
    
    @Field("created_at")
    private Instant createdAt;
    
    private List<String> tags = new ArrayList<>();
    private List<String> images = new ArrayList<>();
    
    // ✅ FIX: Added missing related product IDs field
    @Field("related_product_ids")
    private List<String> relatedProductIds = new ArrayList<>();
    
    @Field("is_deleted")
    private Boolean isDeleted = false;
    
    // Constructors
    public Product() {
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getBrand() {
        return brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getSku() {
        return sku;
    }
    
    public void setSku(String sku) {
        this.sku = sku;
    }
    
    public String getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    public Integer getInventoryCount() {
        return inventoryCount;
    }
    
    public void setInventoryCount(Integer inventoryCount) {
        this.inventoryCount = inventoryCount;
    }
    
    public Instant getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    
    public List<String> getTags() {
        return tags;
    }
    
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    
    public List<String> getImages() {
        return images;
    }
    
    public void setImages(List<String> images) {
        this.images = images;
    }
    
    public List<String> getRelatedProductIds() {
        return relatedProductIds;
    }
    
    public void setRelatedProductIds(List<String> relatedProductIds) {
        this.relatedProductIds = relatedProductIds;
    }
    
    public Boolean getIsDeleted() {
        return isDeleted != null ? isDeleted : false;
    }
    
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted != null ? isDeleted : false;
    }
}
