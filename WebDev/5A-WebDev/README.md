# Coffee Shop Order Application - Assignment 5

## Project Overview
A client-side web application simulating a coffee shop ordering system. Users can browse menu items, place orders, manage their member card balance, view order history, and get AI-powered recommendations.

---

## Project Structure

```
5A-WebDev/
├── index.html              # Entry point - redirects to login
├── pages/
│   ├── login.html          # User login page
│   ├── registration.html   # User registration page
│   ├── dashboard.html      # Main dashboard
│   ├── categories.html     # Menu categories display
│   ├── items.html          # Items in selected category
│   ├── orders.html         # Order history
│   ├── recharge.html       # Member card recharge
│   ├── management.html     # Upload JSON files
│   └── recommendations.html # AI recommendations (Part 2)
├── scripts/
│   ├── global.js           # Navigation functions
│   ├── login.js            # Login logic
│   ├── registration.js     # Registration logic
│   ├── dashboard.js        # Dashboard features (histogram, slider)
│   ├── categories.js       # Category click handling
│   ├── items.js            # Item display and purchase
│   ├── orders.js           # Order history table
│   ├── recharge.js         # Balance recharge
│   ├── management.js       # JSON file upload
│   └── recommendations.js  # AI API integration (Part 2)
├── styles/                 # CSS files for each page
└── json files/
    ├── menu.json           # Sample menu data
    └── orders.json         # Sample orders data
```

---

## Data Flow Diagram

```
[Login/Register] --> [Dashboard] --> [Categories] --> [Items] --> Purchase
                          |                                          |
                          |                                          v
                          +----> [Recharge] <---- Balance Check <----+
                          |                                          |
                          +----> [Orders] <----- Order Saved --------+
                          |
                          +----> [Recommendations] ---> AI API ---> Display HTML
                          |
                          +----> [Management] ---> Upload JSON ---> localStorage
```

---

## localStorage Keys

| Key | Type | Description |
|-----|------|-------------|
| `users` | Array | User accounts: `[{name, user (email), password}]` |
| `currentUser` | String | Currently logged-in username |
| `menuFile` | Object | Menu items by category |
| `ordersFile` | Array | Orders: `[{name, price, createdAt}]` |
| `cardBalance` | Number | Member card balance |

---

## Page-by-Page Breakdown

### 1. Login Page (`login.html` + `login.js`)
- **Purpose**: User authentication
- **Key Functions**:
  - `login()` - Validates credentials against localStorage
  - `userFound(email, password, userDetails)` - Searches for matching user
  - `showAlert(message)` - Displays feedback messages
- **Flow**: User enters email/password -> Validates -> Redirects to dashboard with username in URL

### 2. Registration Page (`registration.html` + `registration.js`)
- **Purpose**: Create new user account
- **Validations**:
  - Full name: minimum 2 characters
  - Email: RegEx validation `/^[^\s@]+@[^\s@]+\.[^\s@]+$/`
  - Password: minimum 8 characters
  - Password confirmation match
- **Key Function**: `register()` - Validates inputs, saves to `users` array in localStorage

### 3. Dashboard (`dashboard.html` + `dashboard.js`)
- **Purpose**: Main hub with overview and navigation
- **Features**:
  - Greeting with username + current date
  - Card balance display
  - Histogram chart (uses Chart.js library)
  - Rotating slider with 3 slides (4-second interval)
- **Key Functions**:
  - `greetingUser()` - Gets username from URL or localStorage
  - `createHistogram()` - Builds bar chart from orders data
  - `dailyMessage()` - Time-based greeting (morning/afternoon/evening)
  - `DisplayMonthOrders()` - Shows monthly order count
  - `grade()` - Shows membership level (Silver >5, Gold >10 orders)
  - `nextSlide()` - Rotates between slider content

### 4. Categories Page (`categories.html` + `categories.js`)
- **Purpose**: Display 6 menu categories
- **Categories**: Drinks, Pastries, Salads, Sandwiches, Toasts, Breakfasts
- **Key Function**: `transferCategory(clicked)` - Passes selected category to items page via URL parameter

### 5. Items Page (`items.html` + `items.js`)
- **Purpose**: Display items from selected category, handle purchases
- **Flow**:
  1. Read category from URL parameter
  2. Load menu from `menuFile` in localStorage
  3. Dynamically create item cards
  4. Handle purchase with balance check
- **Key Functions**:
  - `checkOverdraft(clicked)` - Verifies sufficient balance
  - `addItemToOrdersFileKey(clicked)` - Saves order to localStorage

### 6. Orders Page (`orders.html` + `orders.js`)
- **Purpose**: Display order history in a table
- **Features**:
  - Sorted by date (newest first)
  - Shows: Name, Price, Purchase Date
- **Key Function**: `extractDate(date)` - Converts timestamp to readable format

### 7. Recharge Page (`recharge.html` + `recharge.js`)
- **Purpose**: Add money to member card
- **Key Functions**:
  - `updateRechargePage()` - Updates balance display
  - `rechargeCard()` - Adds amount to balance, saves to localStorage

### 8. Management Page (`management.html` + `management.js`)
- **Purpose**: Upload JSON files for menu and orders
- **Logic**:
  - If filename contains "menu" -> saves to `menuFile`
  - Otherwise -> appends to `ordersFile` (handles merge with existing)
- **Key Function**: `sortItemsByDate(ordersArr)` - Sorts orders by date descending

### 9. Recommendations Page (`recommendations.html` + `recommendations.js`) - **Part 2**
- **Purpose**: Get AI-powered recommendations based on order history
- **Flow**:
  1. Read orders from localStorage
  2. Send POST request to API with transactions
  3. Receive HTML response with recommendations
  4. Display using innerHTML
- **API Endpoint**: `https://yael-ex-client-side-2026a-197579115104.me-west1.run.app/get-recommendations`

---

## Extension Features Implemented

### Feature 1: Login & Registration System
- Email validation using RegEx
- Password minimum 8 characters
- Stores users in localStorage array
- Validates credentials on login

### Feature 2: Dashboard Information Additions (3+ items)
- **Daily Greeting**: Changes based on time (morning/afternoon/evening)
- **Monthly Orders Count**: "You've made X orders this month"
- **Membership Grade**: Silver (>5 orders), Gold (>10 orders)
- **Rotating Slider**: Auto-cycles through info every 4 seconds

---

## Technical Requirements Met

### HTML
- Semantic structure with meaningful element names
- Clean, valid HTML5

### CSS
- Responsive design with media queries
- Consistent color scheme
- Hover effects on interactive elements
- Organized CSS files per page

### JavaScript
- Meaningful variable/function names
- Code comments in English
- DOM manipulation using `createElement` and `appendChild`
- No inline HTML strings
- Clear user feedback messages (alerts)

---

## Quick Reference for Presentation

| Screen | localStorage Keys Used | Main Functions |
|--------|----------------------|----------------|
| Login | `users`, `currentUser` | `login()`, `userFound()` |
| Register | `users` | `register()` |
| Dashboard | `ordersFile`, `cardBalance`, `currentUser` | `greetingUser()`, `createHistogram()`, `grade()` |
| Categories | `menuFile` | `transferCategory()` |
| Items | `menuFile`, `ordersFile`, `cardBalance` | `checkOverdraft()`, `addItemToOrdersFileKey()` |
| Orders | `ordersFile` | `extractDate()` |
| Recharge | `cardBalance` | `rechargeCard()`, `updateRechargePage()` |
| Management | `menuFile`, `ordersFile` | FileReader API |
| Recommendations | `ordersFile` | `fetch()` to AI API |
