// Show greeting message and date based on URL parameter
greetingUser();

// Select all slide elements inside #box
let boxDivs = document.querySelectorAll("#box .slide");

// Create h2 for daily message slide
let h2DailyMess = document.createElement("h2");
boxDivs[0].appendChild(h2DailyMess);
boxDivs[0].classList.add("h2Features"); // styling class

// Create h2 for monthly orders slide
let h2DisplayMorder = document.createElement("h2");
boxDivs[1].appendChild(h2DisplayMorder);
boxDivs[1].classList.add("h2Features");

// Create h2 for membership grade slide
let h2Grade = document.createElement("h2");
boxDivs[2].appendChild(h2Grade);
boxDivs[2].classList.add("h2Features");

// Display card balance from localStorage
getBalance();

// Create histogram chart after DOM loads
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', createHistogram);
} else {
    createHistogram();
}

// Initial content fill before slider starts
dailyMessage();
DisplayMonthOrders();
grade();

// Automatically switch slides every 4 seconds
setInterval(nextSlide, 4000);

// Keeps track of current slide index
let count = 0;

function nextSlide() {

    // Remove active class from current slide
    boxDivs[count].classList.remove("active");

    // Move to next slide
    count++;

    // If reached the end, go back to first slide
    if (count === boxDivs.length) {
        count = 0;
    }

    // Activate the new slide
    boxDivs[count].classList.add("active");

    // Update content based on active slide
    switch (count) {
        case 0:
            dailyMessage(); // update greeting message
            break;
        case 1:
            DisplayMonthOrders(); // update monthly orders message
            break;
        case 2:
            grade(); // update membership grade
            break;
    }
}

// Returns orders array from localStorage or null if not exists
function storageNull() {
    if (localStorage.getItem("ordersFile") !== null) {
        let orderArr = JSON.parse(localStorage.getItem("ordersFile"));
        return orderArr;
    }
    return null;
}

// Display greeting message based on current hour
function dailyMessage() {
    let nowDate = new Date();
    let hours = nowDate.getHours();

    if (hours < 12) {
        h2DailyMess.textContent = "Good Morning Welcome to Our Coffee Shop!";
    }
    else if (hours >= 12 && hours <= 18) {
        h2DailyMess.textContent = "Good Afternoon Welcome to Our Coffee Shop!";
    }
    else {
        h2DailyMess.textContent = "Good Evening Welcome to Our Coffee Shop!";
    }
}

// Display number of orders made this month
function DisplayMonthOrders() {
    h2DisplayMorder.textContent =
        "Keep it up! You’ve made " + countOrderMonth() + " orders this month ☕🥐";
}

// Display membership grade based on number of monthly orders
function grade() {

    if (countOrderMonth() > 5 && countOrderMonth() <= 10) {
        h2Grade.textContent = "Great job! With 5+ orders, you’re now a Silver member 🥈";
    }
    else if (countOrderMonth() > 10) {
        h2Grade.textContent = "Amazing! With over 10 orders, you’ve reached Gold status 🥇";
    }
    else {
        h2Grade.textContent = "Keep ordering to unlock higher membership levels! 🎉";
    }
}

// Count how many orders were made in the current month
function countOrderMonth() {
    let countOrders = 0;
    let orderArr = storageNull();

    if (orderArr !== null) {
        let currentDate = new Date();

        for (let i = 0; i < orderArr.length; i++) {
            // Compare order month with current month
            if (new Date(orderArr[i].createdAt).getMonth() === currentDate.getMonth()) {
                countOrders++;
            }
        }
    }

    return countOrders;
}

// Display card balance from localStorage
function getBalance() {
    if (localStorage.getItem("cardBalance") !== null) {
        let cardBalance = JSON.parse(localStorage.getItem("cardBalance"));
        document.getElementById("balance-display").textContent = cardBalance;
    }
    else {
        document.getElementById("balance-display").textContent = '0';
    }
}

// Get username from URL and display greeting and date
function greetingUser() {
    let params = new URLSearchParams(window.location.search);
    let userName = params.get("user"); // get user name from URL

    if (userName !== null) {
        localStorage.setItem("currentUser", userName);
    }
    // Otherwise, get user from localStorage
else {
        userName = localStorage.getItem("currentUser");
    }

    document.getElementById("greeting-user").textContent = "Hello " + userName;

    let nowDate = new Date();
    document.getElementById("datetime").textContent =
        nowDate.toLocaleDateString("he-IL"); // display date in Israeli format
}

// Create histogram chart showing products bought since today
function createHistogram() {
    let orders = storageNull();
    if (!orders) return;
    
    let today = new Date();
    today.setHours(0, 0, 0, 0);
    
    let todayOrders = orders.filter(o => new Date(o.createdAt) >= today);
    let counts = {};
    todayOrders.forEach(o => counts[o.name] = (counts[o.name] || 0) + 1);
    
    if (Object.keys(counts).length === 0) return;
    
    const chartCanvas = document.getElementById('histogram-chart');
    const container = document.getElementById('histogram-container');
    
    new Chart(chartCanvas, {
        type: 'bar',
        data: {
            labels: Object.keys(counts),
            datasets: [{ 
                label: 'Quantity', 
                data: Object.values(counts),
                backgroundColor: 'rgba(75, 192, 192, 0.6)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            layout: {
                padding: {
                    top: 10,
                    bottom: 10,
                    left: 10,
                    right: 10
                }
            },
            plugins: {
                legend: { 
                    labels: { color: '#000' },
                    display: true
                }
            },
            scales: {
                x: { 
                    ticks: { color: '#000', maxRotation: 45, minRotation: 0 },
                    grid: { color: 'rgba(0,0,0,0.1)' }
                },
                y: { 
                    beginAtZero: true, 
                    ticks: { color: '#000', stepSize: 1 }, 
                    grid: { color: 'rgba(0,0,0,0.1)' } 
                }
            }
        }
    });
}
