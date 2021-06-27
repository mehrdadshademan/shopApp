package com.energize.shop.shopApp;

import com.energize.shop.shopApp.repository.ProductRepository;
import com.energize.shop.shopApp.repository.model.CategoryModel;
import com.energize.shop.shopApp.repository.model.CommandModel;
import com.energize.shop.shopApp.repository.model.ProductModel;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class ShopAppApplicationTests extends AbstractTest {
    @Autowired
    ProductRepository productRepository;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getProductsList() throws Exception {
        String uri = "/api/shop/productsList";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ProductModel[] productlist = super.mapFromJson(content, ProductModel[].class);
        assertTrue(productlist.length > 0);
    }

    @Test
    public void createProduct() throws Exception {
        String uri = "/api/shop/products";
        ProductModel product = new ProductModel();
        product.setPrice(30000);
        product.setName("book");
        product.setRate(5);
        CategoryModel categories = new CategoryModel();
        categories.setName("school");
        product.setCategory(categories);
        //test without header
        String inputJson = super.mapToJson(product);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "Product is created successfully");
    }

    @Test
    public void updateProduct() throws Exception {
        String uri = "/api/shop/products/2";
        ProductModel product = new ProductModel();
        product.setName("book-java");
        product.setPrice(1000);
        String inputJson = super.mapToJson(product);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "Product is updated successsfully");
    }

    @Test
    public void deleteProduct() throws Exception {
        String uri = "/api/shop/removeProducts/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "Product is deleted success");
    }


    @Test
    public void createComment() throws Exception {
        String uri = "/api/shop/comment/2";
        CommandModel command = new CommandModel();
        command.setText("this boos is so good");
        ProductModel product = productRepository.getById(2L);
        command.setProduct(product);
        //test without header
        String inputJson = super.mapToJson(product);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "Product is created successfully");
    }

// test is not complete ... dont have time and should complete this
}
