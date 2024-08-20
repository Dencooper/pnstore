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
                <title>Quản Lý Sản Phẩm - PN Store</title>
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
                                    <li class="breadcrumb-item active">Sản Phẩm</li>
                                </ol>
                                <div class="mt-5">
                                    <div class="row">
                                        <div class="col-12 mx-auto">
                                            <div class="d-flex justify-content-between">
                                                <h3>Bảng Sản Phẩm</h3>
                                                <a href="/admin/product/create" class="btn btn-primary">Thêm sản
                                                    phẩm</a>
                                            </div>

                                            <hr />
                                            <table class=" table table-bordered table-hover">
                                                <thead>
                                                    <tr>
                                                        <th class="text-center align-middle">Mã</th>
                                                        <th>Tên</th>
                                                        <th>Giá cả</th>
                                                        <th class="text-center">Hãng</th>
                                                        <th>Hành động</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="product" items="${products}">
                                                        <tr>
                                                            <th class="text-center align-middle">${product.id}</th>
                                                            <td class="align-middle">${product.name}</td>
                                                            <td class="align-middle">
                                                                <fmt:formatNumber type="number"
                                                                    value="${product.price}" /> đ
                                                            </td>
                                                            <td class="text-center align-middle">${product.factory}</td>
                                                            <td>
                                                                <a href="/admin/product/${product.id}"
                                                                    class="btn btn-success">Xem</a>
                                                                <a href="/admin/product/update/${product.id}"
                                                                    class="btn btn-warning  mx-2">Cật nhật</a>
                                                                <a href="/admin/product/delete/${product.id}"
                                                                    class="btn btn-danger">Xóa</a>
                                                            </td>
                                                        </tr>

                                                    </c:forEach>

                                                </tbody>
                                            </table>
                                        </div>

                                    </div>

                                </div>
                                <nav aria-label="Page navigation example ">
                                    <ul class="pagination justify-content-center">
                                        <li class="page-item">
                                            <a class="page-link ${1 eq  currentPage ? 'disabled' : ''}"
                                                href="/admin/product?page=${currentPage-1}" aria-label="Previous">
                                                <span aria-hidden="true">&laquo;</span>
                                            </a>
                                        </li>

                                        <c:forEach varStatus="loop" begin="0" end="${totalPages - 1}">
                                            <li class="page-item">
                                                <a class="page-link ${(loop.index + 1) eq  currentPage ? 'active' : ''}"
                                                    href="/admin/product?page=${loop.index+1}">${loop.index+1}</a>
                                            </li>
                                            <p>
                                        </c:forEach>

                                        <li class="page-item">
                                            <a class="page-link ${totalPages eq  currentPage ? 'disabled' : ''}"
                                                href="/admin/product?page=${currentPage+1}" aria-label="Next">
                                                <span aria-hidden="true">&raquo;</span>
                                            </a>
                                        </li>
                                    </ul>
                                </nav>
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