package com.structsoftlab.folderpickerdialog;
import java.util.*;

public class Item {
	public String file;
	public int icon;
	public long size;
	public long date;

	public Item(String file, Integer icon, long s, long d) {
		this.file = file;
		this.icon = icon;
		this.size = s;
		this.date = d;
	}

	@Override
	public String toString() {
		return file;
	}
}
class ItemComparator implements Comparator<Item>
{
    public int compare(Item left, Item right) {
        return left.date < right.date?1:-1;
    }
}
