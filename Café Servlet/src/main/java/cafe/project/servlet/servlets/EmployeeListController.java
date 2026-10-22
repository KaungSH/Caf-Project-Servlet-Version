package cafe.project.servlet.servlets;

import java.io.IOException;
import java.util.List;

import cafe.project.servlet.models.EmployeeListDto;
import cafe.project.servlet.repositories.EmployeeRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/employee-list")
@MultipartConfig
public class EmployeeListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EmployeeRepository employeeRepository = new EmployeeRepository();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<EmployeeListDto> employeeList = employeeRepository.getAll();
		request.setAttribute("employees", employeeList);
		request.getRequestDispatcher("/views/employee-list.jsp").forward(request, response);
	}
}