package com.structsoftlab.folderpickerdialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.*;
import android.os.*;
import java.util.*;
import android.content.*;

public class MainActivity extends AppCompatActivity implements folderSelectListener 
{
    Button btnDialog;
    TextView textView;
	FileDialog fd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        
		fd = new FileDialog(this, this);
		fd.setValidExts(new String[]{"jpg", "mp4", "txt", "csv", "html", "java", "xml", "png"});
        btnDialog = (Button) findViewById(R.id.btnDialog);
        btnDialog.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					//showMyDialog();
					fd.showFileDialog("");
				}
			});
    }
	
	@Override
	public void onSelectEvent(String path, Item item){
		textView.setText(path + "\r\n" + item.file);
	}
	
}


