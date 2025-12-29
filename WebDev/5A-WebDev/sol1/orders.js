// Orders page specific script

document.addEventListener('DOMContentLoaded', function() {
    loadOrders();
});

// Load orders from JSON file
function loadOrdersFromFile(event) {
    const file = event.target.files[0];
    if (!file) return;
    
    const reader = new FileReader();
    reader.onload = function(e) {
        try {
            const fileOrders = JSON.parse(e.target.result);
            
            // Validate that it's an array
            if (!Array.isArray(fileOrders)) {
                alert('קובץ לא תקין. יש לצפות למערך של הזמנות.');
                return;
            }
            
            // Get existing orders
            const existingOrders = JSON.parse(localStorage.getItem('orders') || '[]');
            
            // Merge with existing orders (avoid duplicates based on date and item)
            const mergedOrders = [...existingOrders];
            fileOrders.forEach(newOrder => {
                // Check if order already exists
                const exists = existingOrders.some(existing => 
                    existing.itemName === newOrder.itemName && 
                    existing.date === newOrder.date
                );
                if (!exists) {
                    mergedOrders.push(newOrder);
                }
            });
            
            // Sort by date (newest first)
            mergedOrders.sort((a, b) => new Date(b.date) - new Date(a.date));
            
            // Save to LocalStorage
            localStorage.setItem('orders', JSON.stringify(mergedOrders));
            
            // Reload display
            loadOrders();
            
            // Update histogram on dashboard if we're there
            if (typeof updateHistogram === 'function') {
                updateHistogram();
            }
            
            alert(`נטענו ${fileOrders.length} הזמנות בהצלחה!`);
        } catch (error) {
            alert('שגיאה בקריאת הקובץ: ' + error.message);
        }
    };
    reader.readAsText(file);
    
    // Reset file input
    event.target.value = '';
}

// Load and display orders
function loadOrders() {
    const orders = JSON.parse(localStorage.getItem('orders') || '[]');
    const ordersList = document.getElementById('orders-list');
    const noOrders = document.getElementById('no-orders');
    
    if (orders.length === 0) {
        ordersList.style.display = 'none';
        noOrders.style.display = 'block';
        return;
    }
    
    ordersList.style.display = 'block';
    noOrders.style.display = 'none';
    
    // Sort orders by date (newest first)
    orders.sort((a, b) => new Date(b.date) - new Date(a.date));
    
    // Clear existing orders
    ordersList.innerHTML = '';
    
    // Display orders
    orders.forEach(order => {
        const orderItem = document.createElement('div');
        orderItem.className = 'order-item';
        
        const date = new Date(order.date);
        const dateStr = date.toLocaleDateString('he-IL', {
            year: 'numeric',
            month: 'long',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
        
        orderItem.innerHTML = `
            <div class="order-info">
                <div class="order-item-name">${order.itemName}</div>
                <div class="order-item-date">${dateStr}</div>
            </div>
            <div class="order-item-price">${order.price.toFixed(2)} ₪</div>
        `;
        
        ordersList.appendChild(orderItem);
    });
}

