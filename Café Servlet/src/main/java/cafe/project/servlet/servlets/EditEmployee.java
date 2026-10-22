package cafe.project.servlet.servlets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;

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

@WebServlet("/edit-employee")
@MultipartConfig
public class EditEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EmployeeRepository employeeRepository = new EmployeeRepository();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		EmployeeEntryDto employee = employeeRepository.getById(id);

		request.setAttribute("employee", employee);
		request.getRequestDispatcher("/views/employee-edit.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String id = request.getParameter("employeeId");
		EmployeeEntryDto emp = new EmployeeEntryDto();

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
		String fileName = null;

		if (filePart != null && filePart.getSize() > 0) {

			String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

			fileName = System.currentTimeMillis() + "_" + originalFileName;

			String uploadPath = request.getServletContext().getRealPath("") + File.separator + "uploads";
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}

			filePart.write(uploadPath + File.separator + fileName);

			emp.setPhotopath("uploads/" + fileName);
		} else {

			emp.setPhotopath(request.getParameter("existingPhotopath"));
		}

		employeeRepository.edit(id, emp);

		response.sendRedirect("employee-list");
	}
}