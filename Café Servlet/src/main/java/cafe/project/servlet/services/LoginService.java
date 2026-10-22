package cafe.project.servlet.services;

import cafe.project.servlet.models.LoginDto;
import cafe.project.servlet.repositories.LoginRepository;
import cafe.project.servlet.utils.PasswordService;

public class LoginService {
	
	private LoginRepository repo = new LoginRepository();
	private PasswordService pws = new PasswordService();
	
	
	public LoginDto findByEmail(String email) {
		return (repo.findByEmail(email) == null) ? null : repo.findByEmail(email);
	}
	
	public LoginDto findByLogin(LoginDto dto) {
		if(repo.findByEmail(dto.getEmail()) == null) {System.out.println("if method"); return null;}
		return (pws.matches(dto.getPassword(), repo.findByEmail(dto.getEmail()).getPassword())) ? repo.findByLogin(dto.getEmail(), repo.findByEmail(dto.getEmail()).getPassword()) : null;
	}

}
