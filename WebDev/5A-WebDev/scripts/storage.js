// Centralized LocalStorage management utilities
// Provides consistent interface for all storage operations

// Storage keys constants
const STORAGE_KEYS = {
    USERNAME: 'username',
    CARD_BALANCE: 'cardBalance',
    MENU_FILE: 'menuFile',
    ORDERS_FILE: 'ordersFile'
};

// Get username from storage or return default
function getUsername() {
    return localStorage.getItem(STORAGE_KEYS.USERNAME) || 'Guest';
}

// Set username in storage
function setUsername(username) {
    if (username && username.trim()) {
        localStorage.setItem(STORAGE_KEYS.USERNAME, username.trim());
    }
}

// Get card balance from storage
function getCardBalance() {
    const balance = localStorage.getItem(STORAGE_KEYS.CARD_BALANCE);
    return balance ? parseFloat(balance) : 0;
}

// Set card balance in storage
function setCardBalance(balance) {
    localStorage.setItem(STORAGE_KEYS.CARD_BALANCE, balance.toFixed(2));
}

// Add amount to card balance
function addToCardBalance(amount) {
    const currentBalance = getCardBalance();
    const newBalance = currentBalance + parseFloat(amount);
    setCardBalance(newBalance);
    return newBalance;
}

// Deduct amount from card balance
function deductFromCardBalance(amount) {
    const currentBalance = getCardBalance();
    const newBalance = currentBalance - parseFloat(amount);
    if (newBalance < 0) {
        return null; // Insufficient balance
    }
    setCardBalance(newBalance);
    return newBalance;
}

// Get menu file from storage
function getMenuFile() {
    const menuFile = localStorage.getItem(STORAGE_KEYS.MENU_FILE);
    if (!menuFile) {
        return null;
    }
    try {
        return JSON.parse(menuFile);
    } catch (e) {
        console.error('Error parsing menu file:', e);
        return null;
    }
}

// Set menu file in storage
function setMenuFile(menuData) {
    if (typeof menuData === 'string') {
        localStorage.setItem(STORAGE_KEYS.MENU_FILE, menuData);
    } else {
        localStorage.setItem(STORAGE_KEYS.MENU_FILE, JSON.stringify(menuData));
    }
}

// Get orders file from storage
function getOrdersFile() {
    const ordersFile = localStorage.getItem(STORAGE_KEYS.ORDERS_FILE);
    if (!ordersFile) {
        return [];
    }
    try {
        return JSON.parse(ordersFile);
    } catch (e) {
        console.error('Error parsing orders file:', e);
        return [];
    }
}

// Set orders file in storage
function setOrdersFile(ordersArray) {
    localStorage.setItem(STORAGE_KEYS.ORDERS_FILE, JSON.stringify(ordersArray));
}

// Add order to orders file
function addOrder(order) {
    const orders = getOrdersFile();
    orders.push(order);
    setOrdersFile(orders);
    return orders;
}

// Initialize storage with default values if needed
function initializeStorage() {
    // Initialize username if not exists
    if (!localStorage.getItem(STORAGE_KEYS.USERNAME)) {
        setUsername('Guest');
    }
    
    // Initialize card balance if not exists
    if (!localStorage.getItem(STORAGE_KEYS.CARD_BALANCE)) {
        setCardBalance(0);
    }
    
    // Initialize orders file if not exists
    if (!localStorage.getItem(STORAGE_KEYS.ORDERS_FILE)) {
        setOrdersFile([]);
    }
}
