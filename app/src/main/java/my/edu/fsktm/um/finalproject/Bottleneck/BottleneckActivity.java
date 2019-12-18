package my.edu.fsktm.um.finalproject.Bottleneck;

import androidx.appcompat.app.AppCompatActivity;
import my.edu.fsktm.um.finalproject.R;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class BottleneckActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Cpu[] cpu = new Cpu[50];
    Gpu[] gpu = new Gpu[50];
    Ram[] ram = new Ram[50];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottleneck);

        cpu[0] = new Cpu("Intel core I3 3240", 62);
        cpu[1] = new Cpu("Intel Core I7 3770k", 94);
        cpu[2] = new Cpu("Intel Core I9 7990", 98);

        gpu[0] = new Gpu("NVIDIA GeForce 600 Series", 70);
        gpu[1] = new Gpu("NVIDIA RTX 2000 Series", 90);
        gpu[2] = new Gpu("NVIDIA Quadro", 80);

        ram[0] = new Ram("4 GB", 4);
        ram[1] = new Ram("8 GB", 8);
        ram[2] = new Ram("12 GB", 12);
        ram[3] = new Ram("32 GB", 32);

        Spinner cpuSpinner = findViewById(R.id.cpuSpinner);
        cpuSpinner.setPrompt("Choose a CPU");
        ArrayAdapter<CharSequence> adapterCpu = ArrayAdapter.createFromResource(this, R.array.cpu_arrays, android.R.layout.simple_spinner_item);
        adapterCpu.setDropDownViewResource(android.R.layout.simple_spinner_item);
        cpuSpinner.setAdapter(adapterCpu);
        cpuSpinner.setOnItemSelectedListener(this);

        Spinner gpuSpinner = findViewById(R.id.gpuSpinner);
        gpuSpinner.setPrompt("Choose a Processor");
        ArrayAdapter<CharSequence> adapterGpu = ArrayAdapter.createFromResource(this, R.array.gpu_arrays, android.R.layout.simple_spinner_item);
        adapterGpu.setDropDownViewResource(android.R.layout.simple_spinner_item);
        gpuSpinner.setAdapter(adapterGpu);
        gpuSpinner.setOnItemSelectedListener(this);

        Spinner ramSpinner = findViewById(R.id.ramSpinner);
        gpuSpinner.setPrompt("Choose a RAM Memory");
        ArrayAdapter<CharSequence> adapterRam = ArrayAdapter.createFromResource(this, R.array.ram_arrays, android.R.layout.simple_spinner_item);
        adapterRam.setDropDownViewResource(android.R.layout.simple_spinner_item);
        ramSpinner.setAdapter(adapterRam);
        ramSpinner.setOnItemSelectedListener(this);

        TextView resultTv = findViewById(R.id.resultTv);
    }

    //@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        int cpuValue = 0, gpuValue = 0, ramValue = 0, i = 0;
        int sum = 0;
        int a=0, b=0;
        String cpu1 = " ", gpu1 =" ", ram1 = "";


        Spinner cpuSpinner = findViewById(R.id.cpuSpinner);
        Spinner gpuSpinner = findViewById(R.id.gpuSpinner);
        Spinner ramSpinner = findViewById(R.id.ramSpinner);
        TextView resultTv = findViewById(R.id.resultTv);


        while(cpu[i] != null) {
            String text1 = cpuSpinner.getSelectedItem().toString();
            if(text1.equals(cpu[i].name)){
                cpuValue=cpu[i].value;
                cpu1=cpu[i].name;
                break;
            }
            i++;
        }

        while(gpu[i]!=null) {
            String text2 = gpuSpinner.getSelectedItem().toString();
            if(text2.equals(gpu[i].name)){
                gpuValue=gpu[i].value;
                gpu1=gpu[i].name;
                break;
            }
            i++;
        }

        while(ram[i]!=null) {
            String text3 = gpuSpinner.getSelectedItem().toString();
            if(text3.equals(ram[i].name)){
                ramValue=ram[i].value;
                ram1=ram[i].name;
                break;
            }
            i++;
        }

        /*if (cpu.equals(cpu[0].name)&&gpu.equals(gpu[0].name)||cpu.equals(cpu[1].name)||gpu.equals(gpu[2].name)){
            showToastCompatible(view);;
        }else if (cpu.equals(cpu[1].name)&&gpu.equals(gpu[0].name)||cpu.equals(cpu[1].name)||gpu.equals(gpu[2].name)){
            showToastCompatible(view);
        }else if (cpu.equals(cpu[2].name)&&gpu.equals(gpu[0].name)||cpu.equals(cpu[1].name)||gpu.equals(gpu[2].name)){
            showToastCompatible(view);
        }else{
            showToastIncompatible(view);
        }*/

        System.out.println(cpuValue);
        System.out.println(gpuValue);
        System.out.println(ramValue);
        System.out.println(cpuValue+gpuValue+ramValue);
        int add = cpuValue+gpuValue+ramValue;

        //int percent = (add/90)*100;
        resultTv.setText("Bottleneck: "+add+" %");

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
