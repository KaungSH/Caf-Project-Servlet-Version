package cafe.project.servlet.servlets;

import java.io.IOException;

import cafe.project.servlet.repositories.EmployeeRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/delete-employee")
public class DeleteEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EmployeeRepository employeeRepository = new EmployeeRepository();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		if (id != null) {
			employeeRepository.delete(id);
		}
		response.sendRedirect("employee-list");
	}
}