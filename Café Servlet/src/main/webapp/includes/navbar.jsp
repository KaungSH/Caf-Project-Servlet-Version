<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String activeNav = request.getParameter("activeNav");
if (activeNav == null) {
	activeNav = "";
}
String ctx = request.getContextPath();
%>
<header class="cafe-navbar">
	<div class="navbar-container">
		<a href="<%=ctx%>/home.jsp" class="cafe-brand">
			<div class="cafe-brand-icon">
				<i class="bi bi-cup-hot-fill"></i>
			</div>
			<div class="cafe-brand-text">
				<span class="cafe-brand-title">Cafe Operations</span>
				<span class="cafe-brand-subtitle">Artisan Brew &amp; Inventory</span>
			</div>
		</a>

		<nav aria-label="Main Navigation">
			<ul class="cafe-nav-links">
				<!-- Dashboard -->
				<li>
					<a href="<%=ctx%>/home.jsp" class="cafe-nav-link <%= "dashboard".equals(activeNav) ? "active" : "" %>">
						<i class="bi bi-grid-fill"></i>Dashboard
					</a>
				</li>
				
				<!-- Suppliers -->
				<li>
					<a href="<%=ctx%>/suppliers" class="cafe-nav-link <%= "suppliers".equals(activeNav) ? "active" : "" %>">
						<i class="bi bi-truck"></i>Suppliers
					</a>
				</li>

				<!-- Branches (အသစ်ထည့်ရန်) -->
				<li>
					<a href="<%=ctx%>/branches" class="cafe-nav-link <%= "branches".equals(activeNav) ? "active" : "" %>">
						<i class="bi bi-building"></i>Branches
					</a>
				</li>

				<!-- Employees (အသစ်ထည့်ရန်) -->
				<li>
					<a href="<%=ctx%>/employees" class="cafe-nav-link <%= "employees".equals(activeNav) ? "active" : "" %>">
						<i class="bi bi-people-fill"></i>Employees
					</a>
				</li>

				<!-- Ingredients -->
				<li>
					<a href="<%=ctx%>/ingredientTypes" class="cafe-nav-link <%= "ingredients".equals(activeNav) ? "active" : "" %>">
						<i class="bi bi-droplet-half"></i>Ingredients
					</a>
				</li>

				<!-- Stock Imports -->
				<li>
					<a href="<%=ctx%>/stockimport" class="cafe-nav-link <%= "stockImports".equals(activeNav) ? "active" : "" %>">
						<i class="bi bi-box-seam-fill"></i>Stock Imports
					</a>
				</li>
			</ul>
		</nav>
	</div>
</header>
