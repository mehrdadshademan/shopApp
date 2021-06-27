package com.energize.shop.shopApp.repository.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="product")
public class ProductModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Integer id;


    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "rate")
    private Integer rate;

    @ManyToOne
    private CategoryModel category;

    public Integer getpId() {
        return id;
    }

    public void setpId(Integer pId) {
        this.id = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", rate=" + rate +
                ", category=" + category +
                '}';
    }
}
