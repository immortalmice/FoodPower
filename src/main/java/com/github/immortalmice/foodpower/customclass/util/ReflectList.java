package com.github.immortalmice.foodpower.customclass.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/* Fetch non-null fields values in TARGET class with TYPE */
/* Work like a List (will call ReflectList#fetch first), but most of method are disabled */
/* Will catch values when it found all fields are non-null */
public class ReflectList<TYPE, TARGET> implements List<TYPE>{
	private Class<TYPE> listType;
	private Class<TARGET> targetClass;
	private TARGET targetInstance;
	private boolean fuzzyMode = false;
	private boolean allFetched = false;
	private List<TYPE> list = new ArrayList<TYPE>();

	public ReflectList(Class<TYPE> listTypeIn, Class<TARGET> targetClassIn, TARGET targetInstanceIn){
		this(listTypeIn, targetClassIn, targetInstanceIn, false);
	}

	public ReflectList(Class<TYPE> listTypeIn, Class<TARGET> targetClassIn, TARGET targetInstanceIn, boolean fuzzyModeIn){
		this.listType = listTypeIn;
		this.targetClass = targetClassIn;
		this.targetInstance = targetInstanceIn;
		this.fuzzyMode = fuzzyModeIn;
	}

	/* Suppress warning when casting value to TYPE */
	@SuppressWarnings("unchecked")
	private void fetch(){
		if(!this.allFetched){
			boolean hasUnresolvedField = false;
			this.list.clear();

			Field[] fields = this.targetClass.getFields();
			for(Field field : fields){
				try{
					boolean accept = false;
					if(!fuzzyMode){
						accept = field.getType() == this.listType;
					}else{
						accept = field.getType().isAssignableFrom(this.listType);
					}
					
					if(accept){
						Object value = field.get(this.targetInstance);
						if(value != null){
							this.list.add((TYPE)field.get(this.targetInstance));
						}else{
							hasUnresolvedField = true;
						}
					}
				}catch(Exception e){
					hasUnresolvedField = true;
					/* Skip this field */
				}
			}
			this.allFetched = !hasUnresolvedField;
		}
	}

	public boolean isAllFetched(){
		return this.allFetched;
	}

	public List<TYPE> asList(){
		this.fetch();
		ArrayList<TYPE> copy = new ArrayList<TYPE>(this.list);
		return copy;
	}

	@Override
	public void clear(){
		this.list.clear();
		this.allFetched = false;
	}

	@Override
	public boolean contains(Object o){
		this.fetch();
		return this.list.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c){
		this.fetch();
		return this.list.containsAll(c);
	}

	@Override
	public TYPE get(int index){
		this.fetch();
		return this.list.get(index);
	}

	@Override
	public int indexOf(Object o){
		this.fetch();
		return this.list.indexOf(o);
	}

	@Override
	public boolean isEmpty(){
		this.fetch();
		return this.list.isEmpty();
	}

	@Override
	public Iterator<TYPE> iterator(){
		this.fetch();
		return this.list.iterator();
	}

	@Override
	public int lastIndexOf(Object o){
		this.fetch();
		return this.list.lastIndexOf(o);
	}

	@Override
	public ListIterator<TYPE> listIterator(){
		this.fetch();
		return this.list.listIterator();
	}

	@Override
	public ListIterator<TYPE> listIterator(int index){
		this.fetch();
		return this.list.listIterator();
	}

	@Override
	public int size(){
		this.fetch();
		return this.list.size();
	}

	@Override
	public List<TYPE> subList(int fromIndex, int toIndex){
		this.fetch();
		return this.list.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray(){
		this.fetch();
		return this.list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		this.fetch();
		return this.list.toArray(a);
	}

	/* DISABLE */
	@Override
	public boolean remove(Object o){return false;}
	/* DISABLE */
	@Override
	public TYPE remove(int index){return null;}
	/* DISABLE */
	@Override
	public boolean removeAll(Collection<?> c){return false;}
	/* DISABLE */
	@Override
	public boolean retainAll(Collection<?> c){return false;}
	/* DISABLE */
	@Override
	public TYPE set(int index, TYPE element){return null;}
	/* DISABLE */
	@Override
	public boolean add(TYPE e){return false;}
	/* DISABLE */
	@Override
	public void add(int index, TYPE element){}
	/* DISABLE */
	@Override
	public boolean addAll(Collection<? extends TYPE> c){return false;}
	/* DISABLE */
	@Override
	public boolean addAll(int index, Collection<? extends TYPE> c){return false;}
}