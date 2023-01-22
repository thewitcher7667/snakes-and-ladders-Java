class Logic{

    boardWidth;
    boardHeight;
    currentPostion;
    x;
    y;
    finalPosition;


    constructor(width,height)
    {
        this.boardWidth = width;
        this.boardHeight = height;

    }

    setPosition(currentPostion)
    {
        this.currentPostion = currentPostion;

    }

    async getFinalPosition()
    {
        let x = await this.getX();
        let y = await this.getY();
        return [x,y]

    }

    async getX()
    {
        let numDevideTen =Math.floor(this.currentPostion/10);
        if(this.isInteger(this.currentPostion/10))
        {
            if(this.isPrime(numDevideTen))
            {
              return this.boardX(1);
            }
            else
            {
               return this.boardX(10);
            }
        }

        let num =this.currentPostion/10;
        let decimal = Math.round(((num -Math.floor(this.currentPostion/10))*10) * 10) / 10;
        let decimalPart = decimal;
       if(this.isPrime(numDevideTen))
       {
         return this.boardX(decimalPart);
       }
       else
       {
         return this.boardX(11 - decimalPart);
       }
    }

    boardX(number)
	{
        
        return (this.boardWidth/10)*number - (this.boardWidth/20) 
	}

    async getY()
    {
        if(this.isInteger(this.currentPostion/10))
		{
			return this.boardY(this.currentPostion/10);
		}
		
		return this.boardY(Math.floor(this.currentPostion/10 + 1));
    }
	
	boardY(number)
	{
		return this.boardHeight - ((Math.floor(this.boardHeight/10))*number - (Math.floor(this.boardWidth/30)));
	}
	
	isPrime(number)
	{
		if(number %2 == 0)
			return true;
		else
			return false;
	}
	
	isInteger(number)
	{
		if(number%1.0 === 0.0)
			return true;
		else
			return false;
	
	}
}