// Read URL parameters
let params = new URLSearchParams(window.location.search);

// Get selected category name from URL
let category = params.get("category"); // get the category name from the URL

// Get menu data from localStorage
let menuItem = localStorage.getItem("menuFile");
menuItem = JSON.parse(menuItem); // convert JSON string to object

// Select container for the cards
let container = document.querySelector(".container");

// Create page title dynamically
let pageTitle = document.createElement("h1");
document.body.prepend(pageTitle); // add title to the top of the body
pageTitle.textContent = category;
pageTitle.setAttribute("class", "pageTitle");

// Loop to create item cards dynamically from localStorage
for (let i = 0; i < menuItem[category].length; i++) {

    let div = document.createElement("div"); // card container
    let cardTitle = document.createElement("h1"); // item name
    let priceTxt = document.createElement("span"); // item price
    let btn = document.createElement("button"); // buy button

    // Set item name and price text
    cardTitle.textContent = menuItem[category][i].name;
    priceTxt.textContent = "$" + menuItem[category][i].price;
    btn.textContent = "Buy";

    // Store item data inside the button using dataset
    btn.dataset.name = menuItem[category][i].name;
    btn.dataset.price = menuItem[category][i].price;

    // Build card structure
    container.appendChild(div);
    div.appendChild(cardTitle);
    div.appendChild(priceTxt);
    div.appendChild(btn);

    // Assign CSS classes
    div.className = "itemCard";
    cardTitle.className = "cardTitle";
    priceTxt.className = "cardPrice";
    btn.className = "cardBtn";
}

// Select all buttons on the page
btnList = document.querySelectorAll("button");

// Add click event to every Buy button
for (let i = 0; i < btnList.length; i++) {
    btnList[i].addEventListener("click", function () {

        // Check if user has enough balance
        if (!checkOverdraft(this)) {
            addItemToOrdersFileKey(this); // add item to orders
        }
    });
}

// Show small alert message on screen
function showAlert(message) {
    let alertBox = document.createElement("div");
    alertBox.textContent = message;
    alertBox.className = "miniAlert";

    document.body.appendChild(alertBox);

    // Remove alert after 2 seconds
    setTimeout(function () {
        alertBox.remove();
    }, 2000);
}

// Add purchased item to ordersFile in localStorage
function addItemToOrdersFileKey(clicked) {

    let name = clicked.dataset.name; // item name
    let price = clicked.dataset.price; // item price
    let date = Date.now(); // purchase timestamp
    let ordersArr = [];

    // If orders already exist, add new order
    if (localStorage.getItem("ordersFile") !== null) {
        ordersArr = JSON.parse(localStorage.getItem("ordersFile"));
        ordersArr.push({ "name": name, "price": price, "createdAt": date });
        localStorage.setItem("ordersFile", JSON.stringify(ordersArr));
    }
    // If first order, create array and save
    else {
        ordersArr.push({ "name": name, "price": price, "createdAt": date });
        localStorage.setItem("ordersFile", JSON.stringify(ordersArr));
    }
}

// Check if user has enough balance before purchase
function checkOverdraft(clicked) {

    if (localStorage.getItem("cardBalance") !== null) {

        let cardBal = JSON.parse(localStorage.getItem("cardBalance"));

        // If balance is sufficient
        if (cardBal - clicked.dataset.price >= 0) {
            cardBal = cardBal - clicked.dataset.price; // update balance
            localStorage.setItem("cardBalance", JSON.stringify(cardBal));
            showAlert("Enjoy your order! Purchase successful");
            return false; // no overdraft
        }
        // Not enough money
        else {
            showAlert("There is not enough money in your balance to buy this item!");
            return true; // overdraft
        }
    }
    // No balance found
    else {
        showAlert("Load you balance please!");
        return true;
    }
}
