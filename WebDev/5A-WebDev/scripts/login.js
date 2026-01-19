// Initialize users array in localStorage if it does not exist
if (localStorage.getItem("users") === null) {
    localStorage.setItem("users", JSON.stringify([]));
}

// Handle user login
function login() {

    // Get users data from localStorage
    let userDetails = JSON.parse(localStorage.getItem("users"));

    // Get email and password input values
    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;

    // Check if user exists and credentials are correct
    let userName = userFound(email, password, userDetails);

    if (userName !== null) {
        showAlert("Login successful!");

        // Redirect to dashboard with username in URL after short delay
        setTimeout(function () {
            window.location.href = `dashboard.html?user=${userName}`;
        }, 1500);

    } else {
        showAlert("Invalid email or password!");
    }
}

// Check if user with given email and password exists
function userFound(email, password, userDetails) {

    // Remove unnecessary spaces from inputs
    let email2 = email.trim();
    let password2 = password.trim();
    let userName = "";

    // Loop through users array
    for (let i = 0; i < userDetails.length; i++) {

        // Match email and password
        if (userDetails[i].user === email2 &&
            userDetails[i].password === password2) {

            userName = userDetails[i].name; // get user's display name
            return userName; // user found
        }
    }

    // User not found
    return null;
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

// Navigate to registration page
function goToRegister() {
    window.location.href = "registration.html";
}
