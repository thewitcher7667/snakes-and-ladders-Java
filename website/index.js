let settingButt = document.getElementById("settingButt");
let applySetting = document.getElementById("applySetting");
let closeSetting = document.getElementById("closeSetting");
let errorSetting = document.getElementById("errorSetting");
let settingDiv =  document.getElementById("settingDiv");
let offlineButt = document.getElementById("offlineButt");
let closeOffline = document.getElementById("closeOffline");
let applyOffline = document.getElementById("applyOffline");
let playerNumber = document.getElementById("playerNumber");
let playerColor = document.getElementById("playerColor");
let OfflineDiv = document.getElementById("OfflineDiv");
let canvasesDiv = document.getElementById("canvasesDiv");
let canvas = document.getElementById("canvas");
let dice = document.getElementById("dice");
let circles = document.getElementById("circles");
let circlesCtx = circles.getContext("2d");
let diceCtx = dice.getContext("2d");
let ctx = canvas.getContext("2d");
let play = document.getElementById("play");
let area = document.getElementById("area");
let ofllineGame = document.getElementById("ofllineGame");
let firstPanel = document.getElementById("firstPanel");
let winnerDiv = document.getElementById("winnerDiv");
let winnerH3 = document.getElementById("winnerH3");
let backButtMain = document.getElementById("backButtMain");
let backButt = document.getElementById("backButt");
let PlayAgainButt = document.getElementById("PlayAgainButt");
let onlineButt = document.getElementById("onlineButt");
let online = document.getElementById("online");
let backOnline = document.getElementById("backOnline");
let turnH2 = document.getElementById("turnH2");
let creatPartyButt = document.getElementById("creatPartyButt");
let creatPartyTxt = document.getElementById("creatPartyTxt");
let joinPartyButt = document.getElementById("joinPartyButt");
let joinPartyTxt = document.getElementById("joinPartyTxt");
let playerNumbersDiv= document.getElementById("playerNumbersDiv");

let player = new Player("player",false);
player.id = random(100000,0);
let colors = ["Orange","Red","Blue","Green","Black","Yellow"]
let comps = [new Player("pc1",true),new Player("pc2",true),new Player("pc3",true),new Player("pc4",true),new Player("pc5",true)]
let activePlayers = [];
let logic;
let currenPlayerNumber = 0;
let played = false;
let anyWinner = false;
let autoTurnsDelay = 2000;
let game = new Game(false);
let socket;
let admin = false;
let snakesAndLadders = {
    1:38,
    4:14,
    8:30,
    21:42,
    28:76,
    50:67,
    71:92,
    80:99,
    //snakes
    32:10,
    36:6,
    48:26,
    62:18,
    88:24,
    95:56,
    97:78,
}


onlineButt.addEventListener("click",()=>{
    firstPanel.style.display = "none"
    online.style.display = "block";
    socket = io();
})

backOnline.addEventListener("click",()=>{
    online.style.display = "none";
    firstPanel.style.display = "block"
})

settingButt.addEventListener("click", ()=>{
    settingDiv.style.display = "block";
});

applySetting.addEventListener("click", ()=>{
    let nameSetting = document.getElementById("nameSetting");
    if(nameSetting.value != "" && nameSetting.value.length <20)
    {
        player.name = document.getElementById("nameSetting").value;
        errorSetting.innerText = "Saved"
    }
    else
    {
        errorSetting.innerText = "Please Enter Valid Name";
    }
})

closeSetting.addEventListener("click", ()=>{
    settingDiv.style.display = "none";
});

offlineButt.addEventListener("click",()=>{
    game.online = false;
    OfflineDiv.style.display = "block";
})

closeOffline.addEventListener("click", ()=>{
    OfflineDiv.style.display = "none";
});

backButtMain.addEventListener("click",()=>{
    back();
})

backButt.addEventListener("click",()=>{
    winnerDiv.style.display = "none";
    back();
})

function back()
{
    resetGame();
    firstPanel.style.display = "block";
    ofllineGame.style.display = "none";
    if(game.online)
    {
        socket.disconnect()
    }
    area.innerText = "";
}

PlayAgainButt.addEventListener("click", ()=>{
    winnerDiv.style.display = "none";
    OfflineDiv.style.display = "block";
})

applyOffline.addEventListener("click",async ()=>{
    resetGame();
    player.color = (colors[playerColor.selectedIndex]);
    colors.splice(playerColor.selectedIndex,1);
    if(!game.online)
    {
        activePlayers.push(player);
        for(let i = 0 ;i<=playerNumber.selectedIndex ;i++)
        {
            comps[i].color  = (colors[0]);
            activePlayers.push(comps[i]);
            colors.splice(0,1);
        }
        activePlayers = shuffle(activePlayers);
    }
    OfflineDiv.style.display = "none";
    firstPanel.style.display = "none";
    ofllineGame.style.display = "flex";
    currenPlayerNumber = 0;
    drawDice(0);
    getWindowSize();
    game.autoTurns();
})

play.addEventListener("click",async ()=>{
    played = true;
    game.autoTurns();
})

