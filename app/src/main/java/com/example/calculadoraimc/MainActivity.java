package com.example.calculadoraimc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText idadeInput, alturaInput, pesoInput;
    private Button calcularButton;
    private TextView resultadoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        idadeInput = findViewById(R.id.idadeInput);
        alturaInput = findViewById(R.id.alturaInput);
        pesoInput = findViewById(R.id.pesoInput);
        calcularButton = findViewById(R.id.calcularButton);
        resultadoView = findViewById(R.id.resultadoView);

        calcularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularIMC();
            }
        });
    }

    private void calcularIMC() {
        String alturaTexto = alturaInput.getText().toString();
        String pesoTexto = pesoInput.getText().toString();

        if (alturaTexto.isEmpty() || pesoTexto.isEmpty()) {
            Toast.makeText(this, "Por favor, insira valores válidos para peso e altura", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            float altura = Float.parseFloat(alturaTexto) / 100; // Converte para metros
            float peso = Float.parseFloat(pesoTexto);

            float imc = peso / (altura * altura);

            // faixas IMC
            String faixaPeso;
            if (imc < 18.5) {
                faixaPeso = "Magreza (menor que 18.5)";
            } else if (imc >= 18.5 && imc <= 24.9) {
                faixaPeso = "Normal (entre 18.5 a 24.9)";
            } else if (imc >= 25 && imc <= 29.9) {
                faixaPeso = "Sobrepeso (entre 25 a 29.9)";
            } else {
                faixaPeso = "Obesidade (maior igual a 30.0)";
            }

            // Seta resultado
            resultadoView.setText(String.format("Seu IMC é: %.2f kg/m²\nFaixa de Peso: %s", imc, faixaPeso));
            resultadoView.setVisibility(View.VISIBLE);

        } catch (NumberFormatException e) { // catch de erro de dado
            Toast.makeText(this, "Valores inválidos inseridos.", Toast.LENGTH_SHORT).show();
        }
    }
}
