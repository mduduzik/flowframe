package org.flowframe.kernel.json.impl;

public class JSONUtil {
	public static class CharPool {
		public static final char AMPERSAND = '&';

		public static final char APOSTROPHE = '\'';

		public static final char AT = '@';

		public static final char BACK_SLASH = '\\';

		public static final char CLOSE_BRACKET = ']';

		public static final char CLOSE_CURLY_BRACE = '}';

		public static final char CLOSE_PARENTHESIS = ')';

		public static final char COLON = ':';

		public static final char COMMA = ',';

		public static final char DASH = '-';

		public static final char EQUAL = '=';

		public static final char GREATER_THAN = '>';

		public static final char FORWARD_SLASH = '/';

		public static final char LESS_THAN = '<';

		public static final char LOWER_CASE_A = 'a';

		public static final char LOWER_CASE_B = 'b';

		public static final char LOWER_CASE_C = 'c';

		public static final char LOWER_CASE_D = 'd';

		public static final char LOWER_CASE_E = 'e';

		public static final char LOWER_CASE_F = 'f';

		public static final char LOWER_CASE_G = 'g';

		public static final char LOWER_CASE_H = 'h';

		public static final char LOWER_CASE_I = 'i';

		public static final char LOWER_CASE_J = 'j';

		public static final char LOWER_CASE_K = 'k';

		public static final char LOWER_CASE_L = 'l';

		public static final char LOWER_CASE_M = 'm';

		public static final char LOWER_CASE_N = 'n';

		public static final char LOWER_CASE_O = 'o';

		public static final char LOWER_CASE_P = 'p';

		public static final char LOWER_CASE_Q = 'q';

		public static final char LOWER_CASE_R = 'r';

		public static final char LOWER_CASE_S = 's';

		public static final char LOWER_CASE_T = 't';

		public static final char LOWER_CASE_U = 'u';

		public static final char LOWER_CASE_V = 'v';

		public static final char LOWER_CASE_W = 'w';

		public static final char LOWER_CASE_X = 'x';

		public static final char LOWER_CASE_Y = 'y';

		public static final char LOWER_CASE_Z = 'z';

		public static final char NUMBER_0 = '0';

		public static final char NUMBER_1 = '1';

		public static final char NUMBER_2 = '2';

		public static final char NUMBER_3 = '3';

		public static final char NUMBER_4 = '4';

		public static final char NUMBER_5 = '5';

		public static final char NUMBER_6 = '6';

		public static final char NUMBER_7 = '7';

		public static final char NUMBER_8 = '8';

		public static final char NUMBER_9 = '9';

		public static final char MINUS = '-';

		public static final char NEW_LINE = '\n';

		public static final char OPEN_BRACKET = '[';

		public static final char OPEN_CURLY_BRACE = '{';

		public static final char OPEN_PARENTHESIS = '(';

		public static final char PERCENT = '%';

		public static final char PERIOD = '.';

		public static final char PIPE = '|';

		public static final char PLUS = '+';

		public static final char POUND = '#';

		public static final char QUESTION = '?';

		public static final char QUOTE = '\"';

		public static final char RETURN = '\r';

		public static final char SEMICOLON = ';';

		public static final char SLASH = FORWARD_SLASH;

		public static final char SPACE = ' ';

		public static final char STAR = '*';

		public static final char TAB = '\t';

		public static final char TILDE = '~';

		public static final char UNDERLINE = '_';

		public static final char UPPER_CASE_A = 'A';

		public static final char UPPER_CASE_B = 'B';

		public static final char UPPER_CASE_C = 'C';

		public static final char UPPER_CASE_D = 'D';

		public static final char UPPER_CASE_E = 'E';

		public static final char UPPER_CASE_F = 'F';

		public static final char UPPER_CASE_G = 'G';

		public static final char UPPER_CASE_H = 'H';

		public static final char UPPER_CASE_I = 'I';

		public static final char UPPER_CASE_J = 'J';

		public static final char UPPER_CASE_K = 'K';

		public static final char UPPER_CASE_L = 'L';

		public static final char UPPER_CASE_M = 'M';

		public static final char UPPER_CASE_N = 'N';

		public static final char UPPER_CASE_O = 'O';

		public static final char UPPER_CASE_P = 'P';

		public static final char UPPER_CASE_Q = 'Q';

		public static final char UPPER_CASE_R = 'R';

		public static final char UPPER_CASE_S = 'S';

		public static final char UPPER_CASE_T = 'T';

		public static final char UPPER_CASE_U = 'U';

		public static final char UPPER_CASE_V = 'V';

		public static final char UPPER_CASE_W = 'W';

		public static final char UPPER_CASE_X = 'X';

		public static final char UPPER_CASE_Y = 'Y';

		public static final char UPPER_CASE_Z = 'Z';
	}

	public static class StringPool {
		public static final String AMPERSAND = "&";

		public static final String AMPERSAND_ENCODED = "&amp;";

		public static final String APOSTROPHE = "'";

		public static final String[] ASCII_TABLE = new String[128];

		public static final String AT = "@";

		public static final String BACK_SLASH = "\\";

