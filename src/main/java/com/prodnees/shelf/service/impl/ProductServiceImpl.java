package com.prodnees.shelf.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.prodnees.core.dto.ProductDto;
import com.prodnees.core.util.LocalAssert;
import com.prodnees.core.util.MapperUtil;
import com.prodnees.core.web.exception.NeesBadRequestException;
import com.prodnees.core.web.exception.NeesNotFoundException;
import com.prodnees.qc.domain.ValueType;
import com.prodnees.shelf.action.ProductMetadataService;
import com.prodnees.shelf.dao.ProductDao;
import com.prodnees.shelf.domain.ObjectAttribute;
import com.prodnees.shelf.domain.Product;
import com.prodnees.shelf.service.ProductService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    private final String PRICE_HISTORY_DATE = "date";
    private final String PRICE_HISTORY_PRICE = "price";
    private final ProductDao productDao;
    private final ProductMetadataService productMetadataService;

    public ProductServiceImpl(ProductDao productDao, ProductMetadataService productMetadataService) {
        this.productDao = productDao;
        this.productMetadataService = productMetadataService;
    }

    @Override
    public Product save(Product product) {
        return productDao.save(product);
    }

    @Override
    public Map<String, Object> addProductEx(Map<String, Object> requestBody) {
        List<ObjectAttribute> productAttributes = productMetadataService.getAllProductFields(false);
        Map<String, Object> product = new HashMap<>();
        for (ObjectAttribute objectAttribute : productAttributes) {
            String privateKey = objectAttribute.getPrivateKey();
            boolean isRequired = objectAttribute.isRequired();
            if (isRequired && requestBody.get(privateKey) == null) {
                throw new NeesBadRequestException(String.format("%s is a required field", privateKey));
            } else if (requestBody.get(privateKey) == null) {
                continue;
            }
            ValueType valueType = objectAttribute.getValueType();
            if (valueType.equals(ValueType.String)) {
                String value = requestBody.get(privateKey).toString();
            } else if (valueType.equals(ValueType.Number)) {
                LocalAssert.isTrue(requestBody.get(privateKey) instanceof Number,
                        String.format("%s field is of type %s. Invalid ValueType", privateKey, valueType));
                Number number = (Number) requestBody.get(privateKey);
            } else if (valueType.equals(ValueType.Boolean)) {
                Boolean bln = (Boolean) requestBody.get(privateKey);
            }
            product.put(privateKey, requestBody.get(privateKey));
        }
        return product;
    }

    @Override
    public Product addProduct(ProductDto dto) throws JsonProcessingException {
        Product product = MapperUtil.getDozer().map(dto, Product.class);
        List<Map<String, Object>> priceHistories = new ArrayList<>();
        Map<String, Object> priceHistory = new HashMap<>();
        priceHistory.put(PRICE_HISTORY_DATE, LocalDate.now().toString());
        priceHistory.put(PRICE_HISTORY_PRICE, dto.getPrice());
        priceHistories.add(priceHistory);
        product.setPriceHistoryJson(priceHistories)
                .setAddedDate(LocalDate.now());
        product = productDao.save(product);
        return product;
    }

    @Override
    public Product update(ProductDto dto) throws JsonProcessingException {
        Product product = getById(dto.getId());
        product.setName(dto.getName())
                .setDescription(dto.getDescription());
        if (dto.getPrice() > 0 && dto.getPrice() != product.getPrice()) {
            List<Map<String, Object>> productPriceHistories = product.getPriceHistoryJson();
            Map<String, Object> priceHistory = new HashMap<>();
            priceHistory.put(PRICE_HISTORY_DATE, LocalDate.now().toString());
            priceHistory.put(PRICE_HISTORY_PRICE, dto.getPrice());
            productPriceHistories.add(priceHistory);
            product.setPriceHistoryJson(productPriceHistories)
                    .setPrice(dto.getPrice());
        }
        return productDao.save(product);
    }

    @Override
    public Product getById(int id) {
        return productDao.findById(id)
                .orElseThrow(() -> new NeesNotFoundException(String.format("Product with id: %d not found", id)));
    }

    @Override
    public Product getByName(String name) {
        return productDao.getByName(name);
    }

    @Override
    public List<Product> getAllByIds(Iterable<Integer> productIdIterable) {
        return productDao.findAllById(productIdIterable);
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public void deleteById(int id) {
        productDao.deleteById(id);
    }

    @Override
    public boolean existsById(int id) {
        return productDao.existsById(id);
    }
}
