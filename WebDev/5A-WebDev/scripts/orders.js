// Select the container where the table will be placed
let containerDiv = document.querySelector(".container");

// Create table elements
let table = document.createElement("table");
let tRow = document.createElement("tr");
let tHeadNam = document.createElement("th");
let tHeadPri = document.createElement("th");
let tHeadPur = document.createElement("th");

// Check if orders exist in localStorage
if (localStorage.getItem("ordersFile") !== null) {

    // Get orders data from localStorage
    let orders = localStorage.getItem("ordersFile");
    orders = JSON.parse(orders);

    // Sort orders by creation date (newest first)
    orders.sort(function (a, b) {
        return b.createdAt - a.createdAt;
    });

    // Build table header
    containerDiv.appendChild(table).appendChild(tRow);
    tRow.appendChild(tHeadNam);
    tRow.appendChild(tHeadPri);
    tRow.appendChild(tHeadPur);

    // Set table header text
    tHeadNam.textContent = "Name";
    tHeadPri.textContent = "Price";
    tHeadPur.textContent = "Purchase Date";

    // Loop through orders and create table rows
    for (let i = 0; i < orders.length; i++) {

        let tRow = document.createElement("tr"); // table row
        let tdName = document.createElement("td"); // product name cell
        let tdPrice = document.createElement("td"); // product price cell
        let tdDate = document.createElement("td"); // purchase date cell

        // Set cell values
        tdName.textContent = orders[i].name;
        tdPrice.textContent = "$" + orders[i].price;
        tdDate.textContent = extractDate(orders[i].createdAt);

        // Append row and cells to the table
        table.appendChild(tRow);
        tRow.appendChild(tdName);
        tRow.appendChild(tdPrice);
        tRow.appendChild(tdDate);
    }

}
// If there are no orders to display
else {

    let noOrders = document.createElement("p");
    document.querySelector("header").appendChild(noOrders);

    // Display message when no orders exist
    noOrders.textContent = "There is no orders to display";
    noOrders.className = "noOrders";
}

// Convert timestamp to readable date format
function extractDate(date) {

    let newDate = new Date(date);
    let day = newDate.getDate();
    let month = newDate.getMonth() + 1; // months start from 0
    let year = newDate.getFullYear();
    let hour = newDate.getHours();
    // let minute = newDate.getMinutes();
    let minute = newDate.getMinutes().toString().padStart(2, '0');

    // Build full date string
    let fullDate = day + '-' + month + '-' + year + ' ' + hour + ':' + minute;
    return fullDate;
}
