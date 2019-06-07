package br.com.cpsoftware.webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class WebView1Activity extends AppCompatActivity {

  private WebView webview;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_web_view1);

    webview = (WebView) findViewById(R.id.webview);

    // Habilitar JS e Zoom
    webview.getSettings().setJavaScriptEnabled(true);
    webview.getSettings().setBuiltInZoomControls(true);

    // Ajustar ao tamanho da tela do celular
    webview.getSettings().setLoadWithOverviewMode(true);
    webview.getSettings().setUseWideViewPort(true);

    // Carregar URL na webview
    webview.loadUrl("https://www.google.com.br");

    // Adicionar barra de rolagem
    webview.setWebChromeClient(new WebChromeClient(){
      @Override
      public void onProgressChanged(WebView view, int newProgress) {
        setProgress(newProgress * 100);
      }
    });

  }
}
