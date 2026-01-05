// Main script with navigation and LocalStorage management

// Initialize on page load
document.addEventListener('DOMContentLoaded', function() {
    initializeApp();
});

// Initialize application
function initializeApp() {
    // Initialize LocalStorage if needed
    if (!localStorage.getItem('username')) {
        const username = prompt('Enter your name:', 'Guest');
        localStorage.setItem('username', username || 'Guest');
    }
    
    // Initialize balance if not exists
    if (!localStorage.getItem('cardBalance')) {
        localStorage.setItem('cardBalance', '0');
    }
    
    // Load menu items from JSON if not in LocalStorage
    if (!localStorage.getItem('menuItems')) {
        loadMenuItems();
    }
    
    // Initialize orders if not exists
    if (!localStorage.getItem('orders')) {
        localStorage.setItem('orders', JSON.stringify([]));
    }
    
    // Update dashboard if on dashboard page
    if (document.getElementById('greeting')) {
        updateDashboard();
        // Update histogram periodically
        setInterval(updateHistogram, 5000);
    }
    
    // Update recharge page if on recharge page
    if (document.getElementById('current-balance')) {
        updateRechargePage();
    }
}

// Update dashboard with current data
function updateDashboard() {
    // Update greeting
    const username = localStorage.getItem('username') || 'Guest';
    document.getElementById('greeting').textContent = `Hello, ${username}!`;
    
    // Update date and time
    updateDateTime();
    setInterval(updateDateTime, 1000);
    
    // Update balance display
    const balance = parseFloat(localStorage.getItem('cardBalance') || '0');
    document.getElementById('balance-display').textContent = balance.toFixed(2);
    
    // Update histogram
    updateHistogram();
}

// Update date and time display
function updateDateTime() {
    const now = new Date();
    const options = {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit',
        hour12: false
    };
    const dateTimeStr = now.toLocaleDateString('en-US', options);
    document.getElementById('datetime').textContent = dateTimeStr;
}

// Load menu items from JSON file
function loadMenuItems() {
    fetch('menu-items.json')
        .then(response => response.json())
        .then(data => {
            localStorage.setItem('menuItems', JSON.stringify(data));
        })
        .catch(error => {
            console.error('Error loading menu items:', error);
            // Use default menu items if file not found
            const defaultMenu = getDefaultMenuItems();
            localStorage.setItem('menuItems', JSON.stringify(defaultMenu));
        });
}

// Get default menu items (fallback)
function getDefaultMenuItems() {
    return {
        "Drinks": [
            { "name": "Black Coffee", "price": 8.5 },
            { "name": "Black Coffee", "price": 12 },
            { "name": "Cappuccino", "price": 14 },
            { "name": "Espresso", "price": 10 },
            { "name": "Tea", "price": 9 },
            { "name": "Orange Juice", "price": 15 }
        ],
        "Pastries": [
            { "name": "Croissant", "price": 12 },
            { "name": "Cheese Bread", "price": 10 },
            { "name": "Chocolate Cake", "price": 14 },
            { "name": "Beagle", "price": 8 }
        ],
        "Salads": [
            { "name": "Greek Salad", "price": 35 },
            { "name": "Caesar Salad", "price": 38 },
            { "name": "Tabouli Salad", "price": 32 }
        ],
        "Wraps": [
            { "name": "Wrap with Tuna", "price": 28 },
            { "name": "Wrap with Cheese", "price": 25 },
            { "name": "Wrap with Avocado", "price": 30 }
        ],
        "Toast": [
            { "name": "Toast with Cheese", "price": 22 },
            { "name": "Toast with Tuna", "price": 26 },
            { "name": "Toast with Vegetables", "price": 24 }
        ],
        "Breakfast": [
            { "name": "Israeli Breakfast", "price": 55 },
            { "name": "English Breakfast", "price": 65 },
            { "name": "Shake", "price": 42 }
        ],
        "Desserts": [
            { "name": "Cake", "price": 48 },
            { "name": "Ice Cream", "price": 35 },
            { "name": "Pancakes", "price": 42 }
        ]
    };
}

// Navigation functions
function navigateToDashboard() {
    window.location.href = 'index.html';
}

function navigateToOrders() {
    window.location.href = 'orders.html';
}

function navigateToCategories() {
    window.location.href = 'categories.html';
}

function navigateToItems(category) {
    window.location.href = `items.html?category=${encodeURIComponent(category)}`;
}

function navigateToRecharge() {
    window.location.href = 'recharge.html';
}

// Update histogram chart
function updateHistogram() {
    const orders = JSON.parse(localStorage.getItem('orders') || '[]');
    const productCounts = {};
    
    // Count products
    orders.forEach(order => {
        const productName = order.itemName;
        productCounts[productName] = (productCounts[productName] || 0) + 1;
    });
    
    // Get canvas and draw histogram
    const canvas = document.getElementById('histogram-chart');
    if (!canvas) return;
    
    const ctx = canvas.getContext('2d');
    const width = canvas.width = canvas.offsetWidth;
    const height = canvas.height = 300;
    
    // Clear canvas
    ctx.clearRect(0, 0, width, height);
    
    if (Object.keys(productCounts).length === 0) {
        ctx.fillStyle = '#666';
        ctx.font = '20px Arial';
        ctx.textAlign = 'center';
        ctx.fillText('No data to display', width / 2, height / 2);
        return;
    }
    
    // Sort products by count
    const sortedProducts = Object.entries(productCounts)
        .sort((a, b) => b[1] - a[1])
        .slice(0, 10); // Top 10 products
    
    const maxCount = Math.max(...sortedProducts.map(p => p[1]));
    const barWidth = width / (sortedProducts.length + 1);
    const maxBarHeight = height - 80;
    
    // Draw bars
    sortedProducts.forEach(([product, count], index) => {
        const barHeight = (count / maxCount) * maxBarHeight;
        const x = (index + 1) * barWidth;
        const y = height - 40 - barHeight;
        
        // Draw bar
        ctx.fillStyle = '#667eea';
        ctx.fillRect(x - barWidth / 2 + 10, y, barWidth - 20, barHeight);
        
        // Draw count on top
        ctx.fillStyle = '#333';
        ctx.font = '14px Arial';
        ctx.textAlign = 'center';
        ctx.fillText(count, x, y - 5);
        
        // Draw product name (rotated)
        ctx.save();
        ctx.translate(x, height - 20);
        ctx.rotate(-Math.PI / 4);
        ctx.font = '12px Arial';
        ctx.fillText(product.length > 10 ? product.substring(0, 10) + '...' : product, 0, 0);
        ctx.restore();
    });
}

// Purchase item function
function purchaseItem(itemName, itemPrice, category) {
    const balance = parseFloat(localStorage.getItem('cardBalance') || '0');
    
    // Check if balance is sufficient
    if (balance < itemPrice) {
        alert('Balance is not sufficient for this purchase');
        return false;
    }
    
    // Deduct from balance
    const newBalance = balance - itemPrice;
    localStorage.setItem('cardBalance', newBalance.toFixed(2));
    
    // Add to orders
    const orders = JSON.parse(localStorage.getItem('orders') || '[]');
    const newOrder = {
        itemName: itemName,
        price: itemPrice,
        category: category,
        date: new Date().toISOString()
    };
    orders.push(newOrder);
    localStorage.setItem('orders', JSON.stringify(orders));
    
    return true;
}