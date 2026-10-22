<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
				<span class="cafe-brand-title">Cafe Operations</span> <span
					class="cafe-brand-subtitle">Artisan Brew &amp; Inventory</span>
			</div>
		</a>

		<nav aria-label="Main Navigation">
			<ul class="cafe-nav-links">
				<li><a href="<%=ctx%>/home.jsp"
					class="cafe-nav-link <%="dashboard".equals(activeNav) ? "active" : ""%>">
						<i class="bi bi-grid-fill"></i>Dashboard
				</a></li>

				<li class="cafe-nav-item"><a href="<%=ctx%>/suppliers"
					class="cafe-nav-link <%="suppliers".equals(activeNav) ? "active" : ""%>">
						<i class="bi bi-truck"></i>Suppliers <i class="bi bi-chevron-down"
						style="font-size: 11px;"></i>
				</a>
					<ul class="cafe-nav-dropdown-menu">
						<li><a href="<%=ctx%>/suppliers" class="cafe-dropdown-item">
								<i class="bi bi-list-ul"></i>Suppliers Directory
						</a></li>
						<li><a href="<%=ctx%>/suppliers?action=add"
							class="cafe-dropdown-item"> <i class="bi bi-plus-circle"></i>Add
								Supplier
						</a></li>
						<li class="cafe-dropdown-divider"></li>
						<li><a href="<%=ctx%>/suppliers?action=deleted"
							class="cafe-dropdown-item"> <i class="bi bi-trash3"></i>Deleted
								Suppliers
						</a></li>
					</ul></li>

				<li class="cafe-nav-item"><a href="<%=ctx%>/ingredientTypes"
					class="cafe-nav-link <%="ingredients".equals(activeNav) ? "active" : ""%>">
						<i class="bi bi-droplet-half"></i>Ingredients <i
						class="bi bi-chevron-down" style="font-size: 11px;"></i>
				</a>
					<ul class="cafe-nav-dropdown-menu">
						<li><a href="<%=ctx%>/ingredientTypes"
							class="cafe-dropdown-item"> <i class="bi bi-list-ul"></i>Ingredient
								Types
						</a></li>
						<li><a href="<%=ctx%>/ingredientTypes?action=add"
							class="cafe-dropdown-item"> <i class="bi bi-plus-circle"></i>Add
								Ingredient Type
						</a></li>
						<li class="cafe-dropdown-divider"></li>
						<li><a href="<%=ctx%>/ingredientTypes?action=deleted"
							class="cafe-dropdown-item"> <i class="bi bi-trash3"></i>Deleted
								Ingredients
						</a></li>
					</ul></li>

				<li class="cafe-nav-item"><a href="<%=ctx%>/stockimport"
					class="cafe-nav-link <%="stockImports".equals(activeNav) ? "active" : ""%>">
						<i class="bi bi-box-seam-fill"></i>Stock Imports <i
						class="bi bi-chevron-down" style="font-size: 11px;"></i>
				</a>
					<ul class="cafe-nav-dropdown-menu">
						<li><a href="<%=ctx%>/stockimport" class="cafe-dropdown-item">
								<i class="bi bi-list-ul"></i>Deliveries List
						</a></li>
						<li><a href="<%=ctx%>/stockimport?action=add"
							class="cafe-dropdown-item"> <i class="bi bi-plus-circle"></i>Add
								Stock Import
						</a></li>
						<li class="cafe-dropdown-divider"></li>
						<li><a href="<%=ctx%>/stockimport?action=deleted"
							class="cafe-dropdown-item"> <i class="bi bi-trash3"></i>Deleted
								Deliveries
						</a></li>
					</ul></li>
			</ul>
		</nav>
	</div>
</header>