		public static final String BETWEEN = "BETWEEN";

		public static final String BLANK = "";

		public static final String CARET = "^";

		public static final String CDATA_CLOSE = "]]>";

		public static final String CDATA_OPEN = "<![CDATA[";

		public static final String CLOSE_BRACKET = "]";

		public static final String CLOSE_CURLY_BRACE = "}";

		public static final String CLOSE_PARENTHESIS = ")";

		public static final String COLON = ":";

		public static final String COMMA = ",";

		public static final String COMMA_AND_SPACE = ", ";

		public static final String DASH = "-";

		public static final String DOLLAR = "$";

		public static final String DOLLAR_AND_OPEN_CURLY_BRACE = "${";

		public static final String DOUBLE_APOSTROPHE = "''";

		public static final String DOUBLE_BACK_SLASH = "\\\\";

		public static final String DOUBLE_CLOSE_BRACKET = "]]";

		public static final String DOUBLE_CLOSE_CURLY_BRACE = "}}";

		public static final String DOUBLE_DASH = "--";

		public static final String DOUBLE_OPEN_BRACKET = "[[";

		public static final String DOUBLE_OPEN_CURLY_BRACE = "{{";

		public static final String DOUBLE_PERIOD = "..";

		public static final String DOUBLE_QUOTE = "\"\"";

		public static final String DOUBLE_SLASH = "//";

		public static final String DOUBLE_SPACE = "  ";

		public static final String DOUBLE_UNDERLINE = "__";

		public static final String EQUAL = "=";

		public static final String EXCLAMATION = "!";

		public static final String FALSE = "false";

		public static final String FORWARD_SLASH = "/";

		public static final String FOUR_SPACES = "    ";

		public static final String GRAVE_ACCENT = "`";

		public static final String GREATER_THAN = ">";

		public static final String GREATER_THAN_OR_EQUAL = ">=";

		public static final String INVERTED_EXCLAMATION = "\u00A1";

		public static final String INVERTED_QUESTION = "\u00BF";

		public static final String IS_NOT_NULL = "IS NOT NULL";

		public static final String IS_NULL = "IS NULL";

		public static final String ISO_8859_1 = "ISO-8859-1";

		public static final String LESS_THAN = "<";

		public static final String LESS_THAN_OR_EQUAL = "<=";

		public static final String LIKE = "LIKE";

		public static final String MINUS = "-";

		public static final String NBSP = "&nbsp;";

		public static final String NEW_LINE = "\n";

		public static final String NOT_EQUAL = "!=";

		public static final String NOT_LIKE = "NOT LIKE";

		public static final String NULL = "null";

		public static final String NULL_CHAR = "\u0000";

		public static final String OPEN_BRACKET = "[";

		public static final String OPEN_CURLY_BRACE = "{";

		public static final String OPEN_PARENTHESIS = "(";

		public static final String OS_EOL = System
				.getProperty("line.separator");

		public static final String PERCENT = "%";

		public static final String PERIOD = ".";

		public static final String PIPE = "|";

		public static final String PLUS = "+";

		public static final String POUND = "#";

		public static final String QUESTION = "?";

		public static final String QUOTE = "\"";

		public static final String RETURN = "\r";

		public static final String RETURN_NEW_LINE = "\r\n";

		public static final String SEMICOLON = ";";

		public static final String SLASH = FORWARD_SLASH;

		public static final String SPACE = " ";

		public static final String STAR = "*";

		public static final String TAB = "\t";

		public static final String THREE_SPACES = "   ";

		public static final String TILDE = "~";

		public static final String TRUE = "true";

		public static final String UNDERLINE = "_";

		public static final String UTC = "UTC";

		public static final String UTF8 = "UTF-8";

		static {
			for (int i = 0; i < 128; i++) {
				ASCII_TABLE[i] = String.valueOf((char) i);
			}
		}
	}

	public static class Validator {
		public static boolean isNotNull(String s) {
			return !isNull(s);
		}
		
		public static boolean isNull(Object obj) {
			if (obj instanceof Long) {
				return isNull((Long) obj);
			} else if (obj instanceof String) {
				return isNull((String) obj);
			} else if (obj == null) {
				return true;
			} else {
				return false;
			}
		}

		public static boolean isNull(Long l) {
			if ((l == null) || l.longValue() == 0) {
				return true;
			} else {
				return false;
			}
		}

		public static boolean isNull(String s) {
			if (s == null) {
				return true;
			}

			int counter = 0;

			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);

				if (c == CharPool.SPACE) {
					continue;
				} else if (counter > 3) {
					return false;
				}

				if (counter == 0) {
					if (c != CharPool.LOWER_CASE_N) {
						return false;
					}
				} else if (counter == 1) {
					if (c != CharPool.LOWER_CASE_U) {
						return false;
					}
				} else if ((counter == 2) || (counter == 3)) {
					if (c != CharPool.LOWER_CASE_L) {
						return false;
					}
				}

				counter++;
			}

			return true;
		}

		public static boolean isNull(Object[] array) {
			if ((array == null) || (array.length == 0)) {
				return true;
			} else {
				return false;
			}
		}
	}
}
