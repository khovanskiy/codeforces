
public class NegationOperation extends Operation
{
	protected Expression left;
	
	public NegationOperation(Expression left)
	{
		this.left = left;
	}
	
	@Override
	public String toString()
	{
		return "!"+left.toString();
	}
}
