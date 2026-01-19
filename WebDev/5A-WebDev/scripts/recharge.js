// Run code only after the DOM is fully loaded
document.addEventListener('DOMContentLoaded', function () {
    updateRechargePage(); // update balance display on page load
});

// Update recharge page with current balance
function updateRechargePage() {

    // Get current balance from localStorage (default 0 if not exists)
    const balance = parseFloat(localStorage.getItem('cardBalance') || '0');

    // Display balance with two decimal places
    document.getElementById('current-balance').textContent =
        balance.toFixed(2) + ' $';
}

// Recharge card with entered amount
function rechargeCard() {

    // Get input element and message container
    const amountInput = document.getElementById('recharge-amount');
    const amount = parseFloat(amountInput.value);
    const messageDiv = document.getElementById('recharge-message');

    // Validate input value
    if (!amount || amount <= 0) {
        showAlert('please enter a valid value');
        return;
    }

    // Get current balance from localStorage
    const currentBalance =
        parseFloat(localStorage.getItem('cardBalance') || '0');

    // Calculate new balance
    const newBalance = currentBalance + amount;

    // Save updated balance to localStorage
    localStorage.setItem('cardBalance', newBalance.toFixed(2));

    // Update balance display on the page
    updateRechargePage();

    // Show success alert
    showAlert(`The card loaded successfully! New balance: ${newBalance.toFixed(2)} $`);

    // Clear input field
    amountInput.value = '';

    // Clear message area after 3 seconds
    setTimeout(() => {
        messageDiv.textContent = '';
        messageDiv.className = 'message';
    }, 3000);

    // Show alert message on the screen
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
}
