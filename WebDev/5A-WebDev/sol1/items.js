// Items page specific script

document.addEventListener('DOMContentLoaded', function() {
    loadItems();
});

// Load items for selected category
function loadItems() {
    const urlParams = new URLSearchParams(window.location.search);
    const category = urlParams.get('category');
    
    if (!category) {
        document.getElementById('category-title').textContent = 'פריטים בקטגוריה';
        document.getElementById('no-items').style.display = 'block';
        return;
    }
    
    document.getElementById('category-title').textContent = `פריטים - ${category}`;
    
    const menuItems = JSON.parse(localStorage.getItem('menuItems') || '{}');
    const categoryItems = menuItems[category] || [];
    
    const itemsList = document.getElementById('items-list');
    const noItems = document.getElementById('no-items');
    
    if (categoryItems.length === 0) {
        itemsList.style.display = 'none';
        noItems.style.display = 'block';
        return;
    }
    
    itemsList.style.display = 'grid';
    noItems.style.display = 'none';
    
    // Clear existing items
    itemsList.innerHTML = '';
    
    // Display items
    categoryItems.forEach(item => {
        const itemCard = document.createElement('div');
        itemCard.className = 'item-card';
        
        const balance = parseFloat(localStorage.getItem('cardBalance') || '0');
        const canPurchase = balance >= item.price;
        
        itemCard.innerHTML = `
            <div class="item-name">${item.name}</div>
            <div class="item-price">${item.price.toFixed(2)} ₪</div>
            <button class="btn-purchase" 
                    onclick="handlePurchase('${item.name}', ${item.price}, '${category}')"
                    ${!canPurchase ? 'disabled' : ''}>
                ${canPurchase ? 'רכישה' : 'יתרה לא מספקת'}
            </button>
        `;
        
        itemsList.appendChild(itemCard);
    });
}

// Handle purchase
function handlePurchase(itemName, itemPrice, category) {
    if (purchaseItem(itemName, itemPrice, category)) {
        // Show success modal
        const modal = document.getElementById('success-modal');
        const details = document.getElementById('purchase-details');
        details.textContent = `נרכש: ${itemName} - ${itemPrice.toFixed(2)} ₪`;
        modal.style.display = 'block';
        
        // Reload items to update button states
        setTimeout(() => {
            loadItems();
        }, 100);
        
        // Update dashboard balance if exists
        if (document.getElementById('balance-display')) {
            const balance = parseFloat(localStorage.getItem('cardBalance') || '0');
            document.getElementById('balance-display').textContent = balance.toFixed(2);
        }
    }
}

// Close success modal
function closeSuccessModal() {
    document.getElementById('success-modal').style.display = 'none';
}

// Close modal when clicking outside
window.onclick = function(event) {
    const modal = document.getElementById('success-modal');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
}

