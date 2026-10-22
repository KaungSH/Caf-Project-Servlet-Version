package cafe.project.servlet.servlets;

import java.io.IOException;

import cafe.project.servlet.models.UserSessionDto;
import cafe.project.servlet.repositories.AuthRepository;
import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AuthRepository authRepository;

	@Override
	public void init() throws ServletException {
		authRepository = new AuthRepository();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("currentUser") != null) {
			response.sendRedirect(request.getContextPath() + "/employee-list");
			return;
		}

		request.getRequestDispatcher("/views/login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
			request.setAttribute("errorMessage", "Email Or Password");
			request.getRequestDispatcher("/views/login.jsp").forward(request, response);
			return;
		}

		UserSessionDto user = authRepository.authenticate(email.trim(), password.trim());

		if (user != null) {

			HttpSession session = request.getSession(true);
			session.setAttribute("currentUser", user);

			response.sendRedirect(request.getContextPath() + "/employee-list");
		} else {

			request.setAttribute("errorMessage", "Email Or Password Errors");
			request.getRequestDispatcher("/views/login.jsp").forward(request, response);
		}
	}
}