<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="utf-8" />
            <meta http-equiv="X-UA-Compatible" content="IE=edge" />
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
            <meta name="description" content="E-Commerce Website for Laptop" />
            <meta name="author" content="Decooper" />
            <title>Xóa Sản Phẩm - PN Store</title>
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
                                <li class="breadcrumb-item"><a href="/admin/products">Sản Phẩm</a></li>
                                <li class="breadcrumb-item active">Xóa sản phẩm</li>
                            </ol>
                            <div class=" mt-5">
                                <div class="row">
                                    <div class="col-12 mx-auto">
                                        <div class="d-flex justify-content-between">
                                            <h3>Xóa sản phẩm có mã ${id}</h3>
                                        </div>

                                        <hr />
                                        <div class="alert alert-danger">
                                            Bạn có chắc xóa sản phẩm này ?
                                        </div>
                                        <form method="post" action="/admin/product/delete">
                                            <div class="mb-3" style="display: none;">
                                                <label class="form-label">Mã:</label>
                                                <input value="${id}" type="text" class="form-control" name="id" />
                                            </div>
                                            <button class="btn btn-danger">Xác nhận</button>
                                        </form>

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