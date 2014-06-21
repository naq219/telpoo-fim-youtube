/**
 * 
 */
package com.telpoo.frame.object;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author NAQ219
 * 
 */
public class BaseObject implements Parcelable {

	protected ContentValues params = null;

	public void setParams(ContentValues params) {
		this.params = params;
	}

	public ContentValues getParams() {
		return params;
	}

	public String get(String key) {
		if (params != null)
			return params.getAsString(key);
		else
			return null;
	}

	public void set(String key, String value) {
		if (params == null)
			params = new ContentValues();
		params.put(key, value);

	}

	public void set(String key, int value) {
		if (params == null)
			params = new ContentValues();
		params.put(key, ""+value);
	}

	public void set(String key, long value) {
		if (params == null)
			params = new ContentValues();
		params.put(key, ""+value);
	}

	public boolean getBool(String key) {
		if (params != null){
			return Boolean.parseBoolean(params.getAsString(key));
			
		}
		else
			return false;
	}

	public void set(String key, boolean value) {
		if (params == null)
			params = new ContentValues();
		params.put(key, ""+value);

	}

	public int getInt(String key) {
		// nth
		if (params != null)
			if (params.containsKey(key) )
				{
				
				Integer.parseInt(params.getAsString(key));
				}
		return 0;
	}

	public long getLong(String key) {
		// nth
		if (params != null)
			if (params.containsKey(key) && params.getAsLong(key) != null)
				return params.getAsLong(key);
		return 0;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeParcelable(this.params, i);

	}

	public BaseObject(Parcel parcel) {
		this.params = parcel.readParcelable(getClass().getClassLoader());
	}

	public BaseObject() {

	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public BaseObject createFromParcel(Parcel in) {
			return new BaseObject(in);
		}

		public BaseObject[] newArray(int size) {
			return new BaseObject[size];
		}
	};

}
