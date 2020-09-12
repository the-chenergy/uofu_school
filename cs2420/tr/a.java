package tr;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * <code>tr.a.ce()</code>
 * 
 * @author Qianlang Chen
 * @version A 03/02/19
 */
public class a
{
	/**
	 * The world's best <code>trace</code> method. It prints EVERYTHING you give to the
	 * console.<br/>
	 * <br/>
	 * Permitted options (<code>"`[options]"</code>):<br/>
	 * n = extra newline after printing<br/>
	 * q = surround all strings and chars with quotes, also preserve all \n's and \t's<br/>
	 * l = separated by newlines<br/>
	 * s = separated by spaces<br/>
	 * t = separated by tabs<br/>
	 * c = separated by commas<br/>
	 * m = separated by semicolons<br/>
	 * f = well formatted (as in <code>String.format()</code>)<br/>
	 * d = show all details (be careful of <code>StackOverflowException</code>)<br/>
	 * h = hide all details<br/>
	 * k = show stack trace<br/>
	 * p = spell out array elements
	 * 
	 * @param args The stuff to print.
	 * @return The stuff printed.
	 */
	public static String ce(Object ...args)
	{
		// Filter out when no arguments are received.
		
		if (args.length == 0)
		{
			System.out.println();
			
			return "";
		}
		
		// Read options.
		
		int i = 0;
		HashMap<Character, Boolean> opt = new HashMap<>();
		
		if (args[0] instanceof String)
		{
			String optStr = (String)args[0];
			
			if (optStr.length() > 1 && optStr.charAt(0) == '`')
			{
				i = 1;
				
				for (int j = 1; j < optStr.length(); j++)
					opt.put(optStr.charAt(j), true);
			}
		}
		
		boolean showDetails = !opt.getOrDefault('h', false);
		boolean forceShowDetails = opt.getOrDefault('d', false);
		boolean formatStrings = opt.getOrDefault('q', false);
		boolean spellOut = opt.getOrDefault('p', false);
		
		// Figure out the separator.
		
		String sep;
		if (opt.getOrDefault('l', false))
			sep = "\n";
		else if (opt.getOrDefault('s', false))
			sep = " ";
		else if (opt.getOrDefault('t', false))
			sep = "\t";
		else if (opt.getOrDefault('c', false))
			sep = ", ";
		else if (opt.getOrDefault('m', false))
			sep = "; ";
		else
			sep = "";
		
		// Loop and print out everything.
		
		StringBuilder out = new StringBuilder();
		
		for (; i < args.length; i++)
		{
			// Format the string object if needed.
			
			if (args[i] instanceof String && opt.getOrDefault('f', false))
			{
				String temp = (String)args[i];
				int count = 0;
				
				// Count the number of %'s in the string.
				for (int j = temp.indexOf('%'); j != -1; j = temp.indexOf('%', j + 1))
				{
					char next = temp.charAt(j + 1);
					
					if (next == '%') // %% escapes %
					{
						j++;
						continue;
					}
					
					count++;
					
					if (next == 's')
						args[i + count] = toString(args[i + count], showDetails, forceShowDetails, formatStrings,
							spellOut, 1);
				}
				
				out.append(toString(String.format(temp, Arrays.copyOfRange(args, i + 1, i + count + 1)), showDetails,
					forceShowDetails, formatStrings, spellOut, 1));
				
				i += count;
			}
			else
			{
				out.append(toString(args[i], showDetails, forceShowDetails, formatStrings, spellOut, 1));
			}
			
			if (i < args.length - 1)
				out.append(sep);
		}
		
		out.append("\n");
		
		if (opt.getOrDefault('n', false))
			out.append("\n");
		
		
		// Show stack traces if needed.
		
		if (opt.getOrDefault('k', false))
		{
			System.err.print(out);
			
			new e().printStackTrace();
		}
		else
		{
			System.out.print(out);
		}
		
		return out.toString();
	}
	
	private static String toString(Object obj, int tabIndex)
	{
		return toString(obj, true, false, true, false, tabIndex);
	}
	
	private static String toString(Object obj, boolean showDetails, int tabIndex)
	{
		return toString(obj, showDetails, true, false, false, tabIndex);
	}
	
