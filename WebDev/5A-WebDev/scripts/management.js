// Select file input element
let input = document.getElementById("fileInput");

// Wait for a file to be uploaded
input.addEventListener("change", function () {

    // Create FileReader object
    let fileReader = new FileReader();

    // Read the uploaded file as text
    fileReader.readAsText(input.files[0]); // read the file as txt

    // Check if the file is a menu file
    if (input.files[0].name.includes("menu")) {

        // When file reading is finished, save menu to localStorage
        fileReader.onload = function () {
            localStorage.setItem("menuFile", fileReader.result);
        };
    }

    // Otherwise, treat file as orders file
    else {

        // If orders already exist in localStorage
        if (localStorage.getItem("ordersFile") !== null) {

            // Get existing orders
            let ordersArr = JSON.parse(localStorage.getItem("ordersFile"));

            // When file reading is finished
            fileReader.onload = function () {

                // Parse orders from uploaded file
                let fromFileArr = JSON.parse(fileReader.result);

                // Add each order from file to existing orders
                for (let i = 0; i < fromFileArr.length; i++) {
                    ordersArr.push(fromFileArr[i]);
                }

                // Sort orders by date (newest first)
                let sortOrderArr = sortItemsByDate(ordersArr);

                // Save updated orders back to localStorage
                localStorage.setItem("ordersFile", JSON.stringify(sortOrderArr));
            }
        }

        // If no orders exist yet
        else {
            fileReader.onload = function () {

                // Save uploaded orders file directly
                localStorage.setItem("ordersFile", fileReader.result);
            };
        }
    }
});

// Sort orders by creation date (descending)
function sortItemsByDate(ordersArr) {

    ordersArr.sort(function (a, b) {
        return b.createdAt - a.createdAt; // newest orders first
    });

    return ordersArr;
}
