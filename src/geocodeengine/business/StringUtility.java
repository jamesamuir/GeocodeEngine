package geocodeengine.business;

public final class StringUtility {
	
	public static String encodeHTML(String s)
	{
	    StringBuffer out = new StringBuffer();
	    for(int i=0; i<s.length(); i++)
	    {
	    	try{
	        char c = s.charAt(i);
		        if(c > 127 || c=='"' || c=='<' || c=='>' || c == ' ')
		        {
		           out.append("&#"+(int)c+";");
		        }
		        else
		        {
		            out.append(c);
		        }
	    	}catch (Exception e){
	        	System.out.println(e.getMessage());
	        }
	    }
	    return out.toString();
	}

}
