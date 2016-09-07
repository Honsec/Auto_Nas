package genius.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UtilsSP {
  private   String TAG=TAG=UtilsSP.class.getSimpleName();

	private Context context;
	private SharedPreferences sp = null;
	private SharedPreferences.Editor edit = null;

	public UtilsSP(Context context) {
		this(context, PreferenceManager.getDefaultSharedPreferences(context));
	}

	public UtilsSP(Context context, String filename) {
		this(context, context.getSharedPreferences(filename, Context.MODE_PRIVATE));
	}


	public UtilsSP(Context context, SharedPreferences sp) {
		this.context = context;
		this.sp = sp;
		edit = sp.edit();
	}

	public SharedPreferences getInstance() {
		return sp;
	}


	public synchronized void setValue(String key, boolean value) {
		edit.putBoolean(key, value);
		edit.commit();
	}

	public synchronized void setValue(int resKey, boolean value) {
		setValue(this.context.getString(resKey), value);
	}

	// Float
	public synchronized void setValue(String key, float value) {
		edit.putFloat(key, value);
		edit.commit();
	}

	public synchronized void setValue(int resKey, float value) {
		setValue(this.context.getString(resKey), value);
	}

	// Integer
	public synchronized void setValue(String key, int value) {
		edit.putInt(key, value);
		edit.commit();
	}

	public synchronized void setValue(int resKey, int value) {
		setValue(this.context.getString(resKey), value);
	}

	// Long
	public synchronized void setValue(String key, long value) {
		edit.putLong(key, value);
		edit.commit();
	}

	public synchronized void setValue(int resKey, long value) {
		setValue(this.context.getString(resKey), value);
	}

	// String
	public synchronized void setValue(String key, String value) {
		edit.putString(key, value);
		edit.commit();
	}

	public synchronized void setValue(int resKey, String value) {
		setValue(this.context.getString(resKey), value);
	}

	public synchronized void setValue(String key, Set<String> value) {
		edit.putStringSet(key, value);
		edit.commit();
	}
	public synchronized void setValue(int resKey, Set<String> value) {
		setValue(this.context.getString(resKey), value);
	}
	public Set<String> getValue(String key, Set<String> defaultValue) {
		return sp.getStringSet(key, defaultValue);
	}

	public Set<String> getValue(int resKey,  Set<String> defaultValue) {
		return getValue(this.context.getString(resKey), defaultValue);
	}


	public synchronized void setValue(String key, ArrayList<String> value) {
		Set<String> strings = new HashSet<>();
		int i=0;
		for(String str : value){
			strings.add((i++)+"=>"+str);
		}
		edit.putStringSet(key, strings);
		edit.commit();
	}
	public synchronized void setValue(int resKey, ArrayList<String> value) {
		setValue(this.context.getString(resKey), value);
	}

	public ArrayList<String> getValue(String key, ArrayList<String> defaultValue) {

		Set<String> stringSet = sp.getStringSet(key, new HashSet<String>());
		Iterator<String> iterator = stringSet.iterator();

		HashMap<String,String> stringStringHashMap = new HashMap<>();
		ArrayList<String> result = new ArrayList<>();
		while (iterator.hasNext()){
			String next = iterator.next();
			String[] split = next.split("=>");
			if(split!=null){
				if(split.length>1){
					stringStringHashMap.put(split[0],next.substring(next.indexOf(split[1])));
				}else{
					stringStringHashMap.put(split[0],"");
				}
			}
		}

		for(int i =0 ; i<stringStringHashMap.size();i++){
			result.add(stringStringHashMap.get(i+"").toString());
		}

		if(result.size() ==0){
			return  defaultValue;
		}

		return result;
	}

	public ArrayList<String> getValue(int resKey,  ArrayList<String> defaultValue) {
		return getValue(this.context.getString(resKey), defaultValue);
	}



	// Get

	// Boolean
	public boolean getValue(String key, boolean defaultValue) {
		return sp.getBoolean(key, defaultValue);
	}

	public boolean getValue(int resKey, boolean defaultValue) {
		return getValue(this.context.getString(resKey), defaultValue);
	}

	// Float
	public float getValue(String key, float defaultValue) {
		return sp.getFloat(key, defaultValue);
	}

	public float getValue(int resKey, float defaultValue) {
		return getValue(this.context.getString(resKey), defaultValue);
	}

	// Integer
	public int getValue(String key, int defaultValue) {
		return sp.getInt(key, defaultValue);
	}

	public int getValue(int resKey, int defaultValue) {
		return getValue(this.context.getString(resKey), defaultValue);
	}

	// Long
	public long getValue(String key, long defaultValue) {
		return sp.getLong(key, defaultValue);
	}

	public long getValue(int resKey, long defaultValue) {
		return getValue(this.context.getString(resKey), defaultValue);
	}

	// String
	public String getValue(String key, String defaultValue) {
		return sp.getString(key, defaultValue);
	}

	public String getValue(int resKey, String defaultValue) {
		return getValue(this.context.getString(resKey), defaultValue);
	}

	// Delete
	public synchronized void remove(String key) {
		edit.remove(key);
		edit.commit();
	}

	public synchronized void clear() {
		edit.clear();
		edit.commit();
	}


}
