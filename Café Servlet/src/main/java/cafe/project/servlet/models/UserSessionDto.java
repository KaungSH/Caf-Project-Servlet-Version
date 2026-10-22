package cafe.project.servlet.models;

public class UserSessionDto {
private String employeeId;
private String name;
private String email;
private String roleId;
private String roleName;
private String branchId;
private String branchName;

public UserSessionDto() {}
public UserSessionDto(String employeeId, String name, String email, String roleId, String roleName, String branchId, String branchName) {
	this.employeeId = employeeId;
	this.name = name;
	this.email = email;
	this.roleId = roleId;
	this.roleName = roleName;
	this.branchId = branchId;
	this.branchName = branchName;
	
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
public String getRoleId() {
	return roleId;
}
public void setRoleId(String roleId) {
	this.roleId = roleId;
}
public String getRoleName() {
	return roleName;
}
public void setRoleName(String roleName) {
	this.roleName = roleName;
}
public String getBranchId() {
	return branchId;
}
public void setBranchId(String branchId) {
	this.branchId = branchId;
}
public String getBranchName() {
	return branchName;
}
public void setBranchName(String branchName) {
	this.branchName = branchName;
}

}
