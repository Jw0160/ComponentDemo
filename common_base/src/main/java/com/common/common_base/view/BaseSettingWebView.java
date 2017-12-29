package com.common.common_base.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.MimeTypeMap;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.LogUtils;
import com.common.common_base.utils.KLog;
import com.common.common_base.utils.network.NetworkUtils;

import java.io.InputStream;

/**
 * @author : wangb
 * @email :
 * @date : 2017/12/28
 * @desc :
 */

public class BaseSettingWebView extends WebView{
    private Context mContext;
    private final static String TAG = "BaseSettingWebView";
    private ProgressBar mProgressBar;

    public BaseSettingWebView(Context context){
        super(context);
        mContext = context;
        init();
    }

    public BaseSettingWebView(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    public BaseSettingWebView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        init();
    }

    public BaseSettingWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        setWebChromeClient(new MyChromeWebView());
        setWebViewClient(new MyWebViewClient());
        WebSettings settings = this.getSettings();
        // 缓存(cache)
        settings.setAppCacheEnabled(true);      // 默认值 false
        settings.setAppCachePath(mContext.getCacheDir().getAbsolutePath());
        // 存储(storage)
        settings.setDomStorageEnabled(true);    // 默认值 false
        settings.setDatabaseEnabled(true);      // 默认值 false
        // 资源加载
        settings.setLoadsImagesAutomatically(true); // 是否自动加载图片
        settings.setBlockNetworkImage(false);       // 禁止加载网络图片
        settings.setBlockNetworkLoads(false);       // 禁止加载所有网络资源
        // 缩放(zoom)
        settings.setSupportZoom(false);          // 是否支持缩放
        settings.setBuiltInZoomControls(true); // 是否使用内置缩放机制
        settings.setDisplayZoomControls(false);  // 是否显示内置缩放控件
        // 是否支持viewport属性，默认值 false
        // 页面通过`<meta name="viewport" ... />`自适应手机屏幕
        settings.setUseWideViewPort(true);
        // 是否使用overview mode加载页面，默认值 false
        // 当页面宽度大于WebView宽度时，缩小使页面宽度等于WebView宽度
        settings.setLoadWithOverviewMode(true);
        // 是否支持Javascript，默认值false
        settings.setJavaScriptEnabled(true);
        // 布局算法
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            // 5.0以上允许加载http和https混合的页面(5.0以下默认允许，5.0+默认禁止)
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if(NetworkUtils.isConnected(mContext)){
            // 根据cache-control决定是否从网络上取数据
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }else{
            // 没网，离线加载，优先加载缓存(即使已经过期)
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
    }

    private static class MyChromeWebView extends WebChromeClient{

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback){
            super.onShowCustomView(view, callback);
        }

        @Override
        public void onHideCustomView(){
            super.onHideCustomView();
        }

        // 接收当前页面的加载进度
        @Override
        public void onProgressChanged(WebView view, int newProgress){
            LogUtils.d(TAG, "onProgressChanged() called with: ");
        }

        // 接收文档标题
        @Override
        public void onReceivedTitle(WebView view, String title){
            LogUtils.d(TAG, "onReceivedTitle() called with: ");
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result){
            LogUtils.d(TAG, "onJsAlert() called with: message:" + message);
            return true;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result){
            LogUtils.d(TAG, "onJsConfirm() called with: message:" + message);
            return super.onJsConfirm(view, url, message, result);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result){
            LogUtils.d(TAG, "onJsConfirm() called with: message:" + message);
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }
    }

    private class MyWebViewClient extends WebViewClient{
        // 拦截页面加载，返回true表示宿主app拦截并处理了该url，否则返回false由当前WebView处理
        // 此方法在API24被废弃，不处理POST请求
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            LogUtils.d(TAG, "shouldOverrideUrlLoading() called with: ");
            return false;
        }

        // 拦截页面加载，返回true表示宿主app拦截并处理了该url，否则返回false由当前WebView处理
        // 此方法添加于API24，不处理POST请求，可拦截处理子frame的非http请求
        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
            String lUrl = request.getUrl().toString();
            return shouldOverrideUrlLoading(view, lUrl);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request){
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url){
            return preload("assets/", url);
        }

        WebResourceResponse preload(String path, String url){
            if(!url.contains(path)){
                return null;
            }
            String local = url.replaceFirst("^http.*" + path, "");
            try{
                InputStream is = mContext.getAssets().open(local);
                String ext = MimeTypeMap.getFileExtensionFromUrl(local);
                String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext);
                return new WebResourceResponse(mimeType, "UTF-8", is);
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }

        // 页面(url)开始加载
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon){
            KLog.d(TAG, "onPageStarted() called with: ");
        }

        // 页面(url)完成加载
        @Override
        public void onPageFinished(WebView view, String url){
            LogUtils.d(TAG, "onPageFinished() called with: ");
            if(mProgressBar != null){
                mProgressBar.setVisibility(GONE);
            }
        }

        // 将要加载资源(url)
        @Override
        public void onLoadResource(WebView view, String url){
            LogUtils.d(TAG, "onLoadResource() called with: ");
        }

        // 加载资源时发生了一个SSL错误，应用必需响应(继续请求或取消请求)
        // 处理决策可能被缓存用于后续的请求，默认行为是取消请求
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
//                        handler.cancel();    //取消请求  默认的处理方式，WebView变成空白页
            handler.proceed(); //继续请求  接受证书
        }

        // 此方法添加于API23
        // 加载资源时出错，通常意味着连接不到服务器
        // 由于所有资源加载错误都会调用此方法，所以此方法应尽量逻辑简单
        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error){
            super.onReceivedError(view, request, error);
        }

        // 通知应用页面缩放系数变化
        @Override
        public void onScaleChanged(WebView view, float oldScale, float newScale){
            super.onScaleChanged(view, oldScale, newScale);
            /**
             * requestFocus() 方法在三种情况下获取焦点不能生效。 http://blog.csdn.net/wangbole/article/details/45488787
             1）对应的View不支持Focus;
             2) 对应的View支持Focus，但是不支持在Touch模式下的Focus;
             3) 对应的View其祖先View 设置了FOCUS_BLOCK_DESCENDANTS 标志， 阻止其子View获取焦点。
             而requestFocusFromTouch() 方法设计的目的就是解决requestFocus() 在第二种不能获得焦点的情况下，Touch模式下不支持焦点，也能够获得焦点使用的。
             */
            BaseSettingWebView.this.requestFocus();
            BaseSettingWebView.this.requestFocusFromTouch();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(canGoBack()){
                goBack();
                return true;
            }
        }else if(keyCode == KeyEvent.KEYCODE_FORWARD){
            if(canGoForward()){
                goForward();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void loadUrl(String url, View view){
        loadUrl(url);
        mProgressBar = (ProgressBar) view;
    }
}
