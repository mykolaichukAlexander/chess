function resetData() {
    document.getElementById("inputForm").reset();
    location.reload();
    window.location.href = "/index";
}

if (window.history.replaceState) {
    window.history.replaceState(null, null, window.location.href);
}

function drawMoves(indicesStr) {

    const mainBoard = document.getElementById("mainChessBoard");
    mainBoard.innerHTML = '';

    let indices = JSON.parse(indicesStr);
    for (var i = 0; i < 64; i++) {
        let index = indices.findIndex(element => element === i);
        if (index >= 0) {
            const newDiv = document.createElement("div");
            const strIndex = document.createTextNode("" + index);
            newDiv.appendChild(strIndex);
            mainBoard
                .appendChild(newDiv)
                .style.backgroundColor = index === indices.length - 1 || index === 0 ? 'red' : 'green';
        } else {
            let simpleDiv = document.createElement("div");
            mainBoard
                .appendChild(simpleDiv)
                .style.backgroundColor = parseInt((i / 8) + i) % 2 === 0 ? '#ababab' : 'white';
        }
    }
}