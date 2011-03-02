import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TitleTagRegex {

	private static String getFileFromStdIn() throws IOException {
		StringBuilder sb = new StringBuilder();
		InputStreamReader stream = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(stream);
		String line;
		while ((line = in.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		String cdata = "(?:\\<!\\[CDATA\\[.*?\\]\\]>)"; // match cdata
		String cre = "(?:\\<!--.*?--[\\s]*>)"; // match any comment
		String mdcl = "(?:\\<!.*?>)"; // match markup declaration
		String tre = "(?:\\<[^!].*?>)"; // match any tag
		String slit = "(?:[^<]*)"; // match any string literal

		String nonComment = "(?:" + cdata + "|" + cre + "|" + mdcl + "|" + tre + "|" + slit + ")";
		String html = "(?:" + nonComment + ")*?<title>(.*?)</title>(?:" + nonComment + ")*";
		String entirety = "\\A" + html + "\\z";

		int flags = Pattern.DOTALL | Pattern.CASE_INSENSITIVE;
		Pattern p = Pattern.compile(entirety, flags);
		String input;
		try {
			input = getFileFromStdIn();
			Matcher m = p.matcher(input);
			if (m.find()) {
				System.out.println(m.group(1));
			}
		} catch (IOException e) {
			System.err.println("usage: java TitleTagRegex < html");
		}
	}

}
