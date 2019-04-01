package ru.ifmo.ctddev.khovanskiy.task1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;

public class Grep {

	static int getFilesList(Matcher matcher, Path root) {
		Queue<Path> paths = new LinkedList<Path>();
		int count = 0;
		paths.add(root);
		while (paths.size() > 0) {
			Path current = paths.poll();
			try (DirectoryStream<Path> stream = Files
					.newDirectoryStream(current)) {
				for (Path entry : stream) {
					if (Files.isDirectory(entry)) {
						paths.add(entry);
					} else {
						count += execute(matcher, root, entry);
					}
				}
			} catch (IOException ex) {
			}
		}
		return count;
	}

	private static String buildString(LinkedList<Byte> cache)
			throws IOException {
		int n = cache.size();
		byte[] buffer = new byte[n];
		for (int i = 0; i < n; ++i) {
			buffer[i] = cache.poll();
		}
		return new String(buffer, "UTF-8");
	}

	public static boolean isSplitByte(byte b) {
		return b == 10 || b == 13;
	}

	public static boolean isAvailableByte(byte b) {
		return b != 9 && !isSplitByte(b);
	}

	public static int execute(Matcher matcher, Path root, Path temp) {
		Path currentDirectory = root.toAbsolutePath();
		int id = 0;
		try (InputStream is = Files.newInputStream(temp)) {
			byte[] buffer = new byte[BUFFER_SIZE];
			LinkedList<Byte> cache = new LinkedList<Byte>();
			int count = 0;
			int found = 0;
			boolean overflow = false;
			boolean finished = false;
			int lineNumber = 0;
			while ((count = is.read(buffer)) != -1 && !finished) {
				for (int i = 0; i < count; ++i) {
					if (found == 0) {
						if (buffer[i] == 13) {
							++lineNumber;
						}
						if (isSplitByte(buffer[i])) {
							cache.clear();
						} else if (isAvailableByte(buffer[i])) {
							if (cache.size() >= ADDITIONAL_SIZE) {
								cache.poll();
							}
							cache.add(buffer[i]);
						}
						found = matcher.next(buffer[i]);
					} else {
						if (buffer[i] == 13) {
							++lineNumber;
						}
						if (overflow || isSplitByte(buffer[i])) {
							Console.print(currentDirectory.relativize(temp
									.toAbsolutePath())
									/*+ " -"
									+ (lineNumber)*/
									+ "-: " + buildString(cache));
							++id;
							found = 0;
							cache.clear();
							// finished = true;
							// break;
						} else if (isAvailableByte(buffer[i])) {
							cache.add(buffer[i]);
							if (cache.size() >= ADDITIONAL_SIZE * 2 + found) {
								overflow = true;
							}
						}
					}
				}
			}
		} catch (IOException e) {
		}
		return id;
	}

	private final static int ADDITIONAL_SIZE = 1000;
	private final static int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("At least 1 argument required (" + args.length
					+ " given)");
		} else {
			// Console.print(Charset.availableCharsets());
			Matcher matcher = new Matcher();

			if (args[0].equals("-")) {
				try (BufferedReader in = new BufferedReader(
						new InputStreamReader(System.in,
								Charset.forName("UTF-8")))) {
					String ss = "";
					while ((ss = in.readLine()).length() > 0) {
						matcher.addString(ss);
					}
				} catch (Exception e) {
				}
			} else {
				for (int i = 0; i < args.length; ++i) {
					matcher.addString(args[i]);
				}
			}

			Path currentDirectory = Paths.get("");
			int count = getFilesList(matcher, currentDirectory);

			Console.print("\n" + count + " matches found");
		}
	}
}
