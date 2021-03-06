package sh.calaba.instrumentationbackend.query.ast;

import java.util.ArrayList;
import java.util.List;

public class UIQueryASTClassName implements UIQueryAST {
	public final String simpleClassName;	
	@SuppressWarnings("rawtypes")
	public final Class qualifiedClassName;
	
	public UIQueryASTClassName(String simpleClassName) 
	{
		if (simpleClassName == null) {throw new IllegalArgumentException("Cannot instantiate with null class");}
		this.simpleClassName = simpleClassName;
		this.qualifiedClassName = null;
	}
	
	@SuppressWarnings("rawtypes")
	public UIQueryASTClassName(Class qualifiedClassName)
	{
		if (qualifiedClassName == null) {throw new IllegalArgumentException("Cannot instantiate with null class");}
		this.qualifiedClassName = qualifiedClassName;
		this.simpleClassName = null;
		
	}

	@SuppressWarnings({ "rawtypes"})
	@Override
	public List evaluateWithViewsAndDirection(List inputViews,
			UIQueryDirection direction) {
		// TODO Auto-generated method stub
		
		List result = new ArrayList(8);
		for (Object o : inputViews) 
		{			
			switch(direction) {
				case DESCENDANT:
					addDecendantMatchesToResult(o,result);
					break;
				case CHILD:
					addChildMatchesToResult(o,result);
					break;
				case PARENT:
					addParentMatchesToResult(o,result);
					break;
			}
			
			
		}
		
		
		return result;
	}

	@SuppressWarnings("rawtypes")
	private void addParentMatchesToResult(Object o, List result) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addChildMatchesToResult(Object o, List result) {
		for (Object child : UIQueryUtils.subviews(o))
		{
			if (match(child))
			{
				result.add(child);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void addDecendantMatchesToResult(Object o, List result) {
		if (match(o)) 
		{
			result.add(o);
		}
				
		for (Object child : UIQueryUtils.subviews(o))
		{		
			addDecendantMatchesToResult(child, result);
		}
		
	}
	
	private boolean match(Object o)
	{
		return matchSimpleClassName(o,this.simpleClassName) ||
				matchQualifiedClassName(o,this.qualifiedClassName);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean matchQualifiedClassName(Object o, Class qualifiedClassName) {
		return qualifiedClassName != null && qualifiedClassName.isAssignableFrom(o.getClass());
	}

	public static boolean matchSimpleClassName(Object o, String simpleClassName) {
		// TODO Auto-generated method stub
		return simpleClassName != null && simpleClassName.equalsIgnoreCase(o.getClass().getSimpleName());
	}
	
	public String toString() {
		if (this.simpleClassName != null) 
		{
			return "Class["+this.simpleClassName+"]";	
		}
		else 
		{
			return "Class["+this.qualifiedClassName+"]";
		}
		
	}
	
	
}