function drawDice(rand)
{
    dice.width = 100;
    dice.height = 100;
    diceCtx.rect(0,0,dice.width,dice.height)
    diceCtx.fillStyle = "White";
    diceCtx.fill();

    diceCtx.font = `bold 20px Arial`;
    diceCtx.fillStyle = "black";
    diceCtx.fillText(rand, dice.width/2, dice.height/2);
}

async function diceRandom()
{
    let rand = 0;
   for(let i=0;i<4;i++)
   {
    rand = random(0,7);
    drawDice(rand);
    await sleep(500);
   }
   return rand;
}


function getWindowSize()
{
    let w = window.innerWidth;
    let h = 400;
    if(w>616)
    {
        w = 616;
        h= 616;
    }
    else
    {
        w -= 50
    }
    canvasesDiv.style.width = `${w}px`;
    canvasesDiv.style.height = `${h}px`;
    drawBoard(w,h);
}

function drawBoard(w,h)
{ 
    const image = new Image(w,h)
    image.onload = drawImageActualSize;
    image.src = "/game.png";
}

async function drawImageActualSize() 
{
    canvas.width = this.width;
    canvas.height = this.height;
    circles.width = this.width;
    circles.height = this.height;

    ctx.drawImage(this, 0, 0, this.width, this.height);

    logic = new Logic(this.width,this.height)

    if(game.online)
    {
        playerNumbersDiv.style.display = "block";
        online.style.display = "none";
        socket.emit("Party",JSON.stringify(player))
        if(admin)
        {
            socket.emit("Size",playerNumber.selectedIndex+2)
        }
        let playersSize ;
        let partySize;
        socket.emit("Setting","");

        socket.on("Setting",a=>{
            partySize = a;
        })

        socket.emit("ActivePlayers","\"\"");
        socket.on("ActivePlayers",async a=>{
            playersSize = a.length;
            if(partySize === playersSize)
            {
                activePlayers = await a;
            }
            else
            {
                area.innerText += "Waiting players \n"
            }
            for(let i = 0;i<a.length;i++)
            {
                logic.setPosition(a[i].currentPosition)
                a[i].position = await logic.getFinalPosition();
                if(a[i].currentPosition === 100)
                {
                    winnerDiv.style.display = "block";
                    winnerH3.innerText = a[i].name+" is The Winner";
                    resetGame()
                }
            }
            repaint();
        })

        socket.on("CurrentPlayer",a=>{
            currenPlayerNumber = a;
            game.animation(activePlayers[decreaseCurrentNumber()],true)
            game.autoTurns();

        })

        socket.on("Messges",msg=>{
            area.innerText += msg + "\n"; 
        })
    }

    drawPlayers(this.width,this.height);

}

function drawPlayers(width)
{
    for(let i =0;i<activePlayers.length;i++)
    {
        circlesCtx.beginPath();
        //multible by x to get x cord.
        let size = width/30;
        circlesCtx.fillStyle = "black";
        circlesCtx.font = `bold 20px Arial`;
        circlesCtx.fillText(activePlayers[i].name, activePlayers[i].position[0]-20, activePlayers[i].position[1]-20);
        circlesCtx.arc(activePlayers[i].position[0] , activePlayers[i].position[1], size, 0, 2 * Math.PI);
        circlesCtx.fillStyle = activePlayers[i].color;
        circlesCtx.fill();
        circlesCtx.lineWidth = 3;
        circlesCtx.closePath();
    }
}

creatPartyButt.addEventListener("click",()=>{
    let rand = random(100000,0);
    let partyName = creatPartyTxt.value + rand;
    player.party = partyName;
    game.online = true;
    OfflineDiv.style.display = "block";
    admin = true;
})

joinPartyButt.addEventListener("click",()=>{
    let partyName = joinPartyTxt.value ;
    if(partyName === "")
    {
        partyName =  random(100000,0);
    }
    player.party = partyName;
    game.online = true;
    playerNumbersDiv.style.display = "none";
    OfflineDiv.style.display = "block";
    admin = false;
})

//helping functions
function repaint()
{ 
    circlesCtx.clearRect(0, 0, canvas.width, canvas.height);
    drawPlayers(canvas.width)

}

async function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

function random(max,min)
{
    return Math.floor(Math.random() * (max - min + 1) + min)
}

function shuffle(unshuffled){
    return unshuffled
    .map(value => ({ value, sort: Math.random() }))
    .sort((a, b) => a.sort - b.sort)
    .map(({ value }) => value)
}

function increaseCurrentPlayerNumber()
{
    if(currenPlayerNumber == activePlayers.length-1)
    {
        currenPlayerNumber = 0;
    }
    else
    {
        currenPlayerNumber++;
    }
}

function decreaseCurrentNumber()
{
    if(currenPlayerNumber == 0 )
    {
        return activePlayers.length-1;
    }
    else
    {
        return currenPlayerNumber - 1;
    }
}

function resetGame()
{
    for(let i = 0;i<activePlayers.length;i++)
    {
        let current = activePlayers[i];
        current.currentPosition = 0
        current.previousPosition = 0;
        current.position = [-50,-50];
        current.active = false;
        current.isWinner = false;
    }
    colors = ["Orange","Red","Blue","Green","Black","Yellow"]
   activePlayers = [];
   currenPlayerNumber = 0;
   played = false;
   anyWinner = false;
   repaint()

}
