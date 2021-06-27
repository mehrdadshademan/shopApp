package com.energize.shop.shopApp.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "comment")
public class CommandModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long commandId;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JsonIgnoreProperties(value = { "category" }, allowSetters = true)
    private ProductModel product;

    public Long getId() {
        return commandId;
    }

    public void setId(Long id) {
        this.commandId = id;
    }

    public CommandModel id(Long id) {
        this.commandId = id;
        return this;
    }

    public String getText() {
        return this.text;
    }

    public CommandModel text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ProductModel getProduct() {
        return this.product;
    }

    public CommandModel product(ProductModel product) {
        this.setProduct(product);
        return this;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommandModel)) {
            return false;
        }
        return commandId != null && commandId.equals(((CommandModel) o).commandId);
    }



    // prettier-ignore
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + getId() +
                ", text='" + getText() + "'" +
                "}";
    }
}
