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

let player = new Player("player",false);
player.setId(random(100000,0));
let colors = ["Orange","Red","Blue","Green","Black","Yellow"]
let comps = [new Player("pc1",true),new Player("pc2",true),new Player("pc3",true),new Player("pc4",true),new Player("pc5",true)]
let activePlayers = [];
let logic;
let currenPlayerNumber;
let played = false;
let anyWinner = false;
let autoTurnsDelay = 2000;
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
})

backOnline.addEventListener("click",()=>{
    online.style.display = "none";
    firstPanel.style.display = "block"
})

settingButt.addEventListener("click", ()=>{
    settingDiv.style.display = "block";
});

applySetting.addEventListener("click", ()=>{
    if(document.getElementById("nameSetting").value != "" && document.getElementById("nameSetting").value.length <20)
    {
        player.setName(document.getElementById("nameSetting").value);
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
}

PlayAgainButt.addEventListener("click", ()=>{
    winnerDiv.style.display = "none";
    OfflineDiv.style.display = "block";
})

applyOffline.addEventListener("click",()=>{
    resetGame();
    activePlayers.push(player);
    player.setColor(colors[playerColor.selectedIndex]);
    colors.splice(playerColor.selectedIndex,1);

    for(let i = 0 ;i<=playerNumber.selectedIndex;i++)
    {
        comps[i].setColor(colors[0]);
        activePlayers.push(comps[i]);
        colors.splice(0,1);
    }
    activePlayers = shuffle(activePlayers);
    OfflineDiv.style.display = "none";
    firstPanel.style.display = "none";
    ofllineGame.style.display = "flex";
    currenPlayerNumber = 0;
    drawDice(0);
    getWindowSize();
    autoTurns();
})

play.addEventListener("click",async ()=>{
    played = true;
    autoTurns();
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
        circlesCtx.fillText(activePlayers[i].getName(), activePlayers[i].getPosition()[0]-20, activePlayers[i].getPosition()[1]-20);
        circlesCtx.arc(activePlayers[i].getPosition()[0] , activePlayers[i].getPosition()[1], size, 0, 2 * Math.PI);
        circlesCtx.fillStyle = activePlayers[i].getColor();
        circlesCtx.fill();
        circlesCtx.lineWidth = 3;
        circlesCtx.closePath();
    }
}

async function turns(player)
{
    if(checkIfEmpty(player))
    {
      return;
    }
    let rand = await diceRandom();
    player.setPreviousPosition(player.getCurrentPosition());
    player.setCurrentPosition(player.getCurrentPosition()+rand);

    area.innerText += player.getName() + " : Played " + rand + "\n";
    animation(player)
}

async function animation(player)
{
    if(checkIfEmpty(player))
    {
      return;
    }
    autoTurnsDelay = 300 * (player.getCurrentPosition() - player.getPreviousPosition()) + 2000;
    if(player.getPreviousPosition() >= player.getCurrentPosition()+1)
    {
      if(snakesAndLadders[player.getCurrentPosition()] !== undefined)
      {
          player.setCurrentPosition(snakesAndLadders[player.getCurrentPosition()])
          logic.setPosition(player.getCurrentPosition());
          player.setPosition(await logic.getFinalPosition())
          player.setPreviousPosition(player.getCurrentPosition());
      }
      if(player.isWinner)
      {
          winnerDiv.style.display = "block";
          winnerH3.innerText = player.getName()+" is The Winner";
          resetGame()
      }
      repaint()
      return;
    }
    else if(checkWinner(player))
    {
      logic.setPosition(player.getPreviousPosition());
      player.setPosition(await logic.getFinalPosition())
      player.setPreviousPosition(player.getPreviousPosition()+1);
      repaint()
      await sleep(300);
      animation(player)
    }
}

function checkIfEmpty(player)
{
    if(activePlayers.length === 0 )
    {
        player.setPreviousPosition(0);
        player.setCurrentPosition(0);
        return true;
    }
    return false
}

function checkWinner(player)
{
    if(player.getCurrentPosition() == 100) {
        player.setWinner(true);
        anyWinner = true;
        return true;
    }
    else if(player.getCurrentPosition() > 100) {
        player.setCurrentPosition(player.getPreviousPosition());
        return false;
    }
   return true;
}

async function autoTurns()
{
 		//assign current player
         let currentPlayer = activePlayers[currenPlayerNumber];
         if(currentPlayer == undefined)
         {
            return;
         }
         //set the player label text that has the turn
         turnH2.innerText = currentPlayer.getName()+" Turn";
         //make the current player active
         currentPlayer.setActive(true);
         //make the play button disable
         play.disabled = true;
         //if current player is pc and active
         if(currentPlayer.isPc && currentPlayer.isActive() && !anyWinner)
         {
            await sleep(autoTurnsDelay)
            //get the dice and turns of the current pc 
            turns(currentPlayer);
            //increase the currentPlayerNumber to get it at line 156
            increaseCurrentPlayerNumber();
            //set the current player activity to false
            currentPlayer.setActive(false);
           //call the method again
            autoTurns();

        }
         else if(!currentPlayer.isPc && currentPlayer.isActive() && !anyWinner)
         {
             //waiting the user to play make true at line 123 if the user playes
             if(player.getId() == currentPlayer.getId())
             {
                 //if is the user set the button to true
                 play.disabled = false;
                if(played == true)
                 {
                     increaseCurrentPlayerNumber();
                     //set the buuton to disable again after playing
                     play.disabled = true;
                     turns(currentPlayer);
                     played = false;
                     currentPlayer.setActive(false);
                     autoTurns();	
                 }
             }
 
         }	 
}

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

function resetGame()
{
    for(let i = 0;i<activePlayers.length;i++)
    {
        let current = activePlayers[i];
        current.setCurrentPosition(0)
        current.setPreviousPosition(0);
        current.setPosition([-50,-50]);
        current.setActive(false);
        current.setWinner(false);
    }
    colors = ["Orange","Red","Blue","Green","Black","Yellow"]
   activePlayers = [];
   currenPlayerNumber = 0;
   played = false;
   anyWinner = false;
   repaint()

}
