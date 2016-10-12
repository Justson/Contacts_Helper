package com.qypt.just_syn_asis_version1_0.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.FileNameMap;
import java.net.URLConnection;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.qypt.just_syn_asis_version1_0.model.TaskBean;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * 对OkHttp进行二次的封装
 * 
 * @author Administrator justson
 * 
 */
public class HttpManager {

	private OkHttpClient mOkHttpClient;
	public static HttpManager mHttpManager;
	private String defPath = "";
	private Handler handler;

	private HttpManager() throws IOException {
		mOkHttpClient = new OkHttpClient();
		defPath = getPath();
		if (defPath == null) {
//			throw new RuntimeException("you sd Card is null , down load fail");
			defPath=Environment.getDataDirectory().getAbsolutePath();
		}
		//初始化一个handler 用于异步下载上传后回传到主线程
		handler = new Handler(Looper.getMainLooper());
	}
	/**
	 * 获取默认的路径
	 * 
	 * @return
	 * @throws IOException
	 */
	private String getPath() throws IOException {
		String path = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			String sdPath = Environment.getExternalStorageDirectory()
					.getCanonicalPath().toString()
					+ File.separator + "syn";
			File file = new File(sdPath);
			if (!file.exists()) {
				file.mkdir();
			}

			path = file.getAbsolutePath().toString().trim();
		}

		Log.i("Info", "获取路径");
		return path;
	}

	/**
	 * 异步post方式上传文件
	 * 
	 * @param path
	 * @param url
	 * @param mOkHttpCallBack
	 * @param userName
	 */
	private void asnyupLoading(String path, String url,
			final OkHttpCallBack<String> mOkHttpCallBack, String userName,
			int number) {

		File file = new File(path);
		long time = System.currentTimeMillis();
		Log.i("Info", "time" + time);
		RequestBody mRequestBody = new MultipartBuilder()
				.addPart(
						Headers.of("Content-disposition",
								"form-data;name=\"number\""),
						RequestBody.create(null, number + ""))
				//
				.addPart(
						Headers.of("Content-disposition",
								"form-data;name=\"userName\""),
						RequestBody.create(null, userName))
				.addFormDataPart(
						"" + time,
						userName + "_" + file.getName().toString(),
						RequestBody.create(
								MediaType.parse(getFileType(file.getName())),
								file)).build();
		Request request = new Request.Builder().url(url)//
				.post(mRequestBody)//
				.build();
		mOkHttpClient.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Response response) throws IOException {

				if (!response.isSuccessful())
					return;

				sendToMainThread(mOkHttpCallBack, response);

			}

			@Override
			public void onFailure(Request request, IOException io) {

				mOkHttpCallBack.handleFail("上传失败");

			}
		});

	}
	/**
	 *  post 方式下载指定的文件
	 * @param url
	 * @param mTaskBean
	 * @param taskName
	 * @param mOkHttpCallBack
	 */
	private void asynPointTargetDown(String url,TaskBean mTaskBean,String taskName,final OkHttpCallBack<String>mOkHttpCallBack)
	{
		if(url==null||url.equals("")||mTaskBean==null||mOkHttpCallBack==null)
			return;
		Gson mGson=new Gson();
		String json=mGson.toJson(mTaskBean);
		RequestBody mRequestBody=new FormEncodingBuilder().add(taskName, json).build();
		Request mRequest=new Request.Builder().url(url).post(mRequestBody).build();
		mOkHttpClient.newCall(mRequest).enqueue(new Callback() {
			
			@Override
			public void onResponse(Response response) throws IOException {
				
				if(response.isSuccessful())
				{
					
					InputStream is=response.body().byteStream();
					if(is==null)return;
					int len=0;
					byte[]by=new byte[1024];
					Long time=System.currentTimeMillis();
					String path_=defPath+File.separator+time+".txt";
					File file=new File(path_);
					OutputStream os=new FileOutputStream(file);
					while((len=is.read(by))!=-1){
						
						os.write(by, 0, len);
						
					}
					if(is!=null)
						is.close();
					if(os!=null)
						os.close();
					
				  sendToMainThread_Str(mOkHttpCallBack, path_);
					
				}
				
			}
			
			@Override
			public void onFailure(Request request, IOException arg1) {
				
			}
		});
		
	}

	/**
	 * 发送字符串到主线程
	 * @param mOkHttpCallBack
	 * @param result
	 * @throws IOException
	 */
	private void sendToMainThread_Str(
			final OkHttpCallBack<String> mOkHttpCallBack, final String result)
			throws IOException {
		// TODO Auto-generated method stub

		if (mOkHttpCallBack == null)
			return;

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				mOkHttpCallBack.handleSucess(result);
			}
		}, 2000);

	}

	/**
	 * 传入UI线程
	 * 
	 * @param mOkHttpCallBack
	 * @param response
	 * @throws IOException
	 */
	private void sendToMainThread(final OkHttpCallBack<String> mOkHttpCallBack,
			final Response response) throws IOException {
		// TODO Auto-generated method stub

		if (mOkHttpCallBack == null)
			return;

		final String json = response.body().string();

		Log.i("Info", "Json:" + json);
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				mOkHttpCallBack.handleSucess(json);
			}
		}, 2000);

	}

	// 传入文件的全名获取文件的类型
	private String getFileType(String name) {

		FileNameMap mFileNameMap = URLConnection.getFileNameMap();
		String contentType = mFileNameMap.getContentTypeFor(name);
		if (contentType == null) {
			return contentType = "application/octet-stream";
		}

		Log.i("Info", "type:" + contentType);

		return contentType;

	}

	/**
	 * post方式异步下载
	 * 
	 * @param url
	 * @param mOkHttpCallBack
	 * @return
	 */
	private void asnyDownLoading(final String userName, String url,
			final OkHttpCallBack<String> mOkHttpCallBack) {
		//
		// RequestBody mRequestBody=new MultipartBuilder().addPart
		// (Headers.of("Content-position","data-form;name=\"userName\""),
		// RequestBody.create(null, userName)).build();
		// 利用表单上传字符串对
		FormEncodingBuilder mFormEncodingBuilder = new FormEncodingBuilder()
				.add("userName", userName);

		Log.i("Info", "asnydown");
		Request mQuest = new Request.Builder().url(url)
				.post(mFormEncodingBuilder.build()).build();
		mOkHttpClient.newCall(mQuest).enqueue(new Callback() {

			@Override
			public void onResponse(Response response) throws IOException {

				if (response.isSuccessful()) {
					long time;
					InputStream is = response.body().byteStream();
					if (is != null) {
						Log.i("Info", "开始下载");
						byte[] by = new byte[1024];
						time = System.currentTimeMillis();
						Log.i("Info", "path:" + defPath + File.separator + time
								+ ".txt");
						File file = new File(defPath + File.separator + time
								+ ".txt");
						// file.createNewFile();
						OutputStream os = new FileOutputStream(file);
						int len = 0;
						while ((len = is.read(by)) != -1) {
							os.write(by, 0, len);
						}

						if (is != null) {
							is.close();
						}
						if (os != null) {
							os.close();
						}

						sendToMainThread_Str(mOkHttpCallBack, defPath
								+ File.separator + time + ".txt");

					} else {
						Log.i("Info", "回调失败");
						mOkHttpCallBack.handleFail(null);
					}

				}
				else
				{
					
				}

			}

			@Override
			public void onFailure(Request request, IOException io) {
				
				Log.i("Info", "fail on");
				mOkHttpCallBack.handleFail(null);
				
			}
		});

	}

	/**
	 * 用异步的post执行访问
	 * 
	 * @throws IOException
	 */
	private void synLogin(String userName, String password, String phone,
			String url, final OkHttpCallBack<String> mOkHttpCallBack)
			throws IOException {

		FormEncodingBuilder mFormEncodingBuilder = new FormEncodingBuilder();
		mFormEncodingBuilder.add("userName", userName);
		mFormEncodingBuilder.add("password", password);
		mFormEncodingBuilder.add("phone", phone);

		Request mRequest = new Request.Builder().url(url)//
				.post(mFormEncodingBuilder.build()).build();
		  mOkHttpClient.newCall(mRequest).enqueue(new Callback() {
			
			@Override
			public void onResponse(Response mResponse) throws IOException {
				
				
				if (mResponse.isSuccessful()) {

					String json = mResponse.body().string();
					
					Log.i("Info", "成功");
//					mOkHttpCallBack.handleSucess(json);
					sendToMainThread_Str(mOkHttpCallBack, json);
				}
				
			}
			
			@Override
			public void onFailure(Request request, IOException arg1) {
				
			}
		});
		

		
	}
