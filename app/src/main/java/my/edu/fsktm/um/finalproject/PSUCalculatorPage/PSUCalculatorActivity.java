package my.edu.fsktm.um.finalproject.PSUCalculatorPage;

import androidx.appcompat.app.AppCompatActivity;
import my.edu.fsktm.um.finalproject.R;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class PSUCalculatorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    cpu[] cpus=new cpu[130];
    motherboard[] motherboards=new motherboard[110];
    RAM[] memory=new RAM[90];
    SSD[] ssds =new SSD[50];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psucalculator);

        cpus[0]=new cpu("Intel",80);
        cpus[1]=new cpu("Qualcomm",100);
        cpus[2]=new cpu("NVDIA",110);
        cpus[3]=new cpu("Core i3-2100",120);
        cpus[4]=new cpu("Core3 Duo E7200",90);

        motherboards[0]=new motherboard("ATX",65);
        motherboards[1]=new motherboard("SSI CEB",70);
        motherboards[2]=new motherboard("Micro ATX",75);
        motherboards[3]=new motherboard("XL ATX",95);
        motherboards[4]=new motherboard("SSI EEB",105);

        memory[0]=new RAM("4GB",10);
        memory[1]=new RAM("8GB",15);
        memory[2]=new RAM("16GB",20);
        memory[3]=new RAM("32GB",25);
        memory[4]=new RAM("64GB",30);

        ssds[0]=new SSD("Under 120GB",7);
        ssds[1]=new SSD("120GB-256GB",10);
        ssds[2]=new SSD("56GB-512GB",15);
        ssds[3]=new SSD("512GB- 1T",20);
        ssds[4]=new SSD("2T",30);


        Spinner m= findViewById(R.id.spinnermotherboard);
        m.setPrompt("Choose a Motherboard");
        ArrayAdapter<CharSequence> adapterM = ArrayAdapter.createFromResource(this, R.array.Motherboard, android.R.layout.simple_spinner_item);
        adapterM.setDropDownViewResource(android.R.layout.simple_spinner_item);
        m.setAdapter(adapterM);
        m.setOnItemSelectedListener(this);

        Spinner c=findViewById(R.id.spincpu);
        c.setPrompt("Choose a CPU");
        ArrayAdapter<CharSequence> adapterC=ArrayAdapter.createFromResource(this, R.array.CPU, android.R.layout.simple_spinner_item );
        adapterC.setDropDownViewResource(android.R.layout.simple_spinner_item);
        c.setAdapter(adapterC);
        c.setOnItemSelectedListener(this);

        Spinner r=findViewById(R.id.spinnerRAM);
        r.setPrompt("Choose a RAM");
        ArrayAdapter<CharSequence> adapterR=ArrayAdapter.createFromResource(this, R.array.RAM, android.R.layout.simple_spinner_item );
        adapterR.setDropDownViewResource(android.R.layout.simple_spinner_item);
        r.setAdapter(adapterR);
        r.setOnItemSelectedListener(this);

        Spinner ss=findViewById(R.id.spinnerSSD);
        ss.setPrompt("Choose a SSD");
        ArrayAdapter<CharSequence> adapterSS=ArrayAdapter.createFromResource(this, R.array.SSD, android.R.layout.simple_spinner_item );
        adapterSS.setDropDownViewResource(android.R.layout.simple_spinner_item);
        ss.setAdapter(adapterSS);
        ss.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){

        int CPUValue=cpus[0].value;
        int motherboardValue=motherboards[0].value;
        int RAMValue=memory[0].value;
        int SSDValue=ssds[0].value;

        int i = 0;

        Spinner c = findViewById(R.id.spincpu);
        Spinner m = findViewById(R.id.spinnermotherboard);
        Spinner r = findViewById(R.id.spinnerRAM);
        Spinner ss = findViewById(R.id.spinnerSSD);
        TextView sumWatt = findViewById(R.id.wattage);

        while(cpus[i]!=null) {
            String text1 = c.getSelectedItem().toString();
            if(text1.equals(cpus[i].name)){
                CPUValue=cpus[i].value;
                break;
            }
            i++;
        }

        while(motherboards[i]!=null) {
            String text2 = m.getSelectedItem().toString();
            if(text2.equals(motherboards[i].name)){
                motherboardValue=motherboards[i].value;
                break;
            }
            i++;
        }

        while(memory[i]!=null) {
            String text3 = r.getSelectedItem().toString();
            if(text3.equals(memory[i].name)){
                RAMValue=memory[i].value;
                break;
            }
            i++;
        }

        while(ssds[i]!=null) {
            String text4 = ss.getSelectedItem().toString();
            if(text4.equals(ssds[i].name)){
                SSDValue=ssds[i].value;
                break;
            }
            i++;
        }

        int add = CPUValue + motherboardValue + RAMValue + SSDValue ;

        sumWatt.setText("Your Recommended PSU Wattage is: "+add+" WATT");
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
