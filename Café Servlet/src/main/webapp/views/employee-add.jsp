<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Add New Employee - Cafe Luft</title>
<link
	href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@600;700&family=Plus+Jakarta+Sans:wght@400;500;600;700&display=swap"
	rel="stylesheet">
<style>
body {
	font-family: 'Plus Jakarta Sans', sans-serif;
	background-color: #F7F5F0;
	color: #1C1917;
	margin: 0;
	padding: 20px;
}

.container {
	max-width: 520px;
	margin: 0 auto;
	background-color: #FFFFFF;
	border: 1px solid rgba(212, 175, 55, 0.3);
	border-radius: 16px;
	padding: 24px 30px;
	box-shadow: 0 8px 24px rgba(0, 0, 0, 0.04);
}

.form-header {
	display: flex;
	align-items: center;
	gap: 12px;
	border-bottom: 2px solid #EBF2EE;
	padding-bottom: 10px;
	margin-bottom: 16px;
}

/* ပုံပေါ် mouse တင်ရင် နှိပ်လို့ရကြောင်းသိသာစေရန် cursor: pointer ပေးထားပါသည် */
.logo-label {
	cursor: pointer;
	display: inline-block;
}

.form-logo {
	width: 44px;
	height: 44px;
	border-radius: 50%;
	object-fit: cover;
	border: 2px solid #d4af37;
	display: block;
	transition: transform 0.2s, opacity 0.2s;
}

.form-logo:hover {
	transform: scale(1.05);
	opacity: 0.85;
}

h2 {
	font-family: 'Playfair Display', serif;
	font-size: 20px;
	font-weight: 700;
	color: #1B4332;
	margin: 0;
}

label {
	display: block;
	font-size: 11px;
	font-weight: 700;
	color: #475569;
	margin-bottom: 4px;
	text-transform: uppercase;
	letter-spacing: 0.04em;
}

input[type="text"], input[type="email"], input[type="password"], input[type="number"],
	input[type="date"], select {
	width: 100%;
	padding: 8px 12px;
	border: 1px solid #CBD5E1;
	border-radius: 8px;
	font-size: 13px;
	color: #0F172A;
	background-color: #F8FAFC;
	box-sizing: border-box;
	margin-bottom: 12px;
	transition: all 0.2s;
}

input:focus, select:focus {
	outline: none;
	border-color: #1B4332;
	background-color: #FFFFFF;
	box-shadow: 0 0 0 3px rgba(27, 67, 50, 0.1);
}

button[type="submit"] {
	background: linear-gradient(135deg, #1B4332 0%, #081C15 100%);
	color: #FFFFFF;
	padding: 10px 18px;
	border: none;
	border-radius: 10px;
	font-size: 13px;
	font-weight: 700;
	cursor: pointer;
	width: 100%;
	margin-top: 6px;
	box-shadow: 0 4px 12px rgba(27, 67, 50, 0.2);
}

button[type="submit"]:hover {
	opacity: 0.95;
}

.back-link {
	display: inline-block;
	margin-top: 12px;
	color: #475569;
	text-decoration: none;
	font-size: 12px;
	font-weight: 600;
}

.back-link:hover {
	color: #1B4332;
	text-decoration: underline;
}
</style>
</head>
<body>
	<div class="container">

		<form
			action="${pageContext.request.contextPath}/employee?action=insert"
			method="post" enctype="multipart/form-data">
			<div class="form-header">

				<label for="imageUpload" class="logo-label"
					title="Click to upload photo"> <img id="previewLogo"
					src="https://images.unsplash.com/photo-1541167760496-1628856ab772?auto=format&fit=crop&w=100&q=80"
					alt="Cafe Luft Logo" class="form-logo">
				</label>

				<!-- ဖိုင်ရွေးရန် input ကို display: none ဖြင့် ဝှက်ထားပါသည် -->
				<input type="file" id="imageUpload" name="profileImage"
					accept="image/*" style="display: none;"
					onchange="previewImage(event)">

				<h2>Add New Employee</h2>
			</div>

			<label>Name:</label> <input type="text" name="name" required>

			<label>Email:</label> <input type="email" name="email" required>

			<label>Password:</label> <input type="password" name="password"
				required> <label>Phone:</label> <input type="text"
				name="phone"> <label>Salary:</label> <input type="number"
				step="0.01" name="salary"> <label>Address:</label> <input
				type="text" name="address"> <label>Date of Birth:</label> <input
				type="date" name="dob"> <label>Gender:</label> <select
				name="gender">
				<option value="MALE">Male</option>
				<option value="FEMALE">Female</option>
				<option value="OTHER">Other</option>
			</select> <input type="hidden" name="employeeStatusId" value="2"> <input
				type="hidden" name="employeeRoleId" value="2"> <input
				type="hidden" name="branchId" value="1">

			<button type="submit">Save Employee</button>
		</form>

		<a href="${pageContext.request.contextPath}/employee?action=list"
			class="back-link">&larr; Back to Employee List</a>
	</div>

	<script>
		function previewImage(event) {
			const file = event.target.files[0];
			if (file) {
				const reader = new FileReader();
				reader.onload = function(e) {
					document.getElementById('previewLogo').src = e.target.result;
				}
				reader.readAsDataURL(file);
			}
		}
	</script>
</body>
</html>