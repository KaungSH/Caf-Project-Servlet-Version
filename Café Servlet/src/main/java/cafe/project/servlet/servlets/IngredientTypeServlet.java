package cafe.project.servlet.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

import cafe.project.servlet.models.IngredientTypes;
import cafe.project.servlet.repositories.IngredientTypeDAO;
import cafe.project.servlet.repositories.IngredientTypeDAO.UnitOption;

@WebServlet("/ingredientTypes")
public class IngredientTypeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private IngredientTypeDAO ingredientTypeDAO;

	@Override
	public void init() throws ServletException {
		ingredientTypeDAO = new IngredientTypeDAO();
	}

	// ==================== GET ====================

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String action = request.getParameter("action");

		try {

			// LIST
			if (action == null || action.isEmpty() || "list".equals(action)) {

				request.setAttribute("items", ingredientTypeDAO.findAll(false));

				request.getRequestDispatcher("/ingredientTypes/list.jsp").forward(request, response);
			}

			// ADD FORM
			else if ("add".equals(action)) {

				request.setAttribute("units", ingredientTypeDAO.findActiveUnits());

				request.getRequestDispatcher("/ingredientTypes/add.jsp").forward(request, response);
			}

			// EDIT FORM
			else if ("edit".equals(action)) {

				String id = request.getParameter("id");

				if (id == null || id.trim().isEmpty()) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ingredient Type ID is required.");
					return;
				}

				IngredientTypes item = ingredientTypeDAO.findById(id.trim());

				if (item == null || item.getIsDeleted()) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ingredient Type Not Found.");
					return;
				}

				request.setAttribute("item", item);

				request.setAttribute("units", ingredientTypeDAO.findActiveUnits());

				request.getRequestDispatcher("/ingredientTypes/edit.jsp").forward(request, response);
			}

			// DELETED LIST
			else if ("deleted".equals(action)) {

				request.setAttribute("items", ingredientTypeDAO.findAll(true));

				request.getRequestDispatcher("/ingredientTypes/deleted.jsp").forward(request, response);
			}

			// INVALID ACTION
			else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
			}

		} catch (SQLException e) {
			throw new ServletException("Unable to load ingredient type information.", e);
		}
	}

	// ==================== POST ====================

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String action = request.getParameter("action");

		try {

			// ==================== INSERT ====================
			if ("insert".equals(action)) {

				String name = request.getParameter("name");
				String description = request.getParameter("description");
				String unitId = request.getParameter("unitId");

				String nameError = null;
				String descriptionError = null;
				String unitError = null;

				// 1. Validate Name
				if (name == null || name.trim().isEmpty()) {
					nameError = "Please enter ingredient type name.";
				} else if (name.trim().length() > 100) {
					nameError = "Name must not exceed 100 characters.";
				} else if (ingredientTypeDAO.nameExists(name.trim(), null)) {
					nameError = "Ingredient type name already exists.";
				}

				// 2. Validate Description
				if (description == null || description.trim().isEmpty()) {
					descriptionError = "Please enter description.";
				} else if (description.trim().length() > 255) {
					descriptionError = "Description must not exceed 255 characters.";
				}

				// 3. Validate Unit
				if (unitId == null || unitId.trim().isEmpty()) {
					unitError = "Please select a unit.";
				} else if (!isValidActiveUnit(unitId.trim())) {
					unitError = "Please select a valid unit.";
				}

				// Return errors under their respective fields
				if (nameError != null || descriptionError != null || unitError != null) {

					request.setAttribute("nameError", nameError);
					request.setAttribute("descriptionError", descriptionError);
					request.setAttribute("unitError", unitError);

					// Preserve submitted values
					request.setAttribute("formName", name);
					request.setAttribute("formDescription", description);
					request.setAttribute("formUnitId", unitId);

					// Reload available units
					request.setAttribute("units", ingredientTypeDAO.findActiveUnits());

					request.getRequestDispatcher("/ingredientTypes/add.jsp").forward(request, response);

					return;
				}

				// 4. Save when all validations pass
				IngredientTypes item = new IngredientTypes();

				item.setName(name.trim());
				item.setDescription(description.trim());
				item.setUnitId(unitId.trim());

				ingredientTypeDAO.add(item);

				response.sendRedirect(request.getContextPath() + "/ingredientTypes?message=added");

				return;
			}

			// ==================== UPDATE ====================

			else if ("update".equals(action)) {

				String id = request.getParameter("id");
				String name = request.getParameter("name");
				String description = request.getParameter("description");
				String unitId = request.getParameter("unitId");

				IngredientTypes item = ingredientTypeDAO.findById(id);

				if (item == null || item.getIsDeleted()) {
					response.sendRedirect(request.getContextPath() + "/ingredientTypes");
					return;
				}

				String nameError = null;
				String descriptionError = null;
				String unitError = null;

				// Validate Name
				if (name == null || name.trim().isEmpty()) {
					nameError = "Please enter ingredient type name.";
				} else if (name.trim().length() > 100) {
					nameError = "Name must not exceed 100 characters.";
				} else if (ingredientTypeDAO.nameExists(name.trim(), id)) {
					nameError = "Ingredient type name already exists.";
				}

				// Validate Description
				if (description == null || description.trim().isEmpty()) {
					descriptionError = "Please enter description.";
				} else if (description.trim().length() > 255) {
					descriptionError = "Description must not exceed 255 characters.";
				}

				// Validate Unit
				if (unitId == null || unitId.trim().isEmpty()) {
					unitError = "Please select a unit.";
				} else if (!isValidActiveUnit(unitId.trim())) {
					unitError = "Please select a valid unit.";
				}

				// If validation fails, return to edit.jsp
				if (nameError != null || descriptionError != null || unitError != null) {

					// Keep the original item and ID
					request.setAttribute("item", item);

					// Show field-specific errors
					request.setAttribute("nameError", nameError);
					request.setAttribute("descriptionError", descriptionError);
					request.setAttribute("unitError", unitError);

					// Preserve submitted values
					request.setAttribute("formName", name);
					request.setAttribute("formDescription", description);
					request.setAttribute("formUnitId", unitId);

					// Reload available units
					request.setAttribute("units", ingredientTypeDAO.findActiveUnits());

					request.getRequestDispatcher("/ingredientTypes/edit.jsp").forward(request, response);

					return;
				}

				// Save when all validations pass
				item.setName(name.trim());
				item.setDescription(description.trim());
				item.setUnitId(unitId.trim());

				ingredientTypeDAO.edit(item);

				response.sendRedirect(request.getContextPath() + "/ingredientTypes?message=updated");

				return;
			}

			// ==================== SOFT DELETE ====================

			else if ("delete".equals(action)) {

				String id = request.getParameter("id");

				if (id == null || id.trim().isEmpty()) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ingredient Type ID is required.");
					return;
				}

				id = id.trim();

				IngredientTypes item = ingredientTypeDAO.findById(id);

				if (item == null || item.getIsDeleted()) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ingredient Type Not Found.");
					return;
				}

				boolean deleted = ingredientTypeDAO.softDelete(id);

				if (!deleted) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ingredient Type Not Found.");
					return;
				}

				response.sendRedirect(request.getContextPath() + "/ingredientTypes?message=deleted");
			}

			// ==================== RESTORE ====================

			else if ("restore".equals(action)) {

				String id = request.getParameter("id");

				if (id == null || id.trim().isEmpty()) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ingredient Type ID is required.");
					return;
				}

				id = id.trim();

				IngredientTypes item = ingredientTypeDAO.findById(id);

				if (item == null || !item.getIsDeleted()) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND, "Deleted Ingredient Type Not Found.");
					return;
				}

				if (ingredientTypeDAO.nameExists(item.getName(), id)) {
					response.sendRedirect(
							request.getContextPath() + "/ingredientTypes?action=deleted&message=duplicate");
					return;
				}

				boolean restored = ingredientTypeDAO.restore(id);

				if (!restored) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND, "Deleted Ingredient Type Not Found.");
					return;
				}

				response.sendRedirect(request.getContextPath() + "/ingredientTypes?action=deleted&message=restored");
			}

			// ==================== PERMANENT DELETE ====================

			else if ("permanentDelete".equals(action)) {

				String id = request.getParameter("id");

				if (id == null || id.trim().isEmpty()) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ingredient Type ID is required.");
					return;
				}

				id = id.trim();

				IngredientTypes item = ingredientTypeDAO.findById(id);

				if (item == null || !item.getIsDeleted()) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND, "Deleted Ingredient Type Not Found.");
					return;
				}

				boolean deleted = ingredientTypeDAO.permanentDelete(id);

				if (!deleted) {
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
							"Unable to permanently delete ingredient type.");
					return;
				}

				response.sendRedirect(
						request.getContextPath() + "/ingredientTypes?action=deleted&message=permanentDeleted");
			}

			// INVALID ACTION
			else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
			}

		} catch (SQLException e) {
			throw new ServletException("Unable to save ingredient type information.", e);
		}
	}

	// ==================== CHECK ACTIVE UNIT ====================

	private boolean isValidActiveUnit(String unitId) throws SQLException {

		for (UnitOption unit : ingredientTypeDAO.findActiveUnits()) {

			if (unit.getUnitID() != null && unit.getUnitID().equals(unitId)) {
				return true;
			}
		}

		return false;
	}

	// ==================== ADD VALIDATION ERROR ====================

	private void showAddError(HttpServletRequest request, HttpServletResponse response, String error)
			throws ServletException, IOException, SQLException {

		// Preserve the values submitted by the user
		request.setAttribute("formName", request.getParameter("name"));
		request.setAttribute("formDescription", request.getParameter("description"));
		request.setAttribute("formUnitId", request.getParameter("unitId"));

		request.setAttribute("error", error);
		request.setAttribute("units", ingredientTypeDAO.findActiveUnits());

		request.getRequestDispatcher("/ingredientTypes/add.jsp").forward(request, response);
	}

	private void showEditError(HttpServletRequest request, HttpServletResponse response, IngredientTypes item,
			String error) throws ServletException, IOException, SQLException {

		// Preserve the values submitted by the user
		request.setAttribute("formName", request.getParameter("name"));
		request.setAttribute("formDescription", request.getParameter("description"));
		request.setAttribute("formUnitId", request.getParameter("unitId"));

		request.setAttribute("item", item);
		request.setAttribute("error", error);
		request.setAttribute("units", ingredientTypeDAO.findActiveUnits());

		request.getRequestDispatcher("/ingredientTypes/edit.jsp").forward(request, response);
	}
}
