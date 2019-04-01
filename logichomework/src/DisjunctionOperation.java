
public class DisjunctionOperation extends BinaryOperation
{
	public DisjunctionOperation(Expression left, Expression right)
	{
		super(left, right);
	}
	
	@Override
	public String toString()
	{
		return "["+left.toString()+" | "+right.toString()+"]";
	}
}
