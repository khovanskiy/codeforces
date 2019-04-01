
public enum OperationType
{
	IMPLICATION
	{
		@Override
		public String getDelimeter()
		{
			return "->";
		}
		
		@Override
		public boolean isRightAssociative()
		{
			return true;
		}
	},
	DISJUNCTION
	{
		@Override
		public String getDelimeter()
		{
			return "|";
		}
		
		@Override
		public boolean isRightAssociative()
		{
			return false;
		}
	},
	CONJUNCTION
	{
		@Override
		public String getDelimeter()
		{
			return "&";
		}
		
		@Override
		public boolean isRightAssociative()
		{
			return false;
		}
	},
	NEGATION
	{
		@Override
		public String getDelimeter()
		{
			return "!";
		}
		
		@Override
		public boolean isRightAssociative()
		{
			return false;
		}
	},
	EMPTY
	{
		@Override
		public String getDelimeter()
		{
			return "";
		}

		@Override
		public boolean isRightAssociative()
		{
			return false;
		}
	};
	public abstract String getDelimeter();
	public abstract boolean isRightAssociative();
}
