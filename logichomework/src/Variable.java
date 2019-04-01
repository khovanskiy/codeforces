
public class Variable extends Expression
{
	private String label;
	
	public Variable(String label)
	{
		this.label = label;
	}
	
	public String getLabel()
	{
		return label;
	}
	
	@Override
	public String toString()
	{
		return "*"+label+"*";
	}
}
