package com.chris.product.service.impl;

import com.chris.product.common.DecreaseStockInput;
import com.chris.product.dataobject.ProductInfo;
import com.chris.product.enums.ProductStatusEnum;
import com.chris.product.enums.ResultEnum;
import com.chris.product.exception.ProductException;
import com.chris.product.repository.ProductInfoRepository;
import com.chris.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfo> findList(List<String> productIdList) {
        return productInfoRepository.findByProductIdIn(productIdList);
    }

    @Override
    @Transactional
    public void decreaseStock(List<DecreaseStockInput> decreaseStockInputList) {
        for (DecreaseStockInput decreaseStockInput : decreaseStockInputList) {
            Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(decreaseStockInput.getProductId());
                if (!productInfoOptional.isPresent()) {
                    throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
                }

                ProductInfo productInfo = productInfoOptional.get();
                Integer result = productInfo.getProductStock() - decreaseStockInput.getProductQuantity();
                if (result < 0) {
                    throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
                }
                productInfo.setProductStock(result);
                productInfoRepository.save(productInfo);
        }
    }
}
