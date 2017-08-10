class Test {
    public static void main(String args[]) {
        /*for (int a=1;a<10;a++)
        {
			for (int b=1;b<10;b++)
			{
				for (int c=1;c<10;c++)
				{
					for (int ab=0;ab<=a&&ab<=b;ab++)
					{
						for (int ac=0;ac<=a&&ac<=c;ac++)
						{
							for (int bc=0;bc<=b&&bc<=c;bc++)
							{
								for (int x=Math.max(Math.max(a,b), c);x<64;x++)
								{
									for (int abc=0;abc<=10;abc++)
									{
										if (ab*x!=a*b && ac*x!=a*c && bc*x!=b*c && abc*x*x==a*b*c && a>b && a>c && b>c && c-ac<=x-a && b-ab<=x-a)
										{
											System.out.println("|A|="+a+" |B|="+b+" |C|="+c+" |AB|="+ab+" |AC|="+ac+" |BC|="+bc+" |ABC|="+abc+" |X|="+x);
										}
										else
										{
											//System.out.println("2");
										}
									}
								}
							}
						}
					}
				}
			}
		}*/
		/*for (int a=1;a<10;a++)
		{
			for (int b=1;b<10;b++)
			{
				for (int c=1;c<10;c++)
				{
					for (int ab=0;ab<=a&&ab<=b;ab++)
					{
						for (int ac=0;ac<=a&&ac<=c;ac++)
						{
							for (int bc=0;bc<=b&&bc<=c;bc++)
							{
								for (int x=Math.max(Math.max(a,b), c);x<64;x++)
								{
									for (int abc=0;abc<=Math.min(Math.min(ab,bc), ac);abc++)
									{
										if (ab*x==a*b && ac*x==a*c && bc*x==b*c && abc*x*x!=a*b*c && a>=b && a>=c && b>=c)
										{
											System.out.println("|A|="+a+" |B|="+b+" |C|="+c+" |AB|="+ab+" |AC|="+ac+" |BC|="+bc+" |ABC|="+abc+" |X|="+x);
										}
										else
										{
											//System.out.println("2");
										}
									}
								}
							}
						}
					}
				}
			}
		}*/
        for (int a = 1; a < 10; a++) {
            for (int b = 1; b < 10; b++) {
                for (int c = 1; c < 10; c++) {
                    for (int ab = 0; ab <= a && ab <= b; ab++) {
                        for (int ac = 0; ac <= a && ac <= c; ac++) {
                            for (int bc = 0; bc <= b && bc <= c; bc++) {
                                for (int x = Math.max(Math.max(a, b), c); x < 12; x++) {
                                    for (int abc = 0; abc <= Math.min(Math.min(ab, bc), ac); abc++) {
                                        if (ab * bc != abc * x && ab * x == a * b) {
                                            System.out.println("|A|=" + a + " |B|=" + b + " |C|=" + c + " |AB|=" + ab + " |AC|=" + ac + " |BC|=" + bc + " |ABC|=" + abc + " |X|=" + x);
                                        } else {
                                            //System.out.println("2");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}