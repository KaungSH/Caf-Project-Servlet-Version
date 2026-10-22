<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Waste Logs - Café Luft</title>
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body>
<div class="container-fluid">
    <div class="row">
    <%--     <!-- Sidebar Navigation -->
        <jsp:include page="/WEB-INF/views/common/fragments/sidebar_manager.jsp">
            <jsp:param name="activeTab" value="wastelogs"/>
        </jsp:include> --%>

        <!-- Main Workspace -->
        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4">
            <div class="d-flex justify-content-between align-items-center pb-2 mb-3 border-bottom">
                <h1 class="h2">Waste Logs</h1>
                <a href="${pageContext.request.contextPath}/manager/wastelogs?action=new" class="btn btn-danger">
                    <i class="bi bi-plus-circle me-1"></i> Record Waste
                </a>
            </div>

            <c:if test="${param.status == 'created'}">
                <div class="alert alert-success alert-dismissible fade show">Recorded successfully!<button type="button" class="btn-close" data-bs-dismiss="alert"></button></div>
            </c:if>
            <c:if test="${param.status == 'updated'}">
                <div class="alert alert-info alert-dismissible fade show">Log updated!<button type="button" class="btn-close" data-bs-dismiss="alert"></button></div>
            </c:if>
            <c:if test="${param.status == 'deleted'}">
                <div class="alert alert-warning alert-dismissible fade show">Log removed!<button type="button" class="btn-close" data-bs-dismiss="alert"></button></div>
            </c:if>

            <div class="card shadow-sm border-0">
                <div class="card-body p-0">
                    <table class="table table-hover table-striped mb-0 align-middle">
                        <thead class="table-dark">
                            <tr>
                                <th>Date Logged</th>
                                <th>Batch ID</th>
                                <th>Reason</th>
                                <th>Quantity Lost</th>
                                <th>Financial Loss (MMK)</th>
                                <th>Logged By</th>
                                <th>Notes</th>
                                <th class="text-center">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${logs}">
                                <tr>
                                    <td><fmt:formatDate value="${item.loggedAt}" pattern="yyyy-MM-dd HH:mm"/></td>
                                    <td><span class="badge bg-secondary">${item.batchId}</span></td>
                                    <td><span class="badge bg-warning text-dark">${item.reasonName}</span></td>
                                    <td>${item.quantityLost}</td>
                                    <td><fmt:formatNumber value="${item.financialLoss}" type="currency" currencySymbol=""/> MMK</td>
                                    <td>${item.employeeName != null ? item.employeeName : 'System'}</td>
                                    <td>${item.notes}</td>
                                    <td class="text-center">
                                        <a href="${pageContext.request.contextPath}/manager/wastelogs?action=edit&id=${item.wasteId}" 
                                           class="btn btn-sm btn-outline-primary me-1">
                                            <i class="bi bi-pencil"></i>
                                        </a>
                                        <a href="${pageContext.request.contextPath}/manager/wastelogs?action=delete&id=${item.wasteId}" 
                                           class="btn btn-sm btn-outline-danger"
                                           onclick="return confirm('Are you sure you want to delete this record?');">
                                            <i class="bi bi-trash"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty logs}">
                                <tr>
                                    <td colspan="8" class="text-center py-4 text-muted">No waste records found for this branch.</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </main>
    </div>
</div>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>
</body>
</html>s