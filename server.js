const express = require("express");
const app = express();
app.use(express.static('website'));
const http = require("http");
const server = http.createServer(app);
const io = require("socket.io")(server);
const port = 3000;

let parties = {
    PartyIsFull : {
        players :[],
        size:-1,
        currentPlayer:-1
    }
};

app.get("/",(req,res)=>{
    res.render(index);
})

app.get("/api/info/party",(req,res)=>{
    res.send(parties);
})

io.on("connection",(Socket)=>{
    //create new party or join exsiting party
    Socket.on("Party",(party)=>{
        party = JSON.parse(party);
        addPlayer(party);
        Socket.join(party.party);

        io.to(Socket.id).emit("Messges",`Current Party : ${party.party}`);
        io.to(party.party).emit("Messges",`${party.name} Has Joined`);

        
        Socket.on("Size",(playersSize)=>{
            parties[party.party].size = playersSize;
        })
        
        Socket.on("Setting",()=>{
            io.in(party.party).emit("Setting",parties[party.party].size,parties[party.party].players.length);
        })

        Socket.on("ActivePlayers",(players)=>{
          players =  JSON.parse(players);
          if(players.length != 0)
            {
                parties[party.party].players = players[0];
                parties[party.party].currentPlayer = players[1];

            }
            io.in(party.party).emit("ActivePlayers",parties[party.party].players);
            io.in(party.party).emit("CurrentPlayer",parties[party.party].currentPlayer);
        })

        Socket.on("Messges",(msg)=>{
            io.in(party.party).emit("Messges",msg);
        })
        
        Socket.on("disconnect",()=>{
            for(let i =0 ;i<parties[party.party].players.length;i++)
            {
                if(parties[party.party].players[i].id === party.id)
                {
                    parties[party.party].players.splice(i,1);
                    break;
                }
                Socket.leave(party.party);
                io.in(party.party).emit("Messges",`${party.name} Has Left The Party`);
            }
            if(parties[party.party].players.length == 0 && party.party != "PartyIsFull")
            {
                delete parties[party.party];
            }
        })
    })
})

function addPlayer(party)
{
    if(Object.keys(parties).length === 0)
    {
        creatParty(party,0);
        return;
    }
    let found = false;
    for(let i = 0;i<Object.keys(parties).length;i++){
        if(party.party === Object.keys(parties)[i])
        {
            found = true;
            break;
        }
    }

    if(found)
    {
        if( parties[party.party].players.length < parties[party.party].size)
        {
            parties[party.party].players.push(party);
        }
        else
        {
            party.party = "PartyIsFull";
            parties.PartyIsFull.players.push(party);
        }
    }
    else
    {
        creatParty(party,2);
    }
}

function creatParty(party,size)
{
    parties[party.party] = {
        "size" :size,
        "currentPlayer":0,
        "players" :[party],
    };
}

server.listen(process.env.PORT || port,()=>{
    console.log("Listen at port ",port);
})