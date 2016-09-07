/*
 * @Title DownloadService.java
 * @Copyright Copyright 2010-2015 Yann Software Co,.Ltd All Rights Reserved.
 * @Description��
 * @author Yann
 * @date 2015-8-7 ����10:03:42
 * @version 1.0
 */
package com.sera.hongsec.volleyhelper.download;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

//Intent intent = new Intent(mContext, DownloadService.class);
//		intent.setAction(DownloadService.ACTION_START);
//		intent.putExtra("fileInfo", fileInfo);
//		mContext.startService(intent);



//IntentFilter filter = new IntentFilter();
//		filter.addAction(DownloadService.ACTION_UPDATE);
//		filter.addAction(DownloadService.ACTION_FINISHED);
//		registerReceiver(mReceiver, filter);

//BroadcastReceiver mReceiver = new BroadcastReceiver()
//		{
//@Override
//public void onReceive(Context context, Intent intent)
//		{
//		if (DownloadService.ACTION_UPDATE.equals(intent.getAction()))
//		{
//		int finised = intent.getIntExtra("finished", 0);
//		int id = intent.getIntExtra("id", 0);
//		mAdapter.updateProgress(id, finised);
//		Log.i("mReceiver", id + "-finised = " + finised);
//		}
//		else if (DownloadService.ACTION_FINISHED.equals(intent.getAction()))
//		{
//		// ���ؽ���
//		FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
//		mAdapter.updateProgress(fileInfo.getId(), 0);
//		Toast.makeText(MainActivity.this,
//		mFileInfoList.get(fileInfo.getId()).getFileName() + "�������",
//		0).show();
//		}
//		}
//		};


/**
 * ��ע��
 * @author Yann
 * @date 2015-8-7 ����10:03:42
 */
public class DownloadService extends Service
{
//	public static final String DOWNLOAD_PATH = "/data/data/com.miniram.donpush.cn/downloads/";
	public static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory()+"/downloads/";//TODO
	public static final String ACTION_START = "ACTION_START";
	public static final String ACTION_STOP = "ACTION_STOP";
	public static final String ACTION_UPDATE = "ACTION_UPDATE";
	public static final String ACTION_FINISHED = "ACTION_FINISHED";
	public static final int MSG_INIT = 0;
	private String TAG = "DownloadService";
	private Map<Integer, DownloadTask> mTasks = 
			new LinkedHashMap<Integer, DownloadTask>();
	
	/**
	 * @see Service#onStartCommand(Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// ���Activity�������Ĳ���
		try {
			if (ACTION_START.equals(intent.getAction()))
            {
                FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
                Log.i(TAG , "Start:" + fileInfo.toString());
                // ������ʼ���߳�
                new InitThread(fileInfo).start();
            }
            else if (ACTION_STOP.equals(intent.getAction()))
            {
                FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
                Log.i(TAG , "Stop:" + fileInfo.toString());

                // �Ӽ�����ȡ����������
                DownloadTask task = mTasks.get(fileInfo.getId());
                if (task != null)
                {
                    task.isPause = true;
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
		}

		return super.onStartCommand(intent, flags, startId);
	}
	
	private Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg) {
			switch (msg.what)
			{
				case MSG_INIT:
					FileInfo fileInfo = (FileInfo) msg.obj;
					Log.i(TAG, "Init:" + fileInfo);
					// ������������
					DownloadTask task = new DownloadTask(DownloadService.this, fileInfo, 3);
					task.downLoad();
					// ������������ӵ�������
					mTasks.put(fileInfo.getId(), task);
					break;

				default:
					break;
			}
		};
	};
	
	private class InitThread extends Thread
	{
		private FileInfo mFileInfo = null;
		
		public InitThread(FileInfo mFileInfo)
		{
			this.mFileInfo = mFileInfo;
		}
		
		/**
		 * @see Thread#run()
		 */
		@Override
		public void run()
		{
			HttpURLConnection connection = null;
			RandomAccessFile raf = null;
			
			try
			{
				// ���������ļ�
				URL url = new URL(mFileInfo.getUrl());
				connection = (HttpURLConnection) url.openConnection();
				connection.setConnectTimeout(5000);
				connection.setRequestMethod("GET");
				int length = -1;
				
				if (connection.getResponseCode() == HttpStatus.SC_OK)
				{
					// ����ļ��ĳ���
					length = connection.getContentLength();
				}
				
				if (length <= 0)
				{
					return;
				}
				
				File dir = new File(DOWNLOAD_PATH);
				if (!dir.exists())
				{
					dir.mkdir();
				}
				
				// �ڱ��ش����ļ�
				File file = new File(dir, mFileInfo.getFileName());
				raf = new RandomAccessFile(file, "rwd");
				// �����ļ�����
				raf.setLength(length);
				mFileInfo.setLength(length);
				mHandler.obtainMessage(MSG_INIT, mFileInfo).sendToTarget();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				if (connection != null)
				{
					connection.disconnect();
				}
				if (raf != null)
				{
					try
					{
						raf.close();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * @see Service#onBind(Intent)
	 */
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

}
