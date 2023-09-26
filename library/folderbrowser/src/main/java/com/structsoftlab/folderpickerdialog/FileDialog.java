package com.structsoftlab.folderpickerdialog;
import android.support.v7.app.*;
import android.content.*;
import android.os.*;
import java.util.*;
import java.io.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.*;

public class FileDialog
{
	Item selectedItem = null;
	String currentPath = "";
	String root = "";
	AlertDialog.Builder alertDialog = null;
	folderSelectListener mfsl;
	AlertDialog alert = null;
	Context context;
	TextView textView = null;
	String[] exts = new String[]{};


	FileDialog(Context c, folderSelectListener fsl)
	{
		this.mfsl = fsl;
		this.context = c;
		root = Environment.getExternalStorageDirectory().getAbsolutePath();
		currentPath = root;
		alertDialog = new AlertDialog.Builder(c);
		alertDialog.setNegativeButton("Cancel",
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,
									int which)
				{
					// TODO Auto-generated method stub
				}
			});
		alertDialog.setPositiveButton("OK",
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,
									int which)
				{
					if (selectedItem != null && mfsl != null){
						mfsl.onSelectEvent(currentPath, selectedItem);
						
					}
				}
			});

	}
	void setValidExts(String[] s){
		exts = s;
	}
	void showFileDialog(String path)
	{
		ArrayList<Item> fileList = new ArrayList<Item>();
		ArrayList<Item> dirList = new ArrayList<Item>();
		if (path.isEmpty()) path = currentPath;
		File dir = new File(path);
		currentPath = path;

		if (dir.exists())
		{
			File[] files = dir.listFiles();
			for (int i=0; i < files.length; i++)
			{
				long d = files[i].lastModified();
				long s = files[i].length();
				if (files[i].isDirectory()){
					s = 0;
				}
				Item item = new Item(files[i].getName(), 
									 files[i].isFile() ? R.drawable.file : R.drawable.folder, s, d);
				if (files[i].isFile()){
					if (exts.length == 0)
						fileList.add(item);
					else
					for (String ext: exts){
						if (item.file.endsWith("."+ext)){
							fileList.add(item);
						}
					}
				}
				else dirList.add(item);
			}

		}
		Collections.sort(fileList, new ItemComparator());
		Collections.sort(dirList, new ItemComparator());
		if (!path.equals(root))
			dirList.add(0, new Item("..", R.drawable.back,0,0));

		dirList.addAll(fileList);
		MyCustomAdapter myadapter = new MyCustomAdapter(context, R.layout.list_item, dirList);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View convertView = (View) inflater.inflate(R.layout.filedialog_activity, null);
		textView = convertView.findViewById(R.id.text_path);
		ListView listView = (ListView) convertView.findViewById(R.id.mylistview);
		listView.setAdapter(myadapter);
		if (!currentPath.equals(root))
			textView.setText(currentPath.substring(root.length()));

		alertDialog.setView(convertView);
		listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> adapter, View view,
										int position, long id)
				{
					// TODO Auto-generated method stub
					MyCustomAdapter myadapter = (MyCustomAdapter) adapter.getAdapter();
					selectedItem = myadapter.getItem(position);
					File dir = new File(currentPath, selectedItem.file);
					
					if (dir.isDirectory())
					{
						if (selectedItem.file == "..")
							currentPath = currentPath.substring(0, currentPath.lastIndexOf("/"));
						else currentPath += "/" + selectedItem.file;
						alert.dismiss();
						showFileDialog(currentPath);
					}
					//alert.cancel();
				}
			});

		alert = alertDialog.create();
		alert.setTitle("Select Your Items...."); // Title

		alert.show();
	}
}
interface folderSelectListener {
	void onSelectEvent(String path, Item item);
}
