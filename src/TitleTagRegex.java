import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TitleTagRegex {

	private static String getInputFromStdIn() throws IOException {
		StringBuilder sb = new StringBuilder();
		InputStreamReader stream = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(stream);
		String line;
		while ((line = in.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}

	/**
	 * Entry point for the program. The program grabs raw html from stdin and
	 * prints out the title contained within.
	 * 
	 * @param args
	 *            None
	 * @throws IOException
	 *             Pipe failure.
	 */
	public static void main(String[] args) throws IOException {
		/*
		 * Note: java seems to correctly choose the most specific rule for the
		 * occasion. Notice that the first 4 rules all begin with the same
		 * character, yet it chooses the most specific as soon as it can
		 * logically deduce which rule applies.
		 */
		String cdre = "(?:\\<!\\[CDATA\\[.*?\\]\\]>)"; // match cdata
		String cre = "(?:\\<!--.*?--[\\s]*>)"; // match any comment
		String mdre = "(?:\\<!.*?>)"; // match any remaining markup declaration
		String tre = "(?:\\<[^!].*?>)"; // match any tag
		String slre = "(?:[^<]*)"; // match any string literal

		String nonComment = "(?:" + cdre + "|" + cre + "|" + mdre + "|" + tre + "|" + slre + ")";
		String html = nonComment + "*?<title>(.*?)</title>" + nonComment + "*";
		String entirety = "\\A" + html + "\\z";

		int flags = Pattern.DOTALL | Pattern.CASE_INSENSITIVE;
		Pattern p = Pattern.compile(entirety, flags);
		Matcher m = p.matcher(getInputFromStdIn());
		if (m.find()) {
			System.out.println(m.group(1));
		}
	}

}
