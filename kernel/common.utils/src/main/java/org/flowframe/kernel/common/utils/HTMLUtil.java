package org.flowframe.kernel.common.utils;

public class HTMLUtil {

	public static final int ESCAPE_MODE_ATTRIBUTE = 1;

	public static final int ESCAPE_MODE_CSS = 2;

	public static final int ESCAPE_MODE_JS = 3;

	public static final int ESCAPE_MODE_TEXT = 4;

	public static final int ESCAPE_MODE_URL = 5;

	public static String escape(String text) {
		if (text == null) {
			return null;
		}

		if (text.length() == 0) {
			return StringPool.BLANK;
		}

		// Escape using XSS recommendations from
		// http://www.owasp.org/index.php/Cross_Site_Scripting
		// #How_to_Protect_Yourself

		StringBuilder sb = null;

		int lastReplacementIndex = 0;

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);

			String replacement = null;

			switch (c) {
				case '<':
					replacement = "&lt;";

					break;

				case '>':
					replacement = "&gt;";

					break;

				case '&':
					replacement = "&amp;";

					break;

				case '"':
					replacement = "&#034;";

					break;

				case '\'':
					replacement = "&#039;";

					break;

				case '\u00bb': // '�'
					replacement = "&#187;";

					break;

				case '\u2013':
					replacement = "&#x2013;";

					break;

				case '\u2014':
					replacement = "&#x2014;";

					break;
			}

