package cafe.project.servlet.servlets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

import cafe.project.servlet.models.EmployeeEntryDto;
import cafe.project.servlet.models.Gender;
import cafe.project.servlet.repositories.EmployeeRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/create-employee")
@MultipartConfig
public class CreateEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EmployeeRepository employeeRepository = new EmployeeRepository();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/views/employee-add.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		EmployeeEntryDto emp = new EmployeeEntryDto();
		emp.setEmployeeId(UUID.randomUUID().toString());
		emp.setName(request.getParameter("name"));
		emp.setEmail(request.getParameter("email"));
		emp.setPassword(request.getParameter("password"));
		emp.setPhone(request.getParameter("phone"));
		emp.setSalary(Double.parseDouble(request.getParameter("salary")));
		emp.setAddress(request.getParameter("address"));
		emp.setDob(LocalDate.parse(request.getParameter("dob")));
		emp.setGender(Gender.valueOf(request.getParameter("gender")));
		emp.setEmployeeStatusId(request.getParameter("employeeStatusId"));
		emp.setEmployeeRoleId(request.getParameter("employeeRoleId"));
		emp.setBranchId(request.getParameter("branchId"));
		emp.setPhotopath(request.getParameter("photopath"));
		Part filePart = request.getPart("profileImage");
		if (filePart != null && filePart.getSize() > 0) {
			String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
			String fileName = System.currentTimeMillis() + "_" + originalFileName;

			String uploadPath = request.getServletContext().getRealPath("") + File.separator + "uploads";
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}

			filePart.write(uploadPath + File.separator + fileName);
			emp.setPhotopath("uploads/" + fileName);
		} else {

			emp.setPhotopath(null);
		}
		employeeRepository.add(emp);

		response.sendRedirect("employee-list");
	}
}