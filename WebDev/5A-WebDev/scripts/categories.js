


//check if file uploaded and transfer the category name to the next page
function transferCategory(clicked) {
    if(localStorage.getItem("menuFile")!==null) {
        let category= clicked.dataset.category // get the data section from the div who got clicked
        window.location.href = `items.html?category=${category}`;// transfer the category name to item page
    }
    else {
        showAlert()

    }

}

//loop over all the category divs and add the onclick event to them
let categoryDivs=document.querySelectorAll(".grid-container > div");
for (let i = 0; i < categoryDivs.length; i++){
    categoryDivs[i].addEventListener("click", function(){
        transferCategory(this)
    });

}

//showing message on the screen
function showAlert() {
    let alertBox = document.createElement("div");
    alertBox.textContent = "Unable to load menu items! upload the menu file please";
    alertBox.className = "miniAlert";

    document.body.appendChild(alertBox);

    setTimeout(function () {alertBox.remove()}, 3500);
}
