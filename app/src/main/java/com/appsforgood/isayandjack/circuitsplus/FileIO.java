package com.appsforgood.isayandjack.circuitsplus;

import android.content.res.AssetManager;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This is a helper class that makes file input/output easier
 */
public class FileIO {
	AssetManager assets;
	String externalStoragePath;
	
	public FileIO(AssetManager assets) {
		this.assets = assets;
		this.externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
	}
	
	public InputStream readAsset(String fileName) throws IOException {
		return assets.open(fileName);
	}
	
	public InputStream readFile(String fileName) throws IOException {
		return new FileInputStream(externalStoragePath + fileName);
	}
	
	public OutputStream writeFile(String fileName, String dir) throws IOException {
		File foo = new File(Environment.getExternalStorageDirectory().getPath() + dir);
		foo.mkdirs();
		return new FileOutputStream(externalStoragePath + fileName);
	}
}

