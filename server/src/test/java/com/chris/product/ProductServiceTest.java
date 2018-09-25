package com.chris.product;

import com.chris.product.ProductApplicationTests;
import com.chris.product.dataobject.ProductInfo;
import com.chris.product.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.junit.Assert.*;

@Component
public class ProductServiceTest extends ProductApplicationTests {

    @Autowired
    private ProductService productService;
    @Test
    public void findUpAll() {
        List<ProductInfo> productInfos = productService.findUpAll();
        assertTrue(productInfos.size() > 0);

    }
}