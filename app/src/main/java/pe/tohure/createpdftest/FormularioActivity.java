package pe.tohure.createpdftest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class FormularioActivity extends AppCompatActivity {

    private EditText edtHemoglobina;
    private EditText edtTrigliceridos;
    private EditText edtGlucosa;
    private EditText edtColesterol;
    private EditText edtEdad;
    private EditText edtPeso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        init();
    }

    private void init() {
        edtHemoglobina = (EditText) findViewById(R.id.edtHemoglobina);
        edtTrigliceridos = (EditText) findViewById(R.id.edtTrigliceridos);
        edtGlucosa = (EditText) findViewById(R.id.edtGlucosa);
        edtColesterol = (EditText) findViewById(R.id.edtColesterol);
        edtEdad = (EditText) findViewById(R.id.edtEdad);
        edtPeso = (EditText) findViewById(R.id.edtPeso);
    }

    public void previewForm(View view) {
        Report report = new Report();

        if (!edtHemoglobina.getText().toString().isEmpty()) {
            report.setHemoglobina(edtHemoglobina.getText().toString());
        }

        if (!edtTrigliceridos.getText().toString().isEmpty()) {
            report.setTrigliceridos(edtTrigliceridos.getText().toString());
        }

        if (!edtGlucosa.getText().toString().isEmpty()) {
            report.setGlucosa(edtGlucosa.getText().toString());
        }

        if (!edtColesterol.getText().toString().isEmpty()) {
            report.setColesterol(edtColesterol.getText().toString());
        }

        if (!edtEdad.getText().toString().isEmpty()) {
            report.setEdad(edtEdad.getText().toString());
        }

        if (!edtPeso.getText().toString().isEmpty()) {
            report.setPeso(edtPeso.getText().toString());
        }

        Intent intent = new Intent(this, PreviewActivity.class);
        intent.putExtra("report", report);
        startActivity(intent);
    }
}
