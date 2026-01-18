// Dashboard functionality
// Update username greeting and date/time display
function updateDashboardHeader() {
    const username = getUsername();
    const greetingElement = document.getElementById('greeting-user');
    if (greetingElement) {
        greetingElement.textContent = `Hello, ${username}! 👋`;
    }
    
    // Update date and time
    const datetimeElement = document.getElementById('datetime');
    if (datetimeElement) {
        const now = new Date();
        const dateStr = now.toLocaleDateString();
        const timeStr = now.toLocaleTimeString();
        datetimeElement.textContent = `${dateStr} ${timeStr}`;
    }
    
    // Update balance display
    const balanceDisplay = document.getElementById('balance-display');
    if (balanceDisplay) {
        const balance = getCardBalance();
        balanceDisplay.textContent = balance.toFixed(2);
    }
}

// Create histogram chart showing products bought since today
function createHistogramChart() {
    const canvas = document.getElementById('histogram-chart');
    if (!canvas) return;
    
    const orders = getOrdersFile();
    if (!orders || orders.length === 0) {
        return;
    }
    
    // Filter orders since today
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    const todayTimestamp = today.getTime();
    
    const todayOrders = orders.filter(order => {
        const orderDate = new Date(order.createdAt);
        return orderDate >= today;
    });
    
    if (todayOrders.length === 0) {
        return;
    }
    
    // Count products by name
    const productCounts = {};
    todayOrders.forEach(order => {
        productCounts[order.name] = (productCounts[order.name] || 0) + 1;
    });
    
    // Prepare chart data
    const labels = Object.keys(productCounts);
    const data = Object.values(productCounts);
    
    // Create chart
    new Chart(canvas, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Quantity',
                data: data,
                backgroundColor: 'rgba(75, 192, 192, 0.6)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        stepSize: 1
                    }
                }
            }
        }
    });
}

// Initialize dashboard when page loads
document.addEventListener('DOMContentLoaded', function() {
    initializeStorage();
    updateDashboardHeader();
    createHistogramChart();
    
    // Update time every minute
    setInterval(updateDashboardHeader, 60000);
});
