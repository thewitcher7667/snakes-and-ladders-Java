class Player{
    id;
	name;
	position;
	currentPosition;
	previousPosition;
	color;
	active;
	isPc;
	isWinner;
	party;
	
	constructor(name,isPc)
	{
        this.setId(0);
		this.setPosition( [-50,-50]);
		this.setCurrentPosition(0);
		this.setPreviousPosition(0);
		this.setName(name);
		this.setActive(false);
		this.setColor("White");
		this.setPc(isPc);
		this.setWinner(false);
	}
	
	getName() {
		return this.name;
	}

	setName(name) {
		this.name = name;
	}

	getColor() {
		return this.color;
	}

	setColor( color) {
		this.color = color;
	}

	getPosition() {
		return this.position;
	}

	setPosition( position) {
		this.position = position;
	}

	getCurrentPosition() {
		return this.currentPosition;
	}

	setCurrentPosition( currentPosition) {
		this.currentPosition = currentPosition;
	}

	getPreviousPosition() {
		return this.previousPosition;
	}

	setPreviousPosition( previousPosition) {
		this.previousPosition = previousPosition;
	}

	getId() {
		return this.id;
	}

	setId( id) {
		this.id = id;
	}

	isActive() {
		return this.active;
	}

	setActive( active) {
		this.active = active;
	}

	isPc() {
		return this.isPc;
	}

	setPc( isPc) {
		this.isPc = isPc;
	}

    isWinner() {
		return this.isWinner;
	}

	setWinner( isWinner) {
		this.isWinner = isWinner;
	}

    getParty() {
		return this.party;
	}

	setParty(party) {
		this.party = party;
	}


}