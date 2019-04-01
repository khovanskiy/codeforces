
public class ConjunctionOperation extends BinaryOperation
{
	public ConjunctionOperation(Expression left, Expression right)
	{
		super(left, right);
	}
	
	@Override
	public String toString()
	{
		return "("+left.toString()+" & "+right.toString()+")";
	}
}
