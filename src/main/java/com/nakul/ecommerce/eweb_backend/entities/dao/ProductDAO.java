package com.nakul.ecommerce.eweb_backend.entities.dao;

import com.nakul.ecommerce.eweb_backend.entities.Product;
import org.springframework.data.repository.ListCrudRepository;

/**
 * Data Access Object for accessing Product data.
 */
public interface ProductDAO extends ListCrudRepository<Product, Long> {
}