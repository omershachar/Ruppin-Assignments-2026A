document.addEventListener('DOMContentLoaded', function() {
    updateRechargePage();
});

// Update recharge page with current balance
function updateRechargePage() {
    const balance = getCardBalance();
    const balanceElement = document.getElementById('current-balance');
    if (balanceElement) {
        balanceElement.textContent = balance.toFixed(2) + ' $';
    }
}

// Recharge card
function rechargeCard() {
    const amountInput = document.getElementById('recharge-amount');
    const amount = parseFloat(amountInput.value);
    const messageDiv = document.getElementById('recharge-message');
    
    // Validate input
    if (!amount || amount <= 0) {
        messageDiv.className = 'message error';
        messageDiv.textContent = 'Please enter a valid value';
        return;
    }
    
    // Add amount to balance using storage.js function
    const newBalance = addToCardBalance(amount);
    
    // Update display
    updateRechargePage();
    
    // Show success message
    messageDiv.className = 'message success';
    messageDiv.textContent = `The card loaded successfully! New balance: ${newBalance.toFixed(2)} $`;
    
    // Clear input
    amountInput.value = '';
    
    // Clear message after 3 seconds
    setTimeout(() => {
        messageDiv.textContent = '';
        messageDiv.className = 'message';
    }, 3000);
}
