package com.energize.shop.shopApp.services;

import com.energize.shop.shopApp.exception.ProductNotFoundException;
import com.energize.shop.shopApp.exception.ProductNotValidException;
import com.energize.shop.shopApp.repository.CommandRepository;
import com.energize.shop.shopApp.repository.ProductRepository;
import com.energize.shop.shopApp.repository.UsersRepository;
import com.energize.shop.shopApp.repository.model.CommandModel;
import com.energize.shop.shopApp.repository.model.ProductModel;
import org.glassfish.jersey.server.internal.scanning.ResourceFinderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shop")
public class ProductService {
    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CommandRepository commandRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/productsList")
    public List<ProductModel> findAllProduct(Pageable pageable) {
        return productRepository.findAll();
    }

    @GetMapping("/products/{productId}")
    public ProductModel findById(@PathVariable Long productId) {
        try {
            return productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        } catch (Exception e) {
            log.error("error when try to find product", e);
            return null;
        }
    }

    /**
     * insert product by admin
     *
     * @param product is the product model in body
     * @return
     */
    @PostMapping("/products")
    @PreAuthorize("hasRole('Admin')")
    public ProductModel createProduct(@Valid @RequestBody ProductModel product) {
        if (validateProduct(product)) {
            return productRepository.save(product);
        }
        log.error("can not save product , that not valid , product:{}", product.toString());
        return null;
    }

    /**
     * update product by product id , just admin can update product
     *
     * @param productId    , product id that send as variable
     * @param productModel , product should be updated
     * @return
     */
    @PutMapping("/products/{productId}")
    @PreAuthorize("hasRole('Admin')")
    public ProductModel updateProduct(
            @PathVariable Long productId
            , @Valid @RequestBody ProductModel productModel) {
        if (!validateProduct(productModel)) {
            log.error("input is not valid , product:{}", productModel.toString());
            new ProductNotValidException(productId);
        }
        return productRepository.findById(productId).map(
                p -> {
                    p.setCategory(productModel.getCategory());
                    p.setName(productModel.getName());
                    p.setpId(productModel.getpId());
                    p.setRate(productModel.getRate());
                    p.setPrice(productModel.getPrice());
                    return productRepository.save(p);
                }).orElseThrow(() -> new ProductNotFoundException(productId));

    }

    @DeleteMapping("/removeProducts/{productId}")
    @PreAuthorize("hasRole('Admin')")
    void deleteProduct(@PathVariable Long productId) {
        productRepository.deleteById(productId);
    }

    //----------------------------


    private boolean validateProduct(ProductModel productModel) {
        return productModel != null && productModel.getpId() != null && productModel.getCategory() != null;
    }


    ///-----------------------------

    @PutMapping("/user/active/{userName}")
    @PreAuthorize("hasRole('ADMIN')")
    public String activeUserAccess(@PathVariable String userName) {
        try {
            usersRepository.findByUsername(userName).map(u -> {
                u.getUsername();
                u.getEmail();
                u.getPassword();
                u.getId();
                u.getRoles();
                u.setUserStatus(1);
                return usersRepository.save(u);
            }).orElseThrow(() -> new UsernameNotFoundException("username not found:" + userName));

        } catch (Exception e) {
            log.error("can not deActive user:{}", userName, e);
            new UsernameNotFoundException("username not found:" + userName);
        }
        return "User " + userName + " is active ";
    }

    @PutMapping("/user/deactive/{userName}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deactiveUserAccess(@PathVariable String userName) {
        try {
            usersRepository.findByUsername(userName).map(u -> {
                u.getUsername();
                u.getEmail();
                u.getPassword();
                u.getId();
                u.getRoles();
                u.setUserStatus(0);
                return usersRepository.save(u);
            }).orElseThrow(() -> new UsernameNotFoundException("username not found:" + userName));

        } catch (Exception ex) {
            log.error("can not deActive user:{}", userName, ex);
            new UsernameNotFoundException("username not found:" + userName);
        }
        return "User " + userName + " is DeActive ";
    }

    //---------------------------------
    @PostMapping("/comment/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String addComment(@PathVariable Long productId
            , @Valid @RequestBody String comment) {
        try {
            ProductModel product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));;
            if (product!=null )
            {
                CommandModel commandModel = new CommandModel();
                commandModel.setProduct(product);
                commandModel.setText(comment);
            commandRepository.save(commandModel);
            log.debug("success command on productId:{} , comment:{}", productId , comment);
            return "comment Insert on product " + product.getName();
            }
            return "can not find your product ";

        } catch (Exception e) {
            log.error("can not insert comment:{} , product:{}", comment , productId, e);
            return "system on process , try again ";
        }

    }
}
