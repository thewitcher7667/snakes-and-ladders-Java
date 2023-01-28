package Main;

public class Logic {

	private int position;
	private int[] finalPosition;
	
	Logic()
	{
		
	}
	
	void setPosition(int position)
	{
		this.position = position;
		setFinalPosition(new int[] {getX(),getY()});
	}
	
	public int getX()
	{
		   int numDevideTen = position/10;
		    if(isInteger((double) position/10))
		    {
		        if(isPrime(numDevideTen))
		        {
		          return 10;
		        }
		        else
		        {
		           return BoardAndXY.boardX-50;
		        }
		    }

		    double num =(double) position/10;
		    double decimal = (double)Math.round(((num - (int)(position/10))*10) * 100000d) / 100000d;
		    int decimalPart = (int) decimal;
		   if(isPrime(numDevideTen))
		   {
		     return boardX(decimalPart);
		   }
		   else
		   {
		     return boardX(11 - decimalPart);
		   }
	}
	
	private int boardX(int number)
	{
		return (number-1)*60 + 10;
	}
	
	public int getY()
	{
		if(isInteger((double) position/10))
		{
			return boardY(position/10);
		}
		
		return boardY(position/10 + 1);
	}
	
	private int boardY(int number)
	{
		return BoardAndXY.boardY - (number * 60);
	}
	
	boolean isPrime(int number)
	{
		if(number %2 == 0)
			return true;
		else
			return false;
	}
	
	boolean isInteger(double number)
	{
		if(number%1.0 == 0.0)
			return true;
		else
			return false;
	
	}

	public int[] getFinalPosition() {
		return finalPosition;
	}

	public void setFinalPosition(int[] finalPosition) {
		this.finalPosition = finalPosition;
	}
}
