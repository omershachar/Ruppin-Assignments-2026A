let money = 1000;
let selectedRegular = [];
let selectedStrong = null;
let isGameActive = true;

const balanceSpan = document.getElementById('balance');
const resultLog = document.getElementById('result-log');

function createButtons() {
    const regularContainer = document.getElementById('grid-regular');
    const strongContainer = document.getElementById('grid-strong')

    for (let i=1; i<=37; i++) {
        const btn = document.createElement('button');
        btn.innerText = i;
        btn.className = 'num-btn';
        btn.id = 'regular';
        btn.onclick = () => toggleButton(btn, btn.id, i);
        regularContainer.appendChild(btn);
    }

    for (let i=1; i<=7; i++) {
        const btn = document.createElement('button');
        btn.innerText = i;
        btn.className = 'num-btn';
        btn.id = 'strong';
        btn.onclick = () => toggleButton(btn, btn.id, i);
        strongContainer.appendChild(btn);
    }
}

function toggleButton(btn, btnId, num) {
    if (!isGameActive) {return;}

    if (btnId === 'strong') {
        const allStrongButtons = document.getElementById('grid-strong').children;
        for (let strongBtn of allStrongButtons) {
            strongBtn.classList.remove('strong-selected');
        }
        selectedStrong = num;
        btn.classList.add('strong-selected');
    }

    else if (btnId === 'regular' && selectedRegular.includes(num)) {
        selectedRegular = selectedRegular.filter(n => n !== num);
        btn.classList.remove('selected');
    }

    else {
        if (selectedRegular.length < 6) {
            selectedRegular.push(num);
            btn.classList.add('selected');
        }
        else {alert("ניתן לבחור מקסימום 6 מספרים");}
    }
    updateSelectionCount();
}

function updateSelectionCount() {
    const regularCount = document.getElementById('regular-count');
    regularCount.innerText = `${selectedRegular.length}/6`;
}

function checkGame() {}

function addResult(result) {}

function resetGame() {}

function endGame() {}


createButtons();