package cafe.project.servlet.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

import cafe.project.servlet.models.Suppliers;
import cafe.project.servlet.repositories.SupplierDAO;

@WebServlet("/suppliers")
public class SupplierServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private SupplierDAO supplierDAO;

	@Override
	public void init() {
		supplierDAO = new SupplierDAO();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		try {

			if (action == null || action.trim().isEmpty() || "list".equals(action)) {

				request.setAttribute("suppliers", supplierDAO.getAllSuppliers());

				request.getRequestDispatcher("/suppliers/list.jsp").forward(request, response);

				return;
			} else if ("deleted".equals(action)) {

				request.setAttribute("suppliers", supplierDAO.getDeletedSuppliers());

				request.getRequestDispatcher("/suppliers/deleted.jsp").forward(request, response);

			} else if ("add".equals(action)) {

				request.getRequestDispatcher("/suppliers/add.jsp").forward(request, response);

			} else if ("edit".equals(action)) {

				String id = request.getParameter("id");

				if (id == null || id.trim().isEmpty()) {
					response.sendRedirect(request.getContextPath() + "/suppliers?action=list");
					return;
				}

				Suppliers supplier = supplierDAO.getSupplierById(id.trim());

				if (supplier == null) {
					response.sendRedirect(request.getContextPath() + "/suppliers?action=list");
					return;
				}

				request.setAttribute("supplier", supplier);

				request.getRequestDispatcher("/suppliers/edit.jsp").forward(request, response);

			} else {

				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid supplier action.");
			}

		} catch (SQLException e) {

			throw new ServletException("Unable to load supplier information.", e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String action = request.getParameter("action");

		try {

			if ("insert".equals(action)) {

				String name = request.getParameter("name");
				String contact = request.getParameter("contact_info");

				boolean hasError = false;

				if (name == null || name.trim().isEmpty()) {
					request.setAttribute("nameError", "Supplier Name is required.");
					hasError = true;
				}

				if (contact == null || contact.trim().isEmpty()) {
					request.setAttribute("contactError", "Contact Information is required.");
					hasError = true;
				}

				if (hasError) {

					request.setAttribute("nameValue", name);
					request.setAttribute("contactValue", contact);

					request.getRequestDispatcher("/suppliers/add.jsp").forward(request, response);

					return;
				}

				name = name.trim();
				contact = contact.trim();

				if (supplierDAO.isSupplierNameExists(name)) {

					request.setAttribute("nameError", "This Supplier Name already exists.");

					request.setAttribute("nameValue", name);
					request.setAttribute("contactValue", contact);

					request.getRequestDispatcher("/suppliers/add.jsp").forward(request, response);

					return;
				}

				Suppliers supplier = new Suppliers();

				supplier.setName(name);
				supplier.setContact_info(contact);

				boolean success = supplierDAO.addSupplier(supplier);

				if (success) {

					response.sendRedirect(request.getContextPath() + "/suppliers?action=list&message=added");

				} else {

					throw new ServletException("Unable to add supplier.");
				}

			} else if ("update".equals(action)) {

				String id = request.getParameter("supplier_id");
				String name = request.getParameter("name");
				String contact = request.getParameter("contact_info");

				if (id == null || id.trim().isEmpty()) {

					response.sendRedirect(request.getContextPath() + "/suppliers?action=list");

					return;
				}

				id = id.trim();

				boolean hasError = false;

				if (name == null || name.trim().isEmpty()) {
					request.setAttribute("nameError", "Supplier Name is required.");
					hasError = true;
				}

				if (contact == null || contact.trim().isEmpty()) {
					request.setAttribute("contactError", "Contact Information is required.");
					hasError = true;
				}

				request.setAttribute("nameValue", name);
				request.setAttribute("contactValue", contact);

				if (hasError) {

					Suppliers existing = supplierDAO.getSupplierById(id);

					if (existing == null) {
						response.sendRedirect(request.getContextPath() + "/suppliers?action=list");
						return;
					}

					request.setAttribute("supplier", existing);

					request.getRequestDispatcher("/suppliers/edit.jsp").forward(request, response);

					return;
				}

				name = name.trim();
				contact = contact.trim();

				if (supplierDAO.isSupplierNameExistsForUpdate(name, id)) {

					request.setAttribute("nameError", "This Supplier Name already exists.");

					request.setAttribute("nameValue", name);
					request.setAttribute("contactValue", contact);

					request.setAttribute("supplier", supplierDAO.getSupplierById(id));

					request.getRequestDispatcher("/suppliers/edit.jsp").forward(request, response);

					return;
				}

				Suppliers supplier = new Suppliers();

				supplier.setSupplier_id(id);
				supplier.setName(name);
				supplier.setContact_info(contact);

				boolean updated = supplierDAO.updateSupplier(supplier);

				if (updated) {

					response.sendRedirect(request.getContextPath() + "/suppliers?action=list&message=updated");

				} else {

					request.setAttribute("error", "Unable to update supplier.");

					request.setAttribute("nameValue", name);
					request.setAttribute("contactValue", contact);

					request.setAttribute("supplier", supplierDAO.getSupplierById(id));

					request.getRequestDispatcher("/suppliers/edit.jsp").forward(request, response);
				}

			} else if ("delete".equals(action)) {

				String id = request.getParameter("supplier_id");

				if (id != null && !id.trim().isEmpty()) {
					supplierDAO.deleteSupplier(id.trim());
				}

				response.sendRedirect(request.getContextPath() + "/suppliers?action=list&message=deleted");

			} else if ("restore".equals(action)) {

				String id = request.getParameter("supplier_id");

				if (id != null && !id.trim().isEmpty()) {
					supplierDAO.restoreSupplier(id.trim());
				}

				response.sendRedirect(request.getContextPath() + "/suppliers?action=deleted&message=restored");

			} else if ("permanentDelete".equals(action)) {

				String id = request.getParameter("supplier_id");

				if (id != null && !id.trim().isEmpty()) {
					supplierDAO.permanentDeleteSupplier(id.trim());
				}

				response.sendRedirect(request.getContextPath() + "/suppliers?action=deleted&message=permanentDeleted");

			} else {

				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid supplier action.");
			}

		} catch (SQLException e) {

			throw new ServletException("Unable to save supplier information.", e);
		}
	}
}
