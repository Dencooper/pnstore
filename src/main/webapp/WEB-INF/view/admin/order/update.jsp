<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="utf-8" />
                    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                    <meta name="description" content="E-Commerce Website for Laptop" />
                    <meta name="author" content="Decooper" />
                    <title>Cập Nhật Đơn Hàng - PN Store</title>
                    <link href="/css/styles.css" rel="stylesheet" />

                    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
                        crossorigin="anonymous"></script>
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
                                        <li class="breadcrumb-item active">Cập nhật</li>
                                    </ol>
                                    <div class=" mt-5">
                                        <div class="row">
                                            <div class="col-md-6 col-12 mx-auto">
                                                <h3>Cập Nhật Đơn Hàng</h3>
                                                <hr />
                                                <form:form method="post" action="/admin/order/update"
                                                    modelAttribute="currentOrder">

                                                    <div class="mb-3" style="display: none;">
                                                        <label class="form-label">Mã:</label>
                                                        <form:input type="text" class="form-control" path="id" />
                                                    </div>

                                                    <div class="mb-3 d-flex justify-content-star">
                                                        <p class="me-5">Mã đơn hàng = ${currentOrder.id}</p>
                                                        <p>Tổng tiền =
                                                            <fmt:formatNumber type="number"
                                                                value="${currentOrder.totalPrice}" /> đ
                                                        </p>
                                                    </div>

                                                    <div class="d-flex justify-content-between mb-3">
                                                        <div class="mb-3 col-12 col-md-5">
                                                            <label class="form-label">Người mua:</label>
                                                            <form:input type="text" path="user.fullName"
                                                                class="form-control" disabled="true" />
                                                        </div>
                                                        <div class="mb-3 col-12 col-md-5">
                                                            <label class="form-label">Trạng thái:</label>
                                                            <form:select class="form-select" path="status">
                                                                <form:option value="Đang xử lí">Đang xử lí</form:option>
                                                                <form:option value="Đang giao hàng">Đang giao hàng
                                                                </form:option>
                                                                <form:option value="Hoàn thành">Hoàn thành</form:option>
                                                                <form:option value="Hủy">Hủy</form:option>
                                                            </form:select>
                                                        </div>
                                                    </div>


                                                    <button type="submit" class="btn btn-warning">Cập nhật</button>
                                                </form:form>
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