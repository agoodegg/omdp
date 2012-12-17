package com.omdp.webapp.base.util;

/**
 * 字符缓冲
 * 用于在解析过程中保存还未解析的字符
 *
 */
public class CharBuffer{
	
	char[] buffer;
	int count=0;

	public CharBuffer(){
		buffer=new char[16];
	}

	/**
     * This implements the expansion semantics of ensureCapacity with no
     * size check or synchronization.
     */
	void expandCapacity(int minimumCapacity) {
		int newCapacity = (buffer.length + 1) * 2;
		if (newCapacity < 0) {
			newCapacity = Integer.MAX_VALUE;
		} else if (minimumCapacity > newCapacity) {
			newCapacity = minimumCapacity;
		}	
		char newValue[] = new char[newCapacity];
		System.arraycopy(buffer, 0, newValue, 0, count);
		buffer = newValue;
	}
	
	public void put(String value) {
		if(value==null)return;
		char[] chars=value.toCharArray();
		for(int i=0;i<chars.length;i++){
			put(chars[i]);
		}
		
	}

	public String clear() {
		String result=new String(buffer,0,count);
		count=0;
		return result;
	}

	public void put(char c) {
        int newCount = count + 1;
    	if (newCount > buffer.length)
    	    expandCapacity(newCount);
    	buffer[count++] = c;
	}

	public boolean matchIgnoreCase(String string) {
		if(string.length()>size())return false;
		int offset=count - string.length();
		for(int i=0;i<string.length();i++){
			if(Character.toUpperCase(buffer[offset + i])!=Character.toUpperCase(string.charAt(i))){
				return false;
			}
		}
		return true;
	}

	public String clear(int len) {
		String result=new String(buffer,count - len,len);
		count=count-len;
		return result;
	}
	
	public int size(){
		return count;
	}
	
	public String toString(){
		return new String(buffer,0,count);
	}


	public char lastChar() {
		return count==0 ? (char) -1 : buffer[count - 1];
	}
	
}

