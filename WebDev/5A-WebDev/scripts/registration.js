// Handle user registration
function register() {

    // Get input values from form
    let fullName = document.getElementById("fullName").value;
    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;
    let confirmPassword = document.getElementById("confirmPassword").value;

    let userDetails = []; // array to store users

    // Email validation using RegEx
    let emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    // Validate full name length
    if (fullName.trim().length < 2) {
        showAlert("Please enter your full name");
        return;
    }

    // Validate email format
    if (!emailRegex.test(email)) {
        showAlert("Invalid email address!");
        return;
    }

    // Validate password length
    if (password.length < 8) {
        showAlert("Password must contain at least 8 characters!");
        return;
    }

    // Check if passwords match
    if (confirmPassword !== password) {
        showAlert("Passwords do not match!");
        return;
    }

    // Load existing users from localStorage if exists
    if (localStorage.getItem("users") !== null) {
        userDetails = JSON.parse(localStorage.getItem("users"));
    }

    // Add new user object to users array
    userDetails.push({
        name: fullName,
        user: email,
        password: password
    });

    // Save updated users array back to localStorage
    localStorage.setItem("users", JSON.stringify(userDetails));

    // Show success message
    showAlert("Registration completed successfully!");

    // Redirect to login page after short delay
    setTimeout(goToLogin, 1500);
}

// Display alert message on the screen
function showAlert(message) {

    let alertBox = document.createElement("div");
    alertBox.textContent = message;
    alertBox.className = "miniAlert";

    document.body.appendChild(alertBox);

    // Remove alert after 5 seconds
    setTimeout(function () {
        alertBox.remove();
    }, 5000);
}

// Navigate to login page
function goToLogin() {
    window.location.href = "login.html";
}
