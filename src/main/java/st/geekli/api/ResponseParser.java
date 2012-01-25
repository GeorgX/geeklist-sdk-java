package st.geekli.api;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import st.geekli.api.type.GeeklistType;

public class ResponseParser {

	public static Object parseObjects(Class<?> targetObjectClass, JSONArray array, boolean relaxed) throws GeeklistApiException
	{
		GeeklistType[] result = (GeeklistType[]) Array.newInstance(targetObjectClass, array.length());
	    
	    for (int i = 0, l = array.length(); i < l; i++)
	    {
	    	JSONObject jsonObject;
	    	try {
	    		jsonObject = array.getJSONObject(i);
	    		result[i] = (GeeklistType) parseObject(targetObjectClass, jsonObject, relaxed);
	    	} catch (JSONException e) {
	    		throw new GeeklistApiException(e);
	    	}
	    }
	    
	    return result;
	  }
	
	public static Object parseObject(Class<?> targetObjectClass, JSONObject object, boolean relaxed) throws GeeklistApiException
	{
		GeeklistType target = createObject(targetObjectClass);
		JSONArray responseFields = object.names();
		
		for(int i = 0; i < responseFields.length(); i++)
		{
			String field = responseFields.optString(i);
			Field targetField = null;
			try {
				targetField = targetObjectClass.getDeclaredField(Utils.toCamelCase(field));
			} catch (SecurityException e) {
			} catch (NoSuchFieldException e) {} // Nevermind the Exceptions here...a non existing field might be not that bad ;)
			
			if(targetField != null) {
				
				Class<?> fieldClass = targetField.getType();
				targetField.setAccessible(true);
				try {
					targetField.set(target, parseValue(fieldClass, field, object, relaxed));
				} catch (IllegalArgumentException e) {
					throw new GeeklistApiException(e);
				} catch (IllegalAccessException e) {
					throw new GeeklistApiException(e);
				}
			    
			} else {
				if(!relaxed) {
					throw new GeeklistApiException("Could not find a matching field in target object");
				}
			}
		}
		
		return target;
	}
	
	private static Object parseValue(Class<?> fieldClass, String fieldName, JSONObject object, boolean relaxed) throws GeeklistApiException
	{
		Object fieldObject = object.opt(fieldName);
		
		if(fieldObject instanceof JSONObject)
		{
			if(isGeeklistType(fieldClass))
			{
				return parseObject(fieldClass, object.optJSONObject(fieldName), relaxed); 
			} else {
				throw new GeeklistApiException("Unknown target class");
			}
		} else if(fieldObject instanceof JSONArray) {
			
			if(fieldClass.isArray())
			{
				JSONArray fieldArray = (JSONArray)fieldObject;
				
				if(fieldArray.length() > 0)
				{
					Class<?> arrayClass = fieldArray.opt(0).getClass();
					Object[] arrayValue = (Object[]) Array.newInstance(arrayClass, fieldArray.length());
					
					for(int i = 0; i < fieldArray.length(); i++)
					{
						if(arrayClass.equals(JSONObject.class))
						{
							if(isGeeklistType(fieldClass))
							{
								arrayValue[i] = parseObject(fieldClass.getComponentType(), fieldArray.optJSONObject(i), relaxed); 
							} else {
								throw new GeeklistApiException("Unknown target class");
							}
						} else {
							
							if(fieldClass.getComponentType().isAssignableFrom(arrayClass))
							{
								arrayValue[i] = arrayClass.cast(fieldArray.opt(i));
							} else {
								throw new GeeklistApiException("Unknown target class");
							}
						}
					}
					return arrayValue;
				} else {
					return null;
				}
			} else {
				throw new GeeklistApiException("Target field is not an array!");
			}
			
		} else {
			
			if(fieldClass.isAssignableFrom(fieldObject.getClass()))
			{
				return fieldClass.cast(fieldObject);
			} else {
				return null;
				// TODO: Ignore for now...Geekli.st may response with JSONObject$Null
				//throw new GeeklistApiException("Mismatching source and target class, Source: "+ fieldClass.getSimpleName() + ", Target: "+ fieldObject.getClass().getSimpleName());
			}
			
		}
	}
	
	private static boolean isGeeklistType(Class<?> objectClass)
	{
		for (Class<?> interfc : objectClass.getInterfaces()) {
			if (interfc.equals(GeeklistType.class)) {
				return true;
			}
		}
		return false;
	}
	
	private static GeeklistType createObject(Class<?> clazz) {
	    try {
	      return (GeeklistType) clazz.newInstance();
	    } catch (InstantiationException e) {
	      return null;
	    } catch (IllegalAccessException e) {
	      return null;
	    }
	  }
}
