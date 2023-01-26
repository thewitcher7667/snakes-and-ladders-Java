class Game{

    online ;

    constructor(online){
        this.online = online;
    }

async turns(player)
{
    if(this.checkIfEmpty(player))
    {
      return;
    }
    let rand = await diceRandom();
    player.animation = player.currentPosition;
    player.previousPosition = player.currentPosition;
    player.currentPosition  = player.previousPosition+rand;

    if(this.online)
    {
        socket.emit("Messges",player.name + " : Played " + rand )
        this.updateOnline();
    }
    else
    {
        this.animation(player,true)
        area.innerText += player.name + " : Played " + rand + "\n";
    }
}

async animation(player,first)
{
    if(first)
    {
        player.animation = player.previousPosition;
    }
    if(this.checkIfEmpty(player))
    {
      return;
    }
    autoTurnsDelay = 300 * (player.currentPosition - player.previousPosition) + 2000;
    if(player.animation >= player.currentPosition+1)
    {
      if(snakesAndLadders[player.currentPosition] !== undefined)
      {
          player.currentPosition = snakesAndLadders[player.currentPosition]
          logic.setPosition(player.currentPosition);
          player.position = await logic.getFinalPosition()
          player.previousPosition = player.currentPosition;
          player.animation = player.currentPosition;
      }  
      if(player.winner)
      {
          winnerDiv.style.display = "block";
          winnerH3.innerText = player.name+" is The Winner";
          resetGame()
      }
      repaint()
      return;
    }
    else if(this.checkWinner(player))
    {
      logic.setPosition(player.animation);
      player.position = await logic.getFinalPosition()
      player.animation += 1;
      repaint()
      await sleep(300);
      this.animation(player,false)
      return;
    }


}

updateOnline()
{
    socket.emit("ActivePlayers",JSON.stringify([activePlayers,currenPlayerNumber]));
}

checkIfEmpty(player)
{
    if(activePlayers.length === 0 )
    {
        player.previousPosition  = 0;
        player.currentPosition = 0;
        return true;
    }
    return false
}

checkWinner(player)
{
    if(player.previousPosition == 100) {
        player.winner = true;
        anyWinner = true;
        return true;
    }
    else if(player.currentPosition > 100) {
        player.currentPosition = player.previousPosition
        return false;
    }
   return true;
}

async autoTurns()
{
 		//assign current player
         let currentPlayer = activePlayers[currenPlayerNumber];
         if(currentPlayer == undefined)
         {
            return;
         }
         //set the player label text that has the turn
         turnH2.innerText = currentPlayer.name+" Turn";
         //make the current player active
         currentPlayer.active = true;
         //make the play button disable
         play.disabled = true;
         //if current player is pc and active
         if(currentPlayer.pc && currentPlayer.active && !anyWinner)
         {
            await sleep(autoTurnsDelay)
            //get the dice and turns of the current pc 
            this.turns(currentPlayer);
            //increase the currentPlayerNumber to get it at line 156
            increaseCurrentPlayerNumber();
            //set the current player activity to false
            currentPlayer.active = false;
           //call the method again
           this.autoTurns();

        }
         else if(!currentPlayer.pc && currentPlayer.active && !anyWinner)
         {
             //waiting the user to play make true at line 123 if the user playes
             if(player.id == currentPlayer.id)
             {
                 //if is the user set the button to true
                 play.disabled = false;
                if(played == true)
                 {
                     increaseCurrentPlayerNumber();
                     //set the buuton to disable again after playing
                     play.disabled = true;
                     this. turns(currentPlayer);
                     played = false;
                     currentPlayer.active = false;
                     this.autoTurns();	
                 }
             }
 
         }	 
}
}