/**
 * 异步提交信息给服务器
 */	 
	private void asynSubmitMessage(TaskBean mTaskBean,String url,final OkHttpCallBack<String>mOkHttpCallBack,String taskBeanName)
	{
		
		Log.i("Info", "转化字符串");
		Gson mGson=new Gson();
		String json=mGson.toJson(mTaskBean);
		RequestBody mRequestBody=new FormEncodingBuilder().add(taskBeanName, json).build();
		Request mRequest=new Request.Builder().url(url)//
				.post(mRequestBody).build();
		mOkHttpClient.newCall(mRequest).enqueue(new Callback() {
			
			@Override
			public void onResponse(Response response) throws IOException {
				
				if(response.isSuccessful())
				{
					String json=response.body().string();
					sendToMainThread_Str(mOkHttpCallBack, json);
				}
				
			}
			
			@Override
			public void onFailure(Request request, IOException arg1) {
				
				Log.i("Info", "发送失败");
			}
		});
		
		mGson=null;
	}

//----------------------------------------------------------------------------------------------------------------------------//
	public void asnyDwonLaodingForOut(String userName, String url,
			OkHttpCallBack mOkHttpCallBack) {
		this.asnyDownLoading(userName, url, mOkHttpCallBack);
	}

	
	public void asynPointTargetDownx(String url,TaskBean mTaskBean,String taskName,final OkHttpCallBack<String>mOkHttpCallBack){
		this.asynPointTargetDown(url, mTaskBean, taskName, mOkHttpCallBack);
	}
	
	public void asnyUpLoadingForOut(String path, String url,
			OkHttpCallBack mOkHttpCallBack_, String userName, int number) {
		this.asnyupLoading(path, url, mOkHttpCallBack_, userName, number);
	}

	public void synLogin_(String userName, String password, String phone,
			String url, final OkHttpCallBack<String> mOkHttpCallBack) throws IOException {
		this.synLogin(userName, password, phone, url, mOkHttpCallBack);
	}
	public void asynSubmitMessageToService(TaskBean mTaskBean,String url,final OkHttpCallBack<String>mOkHttpCallBack,String taskBeanName)
	{
		this.asynSubmitMessage(mTaskBean, url, mOkHttpCallBack, taskBeanName);
	}

	/**
	 * 这里做了双重判断加锁，防止并发多次new而且保证性能
	 * 
	 * @return 单例 HttpManager
	 */
	public static HttpManager getInstance() {
		if (mHttpManager == null) {
			synchronized (HttpManager.class) {

				try {
					if(mHttpManager==null)
					{
						mHttpManager = new HttpManager();
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return mHttpManager;
	}

	public interface OkHttpCallBack<T> {
		void handleSucess(T t);

		void handleFail(T t);
	}

}
