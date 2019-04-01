
public abstract class BinaryOperation extends Operation
{
	public Expression left;
	public Expression right;

	public BinaryOperation(Expression left, Expression right)
	{
		this.left = left;
		this.right = right;
	}
}