	private static String toString(Object obj, boolean showDetails, boolean forceShowDetails, boolean formatStrings,
		boolean spellOut, int tabIndex)
	{
		if (obj == null)
			return "(null)";
		
		if (obj instanceof Enum)
			return obj.getClass().getName() + "." + obj.toString();
		
		if (obj instanceof Boolean)
			return obj.toString();
		
		if (obj instanceof String)
		{
			String temp = (String)obj;
			
			if (formatStrings)
				temp = "\"" + temp.replace("\\", "\\\\").replace("\"", "\\\"").replace("\r", "\\r").replace("\n", "\\n")
					.replace("\b", "\\\b").replace("\t", "\\t") + "\"";
			
			return temp;
		}
		
		if (obj instanceof Character)
		{
			String temp = obj.toString();
			
			if (formatStrings)
				temp = "'" + temp.replace("\\", "\\\\").replace("\"", "\\\"").replace("\r", "\\r").replace("\n", "\\n")
					.replace("\b", "\\\b").replace("\t", "\\t") + "'";
			
			return temp;
		}
		
		try
		{
			return Long.toString(Long.parseLong(obj.toString()));
		}
		catch (NumberFormatException ex)
		{
		}
		
		try
		{
			return Double.toString(Double.parseDouble(obj.toString()));
		}
		catch (NumberFormatException ex)
		{
		}
		
		if (obj.getClass().isArray())
		{
			if (obj.getClass().getComponentType().isPrimitive())
			{
				String typeName = obj.getClass().getComponentType().getSimpleName();
				ArrayList<Object> temp = new ArrayList<>();
				
				for (int i = 0; i < Array.getLength(obj); i++)
					temp.add(Array.get(obj, i));
				
				return arrayToString(temp.toArray(), typeName, forceShowDetails, spellOut, tabIndex);
			}
			
			return arrayToString((Object[])obj, obj.getClass().getComponentType().getSimpleName(), forceShowDetails,
				spellOut, tabIndex);
		}
		
		if (obj instanceof Collection)
		{
			Object[] temp = ((Collection<?>)obj).toArray();
			
			return arrayToString(temp, temp.getClass().getComponentType().getSimpleName(), forceShowDetails, spellOut,
				tabIndex);
		}
		
		if (obj instanceof Map)
			return mapToString((Map<?, ?>)obj, showDetails, forceShowDetails, tabIndex);
		
		return objectToString(obj, showDetails, forceShowDetails, tabIndex);
	}
	
	private static <T> String arrayToString(T[] arr, String typeName, boolean showDetails, boolean spellOut,
		int tabIndex)
	{
		if (!showDetails)
		{
			if (spellOut)
				return arr.length + " " + typeName + "(s)";
			
			return typeName + "[" + arr.length + "]";
		}
		
		StringBuilder out = new StringBuilder();
		
		if (spellOut)
		{
			if (arr.length == 0)
				return "nothing";
			
			if (arr.length == 1)
				return toString(arr[0], tabIndex);
			
			if (arr.length == 2)
				return toString(arr[0], tabIndex) + " and " + toString(arr[1], tabIndex);
		}
		else
		{
			out.append(typeName + "[" + arr.length + "] {");
		}
		
		for (int i = 0; i < arr.length; i++)
		{
			out.append(toString(arr[i], tabIndex));
			
			if (i < arr.length - 1)
				out.append(", ");
			
			if (spellOut && i == arr.length - 2)
				out.append("and ");
		}
		
		if (!spellOut)
			out.append("}");
		
		return out.toString();
	}
	
	private static <K, V> String mapToString(Map<K, V> map, boolean showDetails, boolean forceShowDetails, int tabIndex)
	{
		if (!showDetails)
			return String.format("Map<%s, %s>", map.keySet().toArray().getClass().getComponentType().getSimpleName(),
				map.values().toArray().getClass().getComponentType().getSimpleName());
		
		String tabs = generateTabs(tabIndex);
		StringBuilder out = new StringBuilder();
		
		for (K k : map.keySet())
			out.append("\n" + tabs + toString(k, forceShowDetails, tabIndex + 1) + ": "
				+ toString(map.get(k), forceShowDetails, tabIndex + 1) + ",");
		
		if (out.length() > 0)
			out.delete(out.length() - 1, out.length());
		
		return "Map {" + out + "\n" + generateTabs(tabIndex - 1) + "}";
	}
	
	private static String objectToString(Object obj, boolean showDetails, boolean forceShowDetails, int tabIndex)
	{
		if (!showDetails)
			return "(object " + obj.getClass().getSimpleName() + ")";
		
		StringBuilder out = new StringBuilder();
		String tabs = generateTabs(tabIndex);
		
		for (Field field : obj.getClass().getDeclaredFields())
		{
			field.setAccessible(true);
			
			try
			{
				out.append("\n" + tabs + field.getName() + ": "
					+ toString(field.get(obj), forceShowDetails, tabIndex + 1) + ",");
			}
			catch (IllegalArgumentException e)
			{
			}
			catch (IllegalAccessException e)
			{
			}
		}
		
		if (out.length() > 0)
			out.delete(out.length() - 1, out.length());
		
		return obj.getClass().getSimpleName() + " {" + out + "\n" + generateTabs(tabIndex - 1) + "}";
	}
	
	private static String generateTabs(int tabIndex)
	{
		return tabIndex == 0 ? "" : String.format("%0" + tabIndex + "d", 0).replace("0", "    ");
	}
	
	private static class e extends Throwable
	{
		private static final long serialVersionUID = 1L;
	}
}
