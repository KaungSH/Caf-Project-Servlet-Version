package cafe.project.servlet.servlets;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import cafe.project.servlet.models.DailyRegisterEntryDto;
import cafe.project.servlet.models.DailyRegisterListDto;
import cafe.project.servlet.models.UserSessionDto;
import cafe.project.servlet.repositories.DailyRegisterRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/daily-register")
@MultipartConfig
public class DailyRegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DailyRegisterRepository dailyRegisterRepository = new DailyRegisterRepository();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		UserSessionDto loginUser = (session != null) ? (UserSessionDto) session.getAttribute("loginUser") : null;

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		List<DailyRegisterListDto> registerList = dailyRegisterRepository.getAll();
		request.setAttribute("registers", registerList);

		DailyRegisterListDto activeRegister = null;
		LocalDate today = LocalDate.now();

		for (DailyRegisterListDto reg : registerList) {

			if (today.equals(reg.getDate()) && "OPENED".equalsIgnoreCase(reg.getStatus_name())) {
				activeRegister = reg;
				break;
			}
		}

		request.setAttribute("activeRegister", activeRegister);
		request.setAttribute("today", today);
		request.getRequestDispatcher("/views/daily-register.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);
		UserSessionDto loginUser = (session != null) ? (UserSessionDto) session.getAttribute("loginUser") : null;

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		String action = request.getParameter("action");

		if ("open".equalsIgnoreCase(action)) {
			DailyRegisterEntryDto entryDto = new DailyRegisterEntryDto();
			entryDto.setRegister_id(UUID.randomUUID().toString());
			entryDto.setBranch_id(loginUser.getBranchId());
			entryDto.setEmployee_id(loginUser.getEmployeeId());
			entryDto.setRegister_status_id("1"); // 1 = OPENED
			entryDto.setDate(LocalDate.now());
			entryDto.setOpened_at(Time.valueOf(LocalTime.now()));
			entryDto.setClosed_at(null);

			dailyRegisterRepository.add(entryDto);

			response.sendRedirect(request.getContextPath() + "/pos");
			return;
		}

		else if ("close".equalsIgnoreCase(action)) {
			String registerId = request.getParameter("registerId");

			if (registerId != null && !registerId.trim().isEmpty()) {
				DailyRegisterEntryDto entryDto = dailyRegisterRepository.getById(registerId);
				if (entryDto != null) {
					entryDto.setRegister_status_id("2"); // 2 = CLOSED
					entryDto.setClosed_at(Time.valueOf(LocalTime.now()));

					dailyRegisterRepository.edit(registerId, entryDto);
				}
			}
		}

		response.sendRedirect(request.getContextPath() + "/daily-register");
	}
}