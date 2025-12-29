// Main script with navigation and LocalStorage management

// Initialize on page load
document.addEventListener('DOMContentLoaded', function() {
    initializeApp();
});

// Initialize application
function initializeApp() {
    // Initialize LocalStorage if needed
    if (!localStorage.getItem('username')) {
        const username = prompt('הזן את שמך:', 'אורח');
        localStorage.setItem('username', username || 'אורח');
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
    const username = localStorage.getItem('username') || 'אורח';
    document.getElementById('greeting').textContent = `שלום, ${username}!`;
    
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
    const dateTimeStr = now.toLocaleDateString('he-IL', options);
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
        "משקאות": [
            { "name": "קפה שחור", "price": 8.5 },
            { "name": "קפה הפוך", "price": 12 },
            { "name": "קפוצ'ינו", "price": 14 },
            { "name": "אספרסו", "price": 10 },
            { "name": "תה", "price": 9 },
            { "name": "מיץ תפוזים", "price": 15 }
        ],
        "מאפים": [
            { "name": "קרואסון", "price": 12 },
            { "name": "בורקס גבינה", "price": 10 },
            { "name": "מאפין שוקולד", "price": 14 },
            { "name": "בייגלה", "price": 8 }
        ],
        "סלטים": [
            { "name": "סלט יווני", "price": 35 },
            { "name": "סלט קיסר", "price": 38 },
            { "name": "סלט טאבולה", "price": 32 }
        ],
        "כריכים": [
            { "name": "כריך טונה", "price": 28 },
            { "name": "כריך גבינה צהובה", "price": 25 },
            { "name": "כריך אבוקדו", "price": 30 }
        ],
        "טוסטים": [
            { "name": "טוסט גבינה", "price": 22 },
            { "name": "טוסט טונה", "price": 26 },
            { "name": "טוסט ירקות", "price": 24 }
        ],
        "ארוחות בוקר": [
            { "name": "ארוחת בוקר ישראלית", "price": 55 },
            { "name": "ארוחת בוקר אנגלית", "price": 65 },
            { "name": "שקשוקה", "price": 42 }
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
        ctx.fillText('אין נתונים להצגה', width / 2, height / 2);
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
        alert('יתרת הכרטיס אינה מספקת לרכישה זו');
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

