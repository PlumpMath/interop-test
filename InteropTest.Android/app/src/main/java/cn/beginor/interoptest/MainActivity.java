package cn.beginor.interoptest;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.web_view);
        WebSettings settings = webView.getSettings();
        //settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setJavaScriptEnabled(true);
        //settings.setBuiltInZoomControls(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //webView.getSettings().setAllowContentAccess(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //boolean base = super.shouldOverrideUrlLoading(view, url);
                view.loadUrl(url);
                return true;
            }
        });
        webView.addJavascriptInterface(new JavascriptObject(this), "interop");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                getSupportActionBar().setTitle(title);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        webView.loadUrl("http://10.0.2.2:8088/interop/");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_refresh) {
            webView.reload();
        }
        return true;
    }

    class JavascriptObject {

        Context context;

        public JavascriptObject(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public String helloWorld() {
            return "hello, world!";
        }

        @JavascriptInterface
        public void showToast(String message) {
            Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
        }
    }
}
