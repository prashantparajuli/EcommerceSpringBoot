package com.codefest.product_service.util;

import com.codefest.product_service.model.Category;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class CategoryDeserializer extends JsonDeserializer<Category> {
    @Override
    public Category deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException {
        String categoryName = jsonParser.getText();

        Category category = new Category();
        category.setName(categoryName);

        return category;
    }
}