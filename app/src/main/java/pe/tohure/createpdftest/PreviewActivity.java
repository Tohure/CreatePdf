package pe.tohure.createpdftest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PreviewActivity extends AppCompatActivity {

    private Report report;
    private String appFolder = "/createPdfApp";
    private LinearLayout lnlContent;
    private ProgressBar progressBar;

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        init();
    }

    private void init() {

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("report")) {
            report = (Report) intent.getSerializableExtra("report");
        }

        lnlContent = (LinearLayout) findViewById(R.id.lnlContent);
        TextView lblHemoglobina = (TextView) findViewById(R.id.lblHemoglobina);
        TextView lblTrigliceridos = (TextView) findViewById(R.id.lblTrigliceridos);
        TextView lblGlucosa = (TextView) findViewById(R.id.lblGlucosa);
        TextView lblColesterol = (TextView) findViewById(R.id.lblColesterol);
        TextView lblEdad = (TextView) findViewById(R.id.lblEdad);
        TextView lblPeso = (TextView) findViewById(R.id.lblPeso);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (report != null) {
            lblHemoglobina.setText(report.getHemoglobina());
            lblTrigliceridos.setText(report.getTrigliceridos());
            lblGlucosa.setText(report.getGlucosa());
            lblColesterol.setText(report.getColesterol());
            lblEdad.setText(report.getEdad());
            lblPeso.setText(report.getPeso());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.preview_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_generate_pdf) {
            progressBar.setVisibility(View.VISIBLE);
            bitmap = bitmapFromView(lnlContent);
            createPdf();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void createPdf() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels;
        float width = displaymetrics.widthPixels;

        int convertHighet = (int) hight, convertWidth = (int) width;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

        // Start to write pdf file
        File folderDirectory = new File(Environment.getExternalStorageDirectory(), appFolder);
        boolean success = true;
        if (!folderDirectory.exists()) {
            success = folderDirectory.mkdir();
        }

        if (success) {
            String targetPdf = appFolder + "/test.pdf";
            File filePath = new File(Environment.getExternalStorageDirectory(), targetPdf);

            try {
                document.writeTo(new FileOutputStream(filePath));
                viewPdfFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Something wrong creatig folder ", Toast.LENGTH_LONG).show();
        }

        // close pdf file
        document.close();
    }

    private void viewPdfFile(File file) {
        progressBar.setVisibility(View.GONE);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        Uri apkURI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
        intent.setDataAndType(apkURI, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivity(intent);
        Toast.makeText(this, "Pdf Generado Correctamente!!!", Toast.LENGTH_SHORT).show();
    }


    public static Bitmap bitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }
}
