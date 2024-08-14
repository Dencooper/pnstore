package com.dencooper.pnstore.domain;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotEmpty(message = "Tên sản phẩm không được để trống")
    private String name;

    @NotNull
    @DecimalMin(value = "0", inclusive = false, message = "Giá phải lớn hơn 0")
    private double price;

    private String image;

    @NotNull
    @NotEmpty(message = "Mô tả chi tiết không được để trống")
    @Column(columnDefinition = "MEDIUMTEXT")
    private String detailDesc;

    @NotNull
    @NotEmpty(message = "Mô tả ngắn không được để trống")
    private String shortDesc;

    @NotNull
    @Min(value = 1, message = "Số lượng cần lớn hơn 1")
    private long quantity;

    private long sold;
    private String factory;
    private String target;

    @OneToMany(mappedBy = "product")
    private List<CartDetail> cartDetails;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;

}
