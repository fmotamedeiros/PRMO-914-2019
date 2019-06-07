package br.com.cpsoftware.webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebView2Activity extends AppCompatActivity {

  private WebView webview;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_web_view2);

    webview = (WebView) findViewById(R.id.webview);

    String html = "<html><body><h1>Olá mundo!</h1></body></html>";
    webview.loadData(html, "text/html", "UTF-8");

  }
}
