package cafe.project.servlet.models;

import java.time.LocalDate;

public class EmployeeEntryDto {

	private String employeeId;
	private String name;
	private String email;
	private String password;
	private String photopath;
	private String phone;
	private Double salary;
	private String address;
	private LocalDate dob;
	private Gender gender;
	private String employeeStatusId;
	private String employeeRoleId;
	private String branchId;

	public EmployeeEntryDto() {
	}

	public EmployeeEntryDto(String employeeId, String name, String email, String password, String photopath,
			String phone, Double salary, String address, LocalDate dob, Gender gender, String employeeStatusId,
			String employeeRoleId, String branchId) {
		this.employeeId = employeeId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.photopath = photopath;
		this.phone = phone;
		this.salary = salary;
		this.address = address;
		this.dob = dob;
		this.gender = gender;
		this.employeeStatusId = employeeStatusId;
		this.employeeRoleId = employeeRoleId;
		this.branchId = branchId;
	}

	// Getters and Setters (Standard camelCase)
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhotopath() {
		return photopath;
	}

	public void setPhotopath(String photopath) {
		this.photopath = photopath;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getEmployeeStatusId() {
		return employeeStatusId;
	}

	public void setEmployeeStatusId(String employeeStatusId) {
		this.employeeStatusId = employeeStatusId;
	}

	public String getEmployeeRoleId() {
		return employeeRoleId;
	}

	public void setEmployeeRoleId(String employeeRoleId) {
		this.employeeRoleId = employeeRoleId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
}