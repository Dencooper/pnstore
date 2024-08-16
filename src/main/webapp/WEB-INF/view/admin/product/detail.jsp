<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="E-Commerce Website for Laptop" />
                <meta name="author" content="Decooper" />
                <title>Chi Tiết Sản Phẩm - PN Store</title>
                <link href="/css/styles.css" rel="stylesheet" />

                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Quản Lý Sản Phẩm</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item"><a href="/admin">Trang chủ</a></li>
                                    <li class="breadcrumb-item"><a href="/admin/product">Sản phẩm</a></li>
                                    <li class="breadcrumb-item active">Chi tiết</li>
                                </ol>
                                <div class="container mt-5">
                                    <div class="row">
                                        <div class="col-12 mx-auto">
                                            <div class="d-flex justify-content-between">
                                                <h3>Chi tiết sản phẩm có mã ${id}</h3>
                                            </div>

                                            <hr />

                                            <div class="d-flex justify-content-around">
                                                <div class="card" style="width: 50%">
                                                    <div class="card-header">
                                                        Thông Tin Sản Phẩm
                                                    </div>
                                                    <ul class="list-group list-group-flush">
                                                        <li class="list-group-item">Mã: ${product.id}</li>
                                                        <li class="list-group-item">Tên: ${product.name}</li>
                                                        <li class="list-group-item">Giá cả: ${product.price}</li>
                                                        <li class="list-group-item">Mô tả chi tiết:
                                                            ${product.detailDesc}</li>
                                                        <li class="list-group-item">Mô tả ngắn:
                                                            ${product.shortDesc}</li>
                                                        <li class="list-group-item">Số lượng: ${product.quantity}</li>
                                                        <li class="list-group-item">Đã bán: ${product.sold}</li>
                                                        <li class="list-group-item">Hãng: ${product.factory}</li>
                                                        <li class="list-group-item">Mục đích: ${product.target}</li>
                                                    </ul>
                                                </div>
                                                <div class="card" style="width: 35%;">
                                                    <img src="/images/product/${product.image}" alt="Card Image Top"
                                                        class="card-img-top">
                                                </div>
                                            </div>

                                            <a href="/admin/product" class="btn btn-success mt-3">Trở lại</a>

                                        </div>

                                    </div>

                                </div>
                            </div>
                        </main>
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="/js/scripts.js"></script>

            </body>

            </html>