			if (replacement != null) {
				if (sb == null) {
					sb = new StringBuilder();
				}

				if (i > lastReplacementIndex) {
					sb.append(text.substring(lastReplacementIndex, i));
				}

				sb.append(replacement);

				lastReplacementIndex = i + 1;
			}
		}

		if (sb == null) {
			return text;
		}
		else {
			if (lastReplacementIndex < text.length()) {
				sb.append(text.substring(lastReplacementIndex));
			}

			return sb.toString();
		}
	}

	public static String escape(String text, int type) {
		if (text == null) {
			return null;
		}

		if (text.length() == 0) {
			return StringPool.BLANK;
		}

		String prefix = StringPool.BLANK;
		String postfix = StringPool.BLANK;

		if (type == ESCAPE_MODE_ATTRIBUTE) {
			prefix = "&#x";
			postfix = StringPool.SEMICOLON;
		}
		else if (type == ESCAPE_MODE_CSS) {
			prefix = StringPool.BACK_SLASH;
		}
		else if (type == ESCAPE_MODE_JS) {
			prefix = "\\x";
		}
		else if (type == ESCAPE_MODE_URL) {
			return HTTPUtil.encodeURL(text, true);
		}
		else {
			return escape(text);
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);

			if (Character.isLetterOrDigit(c) ||
				(c == CharPool.DASH) || (c == CharPool.UNDERLINE)) {

				sb.append(c);
			}
			else {
				sb.append(prefix);

				String hexString = StringUtil.toHexString(c);

				if (hexString.length() == 1) {
					sb.append(StringPool.ASCII_TABLE[48]);
				}

				sb.append(hexString);
				sb.append(postfix);
			}
		}

		if (sb.length() == text.length()) {
			return text;
		}
		else {
			return sb.toString();
		}
	}

	public String escapeAttribute(String attribute) {
		return escape(attribute, ESCAPE_MODE_ATTRIBUTE);
	}

	public String escapeCSS(String css) {
		return escape(css, ESCAPE_MODE_CSS);
	}

	public String escapeHREF(String href) {
		if (href == null) {
			return null;
		}

		if (href.length() == 0) {
			return StringPool.BLANK;
		}

		if (href.indexOf(StringPool.COLON) == 10) {
			String protocol = href.substring(0, 10).toLowerCase();

			if (protocol.equals("javascript")) {
				return StringUtil.replaceFirst(href, StringPool.COLON, "%3a");
			}
		}

		return href;
	}

	public String escapeJS(String js) {
		return escape(js, ESCAPE_MODE_JS);
	}

	public String escapeURL(String url) {
		return escape(url, ESCAPE_MODE_URL);
	}

	public String escapeXPath(String xPath) {
		if (Validator.isNull(xPath)) {
			return xPath;
		}

		StringBuilder sb = new StringBuilder(xPath.length());

		for (int i = 0; i < xPath.length(); i++) {
			char c = xPath.charAt(i);

			boolean hasToken = false;

			for (int j = 0; j < _XPATH_TOKENS.length; j++) {
				if (c == _XPATH_TOKENS[j]) {
					hasToken = true;

					break;
				}
			}

			if (hasToken) {
				sb.append(StringPool.UNDERLINE);
			}
			else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	public String escapeXPathAttribute(String xPathAttribute) {
		boolean hasApostrophe = xPathAttribute.contains(StringPool.APOSTROPHE);
		boolean hasQuote = xPathAttribute.contains(StringPool.QUOTE);

		if (hasQuote && hasApostrophe) {
			String[] parts = xPathAttribute.split(StringPool.APOSTROPHE);

			return "concat('".concat(
				StringUtil.merge(parts, "', \"'\", '")).concat("')");
		}

		if (hasQuote) {
			return StringPool.APOSTROPHE.concat(xPathAttribute).concat(
				StringPool.APOSTROPHE);
		}

		return StringPool.QUOTE.concat(xPathAttribute).concat(StringPool.QUOTE);
	}


	public String fromInputSafe(String text) {
		return StringUtil.replace(text, "&amp;", "&");
	}

	public String replaceMsWordCharacters(String text) {
		return StringUtil.replace(text, _MS_WORD_UNICODE, _MS_WORD_HTML);
	}

	public String stripBetween(String text, String tag) {
		return StringUtil.stripBetween(text, "<" + tag, "</" + tag + ">");
	}

	public String stripComments(String text) {
		return StringUtil.stripBetween(text, "<!--", "-->");
	}

	public String stripHtml(String text) {
		if (text == null) {
			return null;
		}

		text = stripComments(text);

		StringBuilder sb = new StringBuilder(text.length());

		int x = 0;
		int y = text.indexOf("<");

		while (y != -1) {
			sb.append(text.substring(x, y));
			sb.append(StringPool.SPACE);

			// Look for text enclosed by <script></script>

			boolean scriptFound = isScriptTag(text, y + 1);

			if (scriptFound) {
				int pos = y + _TAG_SCRIPT.length;

				// Find end of the tag

				pos = text.indexOf(">", pos);

				if (pos >= 0) {

					// Check if preceding character is / (i.e. is this instance
					// of <script/>)

					if (text.charAt(pos-1) != '/') {

						// Search for the ending </script> tag

						for (;;) {
							pos = text.indexOf("</", pos);

							if (pos >= 0) {
								if (isScriptTag(text, pos + 2)) {
									y = pos;

									break;
								}
								else {

									// Skip past "</"

									pos += 2;
								}
							}
							else {
								break;
							}
						}
					}
				}
			}

			x = text.indexOf(">", y);

			if (x == -1) {
				break;
			}

			x++;

			if (x < y) {

				// <b>Hello</b

				break;
			}

			y = text.indexOf("<", x);
		}

		if (y == -1) {
			sb.append(text.substring(x));
		}

		return sb.toString();
	}

	public String toInputSafe(String text) {
		return StringUtil.replace(
			text,
			new String[] {"&", "\""},
			new String[] {"&amp;", "&quot;"});
	}

	public String unescape(String text) {
		if (text == null) {
			return null;
		}

		if (text.length() == 0) {
			return StringPool.BLANK;
		}

		// Optimize this

		text = StringUtil.replace(text, "&lt;", "<");
		text = StringUtil.replace(text, "&gt;", ">");
		text = StringUtil.replace(text, "&amp;", "&");
		text = StringUtil.replace(text, "&#034;", "\"");
		text = StringUtil.replace(text, "&#039;", "'");
		text = StringUtil.replace(text, "&#040;", "(");
		text = StringUtil.replace(text, "&#041;", ")");
		text = StringUtil.replace(text, "&#044;", ",");
		text = StringUtil.replace(text, "&#035;", "#");
		text = StringUtil.replace(text, "&#037;", "%");
		text = StringUtil.replace(text, "&#059;", ";");
		text = StringUtil.replace(text, "&#061;", "=");
		text = StringUtil.replace(text, "&#043;", "+");
		text = StringUtil.replace(text, "&#045;", "-");

		return text;
	}

	public String unescapeCDATA(String text) {
		if (text == null) {
			return null;
		}

		if (text.length() == 0) {
			return StringPool.BLANK;
		}

		text = StringUtil.replace(text, "&lt;![CDATA[", "<![CDATA[");
		text = StringUtil.replace(text, "]]&gt;", "]]>");

		return text;
	}



	protected boolean isScriptTag(String text, int pos) {
		if ((pos + _TAG_SCRIPT.length + 1) <= text.length()) {
			char item;

			for (int i = 0; i < _TAG_SCRIPT.length; i++) {
				item = text.charAt(pos++);

				if (Character.toLowerCase(item) != _TAG_SCRIPT[i]) {
					return false;
				}
			}

			item = text.charAt(pos);

			// Check that char after "script" is not a letter (i.e. another tag)

			return !Character.isLetter(item);
		}
		else {
			return false;
		}
	}

	private static final String[] _MS_WORD_HTML = new String[] {
		"&reg;", StringPool.APOSTROPHE, StringPool.QUOTE, StringPool.QUOTE
	};

	private static final String[] _MS_WORD_UNICODE = new String[] {
		"\u00ae", "\u2019", "\u201c", "\u201d"
	};

	private static final char[] _TAG_SCRIPT = {'s', 'c', 'r', 'i', 'p', 't'};

	// See http://www.w3.org/TR/xpath20/#lexical-structure

	private static final char[] _XPATH_TOKENS = {
		'(', ')', '[', ']', '.', '@', ',', ':', '/', '|', '+', '-', '=', '!',
		'<', '>', '*', '$', '"', '"', ' ', 9, 10, 13, 133, 8232};

}
