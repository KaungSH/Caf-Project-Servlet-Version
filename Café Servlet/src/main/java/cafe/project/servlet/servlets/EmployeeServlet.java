package cafe.project.servlet.servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/employee")
@MultipartConfig
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		if (action == null || action.trim().isEmpty()) {
			action = "list";
		}

		switch (action.toLowerCase()) {
		case "new":
		case "insert":

			request.getRequestDispatcher("/create-employee").forward(request, response);
			break;

		case "edit":
		case "update":

			request.getRequestDispatcher("/edit-employee").forward(request, response);
			break;

		case "delete":

			request.getRequestDispatcher("/delete-employee").forward(request, response);
			break;

		case "list":
		default:

			request.getRequestDispatcher("/employee-list").forward(request, response);
			break;
		}
	}
}