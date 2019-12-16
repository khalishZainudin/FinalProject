package my.edu.fsktm.um.finalproject.CompatibilityPage;

import androidx.appcompat.app.AppCompatActivity;

import my.edu.fsktm.um.finalproject.R;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.muddzdev.styleabletoast.StyleableToast;

public class CompatibleMainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Proc[] processor = new Proc[100];
    Mobo[] motherboard = new Mobo[100];
    String toastCompatible = "COMPATIBLE";
    String toastNotCompatible = "NOT COMPATIBLE";
    int redColor = Color.parseColor("#FF5A5F");
    int greenColor = Color.parseColor("#00c976");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compatible_main);

        processor[0] = new Proc("AMD Ryzen5 2600", 65);
        processor[1] = new Proc("AMD Ryzen5 3600", 65);
        processor[2] = new Proc("AMD Ryzen7 2700", 65);
        processor[3] = new Proc("AMD Ryzen9 3900X", 105);
        processor[4] = new Proc("Intel i5 9400F", 65);
        processor[5] = new Proc("Intel i7 8700K", 95);

        motherboard[0] = new Mobo("MSI B450 TOMAHAWK", 70);
        motherboard[1] = new Mobo("MSI B450M GAMING PLUS", 60);
        motherboard[2] = new Mobo("Gigabyte B450 AORUS M", 60);
        motherboard[3] = new Mobo("ASRock X570M Pro4", 60);
        motherboard[4] = new Mobo("MSI B360m MORTAR", 60);
        motherboard[5] = new Mobo("GIGABYTE Z370", 70);

        Spinner spinnerM = findViewById(R.id.spinnerMobo);
        spinnerM.setPrompt("Choose a Motherboard");
        ArrayAdapter<CharSequence> adapterM = ArrayAdapter.createFromResource(this, R.array.mobo_arrays, android.R.layout.simple_spinner_item);
        adapterM.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerM.setAdapter(adapterM);
        spinnerM.setOnItemSelectedListener(this);

        Spinner spinnerP = findViewById(R.id.spinnerProc);
        spinnerP.setPrompt("Choose a Processor");
        ArrayAdapter<CharSequence> adapterP = ArrayAdapter.createFromResource(this, R.array.proc_arrays, android.R.layout.simple_spinner_item);
        adapterP.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerP.setAdapter(adapterP);
        spinnerP.setOnItemSelectedListener(this);

        TextView sumWatt = findViewById(R.id.sum);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        int procValue = 0, moboValue = 0, i = 0;
        int sum = 0;
        int a=0,b=0;
        String proc = " ", mobo =" ";


        Spinner spinnerP = findViewById(R.id.spinnerProc);
        Spinner spinnerM = findViewById(R.id.spinnerMobo);
        TextView sumWatt = findViewById(R.id.sum);


        while(processor[i]!=null) {
            String text1 = spinnerP.getSelectedItem().toString();
            if(text1.equals(processor[i].name)){
                procValue=processor[i].value;
                proc=processor[i].name;
                break;
            }
            i++;
        }

        while(motherboard[i]!=null) {
            String text2 = spinnerM.getSelectedItem().toString();
            if(text2.equals(motherboard[i].name)){
                moboValue=motherboard[i].value;
                mobo=motherboard[i].name;
                break;
            }
            i++;
        }

        if (proc.equals(processor[0].name)&&mobo.equals(motherboard[0].name)||mobo.equals(motherboard[1].name)||mobo.equals(motherboard[2].name)){
            showToastCompatible(view);;
        }else if (proc.equals(processor[1].name)&&mobo.equals(motherboard[0].name)||mobo.equals(motherboard[1].name)||mobo.equals(motherboard[2].name)){
            showToastCompatible(view);
        }else if (proc.equals(processor[2].name)&&mobo.equals(motherboard[0].name)||mobo.equals(motherboard[1].name)||mobo.equals(motherboard[2].name)){
            showToastCompatible(view);
        }else if (proc.equals(processor[3].name)&&mobo.equals(motherboard[3].name)){
            showToastCompatible(view);
        }else if (proc.equals(processor[4].name)&&mobo.equals(motherboard[4].name)){
            showToastCompatible(view);
        }else if (proc.equals(processor[5].name)&&mobo.equals(motherboard[5].name)){
            showToastCompatible(view);
        }else{
            showToastIncompatible(view);
        }

        System.out.println(procValue);
        System.out.println(moboValue);
        System.out.println(procValue+moboValue);
        int add = procValue+moboValue;

        sumWatt.setText("Expected Voltage Consumption: "+add+" WATT");
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void showToastCompatible(View v){
        new StyleableToast.Builder(this)
                .text(toastCompatible)
                .backgroundColor(greenColor)
                .iconStart(R.drawable.ic_compatible)
                .textBold()
                .show();
    }

    public void showToastIncompatible(View v){
        new StyleableToast.Builder(this)
                .text(toastNotCompatible)
                .backgroundColor(redColor)
                .iconStart(R.drawable.ic_incompatible)
                .textBold()
                .show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
