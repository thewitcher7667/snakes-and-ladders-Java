class Player{
    id;
	name;
	currentPosition;
	previousPosition;
	color;
	active;
	pc;
	winner;
	party;
	position;
	animation;
	
	constructor(name,isPc)
	{
        this.id  =  0 ;
		this.position =  [-50,-50];
		this.currentPosition = 0;
		this.previousPosition = 0;
		this.name = name;
		this.active = false;
		this.color = "White";
		this.pc = isPc
		this.winner = false;
		this.animation= 0 ;
	}
}