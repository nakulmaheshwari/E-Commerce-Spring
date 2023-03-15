package com.nakul.ecommerce.eweb_backend.api.controller.product;

import com.nakul.ecommerce.eweb_backend.entities.Product;
import com.nakul.ecommerce.eweb_backend.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller to handle the creation, updating & viewing of products.
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    /** The Product Service. */
    private ProductService productService;

    /**
     * Constructor for spring injection.
     * @param productService
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Gets the list of products available.
     * @return The list of products.
     */
    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }

}
