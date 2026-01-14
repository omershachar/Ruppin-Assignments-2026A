document.addEventListener('DOMContentLoaded', function() {
    updateRechargePage();
});

// Update recharge page with current balance
function updateRechargePage() {
    const balance = parseFloat(localStorage.getItem('cardBalance') || '0');
    document.getElementById('current-balance').textContent = balance.toFixed(2) + ' $';
}

// Recharge card
function rechargeCard() {
    const amountInput = document.getElementById('recharge-amount');
    const amount = parseFloat(amountInput.value);
    const messageDiv = document.getElementById('recharge-message');
    
    // Validate input
    if (!amount || amount <= 0) {
        messageDiv.className = 'message error';
        messageDiv.textContent = 'please enter a valid value';
        return;
    }
    
    // Get current balance
    const currentBalance = parseFloat(localStorage.getItem('cardBalance') || '0');
    const newBalance = currentBalance + amount;
    
    // Update balance in LocalStorage
    localStorage.setItem('cardBalance', newBalance.toFixed(2));
    
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
