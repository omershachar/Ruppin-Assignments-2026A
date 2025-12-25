// constants game variables
const INITIAL_BALANCE = 1000;
const LOTTO_COST = 300;
const PRIZE_ONE = 1000; // 6/6 and 1/1
const PRIZE_TWO = 600; // 6/6 and 0/1
const PRIZE_THREE = 400; // 4/6 and 1/1

// variables for storing the current game numbers
let selectedR = [];
let selectedS = null;

function createButtons() {
    const regularContainer = document.getElementById('grid-regular');
    const strongContainer = document.getElementById('grid-strong');

    // create regular buttons
    for (let i=1; i<=37; i++) {
        const btn = document.createElement('button');
        btn.innerText = i;
        btn.className = 'num-btn';
        btn.id = 'regular';
        btn.onclick = () => toggleButton(btn, btn.id, i);
        regularContainer.appendChild(btn);
    }

    // create strong buttons
    for (let i=1; i<=7; i++) {
        const btn = document.createElement('button');
        btn.innerText = i;
        btn.className = 'num-btn';
        btn.id = 'strong';
        btn.onclick = () => toggleButton(btn, btn.id, i);
        strongContainer.appendChild(btn);
    }
}

// function for handling the button clicks
function toggleButton(btn, btnId, num) {
    if (btnId === 'strong') {
        const allStrongButtons = document.getElementById('grid-strong').children;
        // unselect all strong buttons
        for (let strongBtn of allStrongButtons) {
            strongBtn.classList.remove('strong-selected');
        }
        selectedS = num;
        btn.classList.add('strong-selected');
    }

    else if (btnId === 'regular' && selectedR.includes(num)) {
        selectedR = selectedR.filter(n => n !== num);
        btn.classList.remove('selected');
    }

    else {
        if (selectedR.length < 6) {
            selectedR.push(num);
            btn.classList.add('selected');
        }
        else {alert("ניתן לבחור מקסימום 6 מספרים");}
    }
    updateSelectionCount();
}

// function activates when the check button is clicked
function lottoCheck() {
    if (selectedR.length !== 6 || selectedS === null) {
        alert("נא למלא את כל המספרים");
        return;
    }

    const resultR = generateLottoResult(6,1,37);
    const resultS = generateLottoResult(1,1,7)[0];
    
    let hitsS = (selectedS === resultS)? 1 : 0;
    let hitsR = 0;
    selectedR.forEach(num => {
        if (resultR.includes(num)) {hitsR++;}
    });
    
    let prize = checkForPrizes(hitsR, hitsS);
    addResultToLog(resultR, resultS, hitsR, hitsS, prize);

    resetLottoGame();
    updateBalance(prize - LOTTO_COST);
}

function addResultToLog(resultR, resultS, hitsR, hitsS, prize) {
    const resultLog = document.getElementById('results-log');
    if (resultLog.querySelector('p') != null) {
        resultLog.innerHTML = '';
    }
    const div = document.createElement('div');
    div.className = 'result-item';
    div.innerHTML = `
        <div><strong>הגרלה:</strong> ${resultR.join(', ')} (חזק: ${resultS})</div>
        <div><strong>הבחירה שלך:</strong> ${selectedR.join(', ')} (חזק: ${selectedS})</div>
        <div>פגיעות: ${hitsR} | חזק: ${hitsS ? 'V' : 'X'}</div>
        <div style="color: ${prize > 0 ? 'green' : 'red'}; font-weight: bold;">
            ${prize > 0 ? 'זכית ב-' + prize + ' ₪' : 'לא זכית'}
        </div>
    `;
    resultLog.prepend(div);
}

// helper functions:

function getBalance() {
    return parseInt(document.getElementById('balance').innerText);
}

function updateBalance(num) {
    let balanceNew = getBalance() + num;
    document.getElementById('balance').innerText = balanceNew;
    if (balanceNew < 300) {
        alert("יתרה פחותה מהדרישה");
        endLottoGame();
    }    
}    

function updateSelectionCount() {
    // updating the regular buttons selection count
    const regularCount = document.getElementById('regular-count');
    regularCount.innerText = `${selectedR.length}/6`;
}    

function generateLottoResult(len, min, max) {
    const winningNums = [];
    while (winningNums.length < len) {
        let r = Math.floor(Math.random() * max) + min;
        if (!winningNums.includes(r)) {winningNums.push(r)}
    }
    return winningNums;
}

function checkForPrizes(hitsR, hitsS) {
    let prize = 0;
    if (hitsR === 6 ) {
        prize = (hitsS === 1)? PRIZE_ONE : PRIZE_TWO;
    }
    else if (hitsR === 4 && hitsS === 1) {prize = PRIZE_THREE;}
    return prize;
}

function resetButtons() {
    selectedR = [];
            selectedS = null;
            document.querySelectorAll('.num-btn').forEach(btn => {
                btn.classList.remove('selected');
                btn.classList.remove('strong-selected');
            });
    document.querySelectorAll('button').forEach(b => b.disabled = false);
    updateSelectionCount();
}

function resetLottoGame() {
    selectedR = [];
    selectedS = null;
    resetButtons();
}

function resetResultLog() {
    document.getElementById('results-log').innerHTML = '<p>עוד לא בוצעו הגרלות</p>';
}

function endLottoGame() {
    resetButtons();
    document.querySelectorAll('button').forEach(b => b.disabled = true);
    alert(`המשחק נגמר. סכום סופי: ${getBalance()} ₪`);
}

function newGame() {
    // resetting balance to 1000
    let balanceVar = INITIAL_BALANCE - (getBalance());
    updateBalance(balanceVar);
    createButtons();
    resetResultLog();
    resetLottoGame();
}

newGame();