import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.*;

public class Encode {
	public static boolean originalVisible;
	public static boolean frequencyVisible;
	public static boolean prefixVisible;
	public static boolean terminate;

	public static void main(String[] args) {
		terminate = false;

		while (!terminate) {

			Scanner scan = new Scanner(System.in);

			System.out.println("Show Prefixes? y/n ");

			prefixVisible = (scan.next().equals("y")) ? true : false;
			
			System.out.println("Show Frequencies? y/n");
			
			frequencyVisible = (scan.next().equals("y")) ? true : false;
			
			System.out.println("Show original? y/n");
			
			originalVisible = frequencyVisible = (scan.next().equals("y")) ? true : false;
			
			System.out.println("File name? ");

			String filename = scan.next();

			HashMap<Character, Integer> map = new HashMap<Character, Integer>();

			try {

				countFrequency(filename, map);

				PriorityQueue<HuffNode> pri = new PriorityQueue<HuffNode>();

				if (frequencyVisible) map.forEach((k,v) -> System.out.println("character: "+k+" frequency"+v));
				map.forEach((c, f) -> pri.add(new HuffNode(c.charValue(), f.intValue())));

				while (pri.size() != 1) {
					HuffNode h = new HuffNode();

					HuffNode l = pri.peek();
					pri.poll();

					HuffNode r = pri.peek();
					pri.poll();

					h.setFreq(l.getFreq() + r.getFreq());
					h.setLeft(l);
					h.setRight(r);

					pri.add(h);
				}

				HuffNode root = pri.peek();

				HashMap<Character, String> encoder = new HashMap<Character, String>();

				printCode(root, "", encoder);

				encode(encoder, filename);

				System.out.println("\nEnd?");

				terminate = (scan.next().equals("y")) ? true : false;

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void countFrequency(String filename, HashMap<Character, Integer> map) throws IOException {

		try {

			Scanner scan = new Scanner(new FileInputStream(filename));

			Charset encoding = Charset.defaultCharset();

			InputStream in = new FileInputStream(filename);

			Reader reader = new InputStreamReader(in, encoding);

			int r;

			while ((r = reader.read()) != -1) {

				Character c = (char) r;
				System.out.print(c);
				if (c >= 0x20 && c <= 0x7e) {

					if (!map.containsKey(c))
						map.put(c, 1);

					else
						map.put(c, map.get(c) + 1);

				}
			}
			// map.forEach((k,v) -> System.out.println("key: "+k+" value:"+v));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void printCode(HuffNode root, String s, HashMap<Character, String> encoder) {
		if (root == null)
			System.out.println("null");

		else if (root.getLeft() == null && root.getRight() == null) {

			if (prefixVisible)
				System.out.println(root.getC() + ":" + s);
			encoder.put(root.getC(), s);
			return;
		}

		printCode(root.getLeft(), s + "0", encoder);
		printCode(root.getRight(), s + "1", encoder);
	}

	public static void encode(HashMap<Character, String> encoder, String filename) throws IOException {
		Scanner scan = new Scanner(new FileInputStream(filename));

		Charset encoding = Charset.defaultCharset();

		InputStream in = new FileInputStream(filename);

		Reader reader = new InputStreamReader(in, encoding);

		int r;

		while ((r = reader.read()) != -1) {
			Character c = (char) r;

			if (c >= 0x20 && c <= 0x7e)
				System.out.print(encoder.get(c));
		}
	}

}
