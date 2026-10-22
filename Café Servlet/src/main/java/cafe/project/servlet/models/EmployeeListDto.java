package cafe.project.servlet.models;
import java.time.LocalDate;

public class EmployeeListDto {
	private String employeeId;
	private String name;
	private String email;
	private String phone;
	private double salary; 
	private String address;
	private LocalDate dob;
	private String photopath;
	private Gender gender;

	private String roleName;
	private String branchName;
	private String statusName;

	public EmployeeListDto() {
	}

	public EmployeeListDto(String employeeId, String name, String email, String phone, double salary, String address,
			LocalDate dob, String photopath, Gender gender, String roleName, String branchName, String statusName) {
		this.employeeId = employeeId;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.salary = salary;
		this.address = address;
		this.dob = dob;
		this.photopath = photopath;
		this.gender = gender;
		this.roleName = roleName;
		this.branchName = branchName;
		this.statusName = statusName;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getPhotopath() {
		return photopath;
	}

	public void setPhotopath(String photopath) {
		this.photopath = photopath;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}