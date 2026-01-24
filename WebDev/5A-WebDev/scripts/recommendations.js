if (localStorage.getItem('ordersFile') !== null) {
    let transactions=JSON.parse(localStorage.getItem('ordersFile'));
    let url ="https://yael-ex-client-side-2026a-197579115104.me-west1.run.app/get-recommendations?api-key=afGre4Eerf223432AXE&lang=he"
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({transactions})
    })
        .then(res => res.json())
        .then(html => {
            document.getElementById("recommendations").innerHTML = html.recommendations;
        })
        .catch(err => console.error(err));
}
else{
    let div= document.getElementById("recommendations")
    let h2 = document.createElement("h2")
    div.appendChild(h2)
    h2.textContent="First order some items"
}
