<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="E-Commerce Website for Laptop" />
                <meta name="author" content="Decooper" />
                <title>Chi Tiết Đơn Hàng - PN Store</title>
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
                                <h1 class="mt-4">Quản Lý Đơn Hàng</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item"><a href="/admin">Trang chủ</a></li>
                                    <li class="breadcrumb-item"><a href="/admin/order">Đơn hàng</a></li>
                                    <li class="breadcrumb-item active">Xem chi tiết</li>
                                </ol>
                                <div class="mt-5">
                                    <div class="row">
                                        <div class="col-12 mx-auto">
                                            <div>
                                                <h3>Đơn hàng với Mã = ${order.id}</h3>
                                            </div>

                                            <hr />
                                            <div class="table-responsive">
                                                <table class="table text-center">
                                                    <thead>
                                                        <tr>
                                                            <th scope="col" class="text-start">Sản phẩm</th>
                                                            <th scope="col">Tên</th>
                                                            <th scope="col">Giá cả</th>
                                                            <th scope="col">Số lượng</th>
                                                            <th scope="col">Thành tiền</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach var="orderDetail" items="${order.orderDetails}"
                                                            varStatus="status">
                                                            <tr>
                                                                <th scope="row">
                                                                    <div class="text-start">
                                                                        <img src="/images/product/${orderDetail.product.images[0]}"
                                                                            class="img-fluid me-5"
                                                                            style="width: 80px; height: 80px; border-radius: 10px;"
                                                                            alt="">
                                                                    </div>
                                                                </th>
                                                                <td>
                                                                    <p class="mb-0 mt-4">
                                                                        <a href="/product/${orderDetail.product.id}"
                                                                            target="_blank">${orderDetail.product.name}</a>
                                                                    </p>
                                                                </td>
                                                                <td>
                                                                    <p class="mb-0 mt-4">
                                                                        <fmt:formatNumber type="number"
                                                                            value="${orderDetail.price}" /> đ

                                                                    </p>
                                                                </td>
                                                                <td>
                                                                    <p class="mb-0 mt-4">
                                                                        ${orderDetail.quantity}
                                                                    </p>
                                                                </td>
                                                                <td>
                                                                    <p class="mb-0 mt-4">
                                                                        <fmt:formatNumber type="number"
                                                                            value="${orderDetail.product.price * orderDetail.quantity}" />
                                                                        đ
                                                                    </p>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>

                                                    </tbody>
                                                </table>
                                            </div>
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