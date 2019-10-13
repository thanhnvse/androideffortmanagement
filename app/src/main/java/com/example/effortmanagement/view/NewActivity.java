package com.example.effortmanagement.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.effortmanagement.R;
import com.example.effortmanagement.contract.EmployeeProContract;
import com.example.effortmanagement.contract.TaskCreContract;
import com.example.effortmanagement.model.dto.EmployeeProDTO;
import com.example.effortmanagement.model.dto.TaskCreDTO;
import com.example.effortmanagement.presenter.EmployeeProPresenter;
import com.example.effortmanagement.presenter.ProjectInfoPresenter;
import com.example.effortmanagement.presenter.TaskCrePresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.effortmanagement.view.LoginActivity.tokens;

public class NewActivity extends AppCompatActivity implements TaskCreContract.View, EmployeeProContract.View {
    private EditText edtTitle, edtDesc, edtEndTaskTime, edtCalendarEffort;
    private Spinner spinner;
    private TaskCrePresenter taskCrePresenter;
    private EmployeeProPresenter employeeProPresenter;
    private String date;
    private int idInt;
    private Button btnSave;
    private int employeeID;
    private TaskCreDTO taskCreDto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        btnSave = findViewById(R.id.btnSave);

        init();

        String id = getIntent().getStringExtra("projectid");

        idInt = Integer.valueOf(id);
        System.out.println(idInt);

        TextView txtID = findViewById(R.id.txtId);
        txtID.setText(idInt+"");

        edtTitle = findViewById(R.id.edtTitle);
        edtDesc = findViewById(R.id.edtDesc);
        edtEndTaskTime = findViewById(R.id.edtEndTaskTime);
        edtEndTaskTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDate();
            }
        });
        edtCalendarEffort = findViewById(R.id.edtCalendarEffort);
        spinner = findViewById(R.id.spinEmployee);

        employeeProPresenter.getEmployeeProInfo(idInt,tokens);

//        Button btnSave = findViewById(R.id.btnSave);
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                taskCrePresenter.getTaskName(tokens);
//                Toast.makeText(NewActivity.this, "Add Successful", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    private void doSomething(int employeeID1){
        employeeID = employeeID1;
        Toast.makeText(this, employeeID+ " success", Toast.LENGTH_SHORT).show();
    }

    private void getDate(){
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                edtEndTaskTime.setText(simpleDateFormat.format(calendar.getTime()));
                date = edtEndTaskTime.getText().toString();
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void init() {
        taskCrePresenter = new TaskCrePresenter();
        taskCrePresenter.setmView(this);

        employeeProPresenter = new EmployeeProPresenter();
        employeeProPresenter.setmView(this);
    }

    @Override
    public void getTaskNameSuccess(TaskCreDTO taskCreDTO) {
        System.out.println("vao```");
        String title = edtTitle.getText().toString();
        String desc = edtDesc.getText().toString();
        String status = "NOT-START";
        int calendarEffort = Integer.valueOf(edtCalendarEffort.getText().toString());

        taskCreDTO = new TaskCreDTO(title,desc,status,date,calendarEffort,idInt,employeeID);
        this.taskCreDto = taskCreDTO;
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println(taskCreDto.getTitle());
//                taskCrePresenter.getTaskName(taskCreDto ,tokens);
//                Toast.makeText(NewActivity.this, "Clicked!!!", Toast.LENGTH_SHORT).show();
//            }
//        });


        Toast.makeText(this, "Create Successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getTaskNameFailure(String message) {

    }

    @Override
    public void getEmployeeProInfoSuccess(final List<EmployeeProDTO> listDTO) {
        List<String> employeeName = new ArrayList<>();
        System.out.println(listDTO);
        for (EmployeeProDTO dto: listDTO){
            employeeName.add(dto.getEmployeeName());
        }
        System.out.println(employeeName);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, employeeName);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String empName = parent.getItemAtPosition(position).toString();
                System.out.println(empName);
                for(EmployeeProDTO dto: listDTO){
                    if(empName.equals(dto.getEmployeeName())){
                        employeeID = dto.getEmployeeID();
                        doSomething(dto.getEmployeeID());
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(taskCreDto.getTitle());
                taskCrePresenter.getTaskName(taskCreDto ,tokens);
                Toast.makeText(NewActivity.this, "Clicked!!!", Toast.LENGTH_SHORT).show();
            }
        });

        //spinner.setSelection();
    }

    @Override
    public void getEmployeeProInfoFailure(String message) {
        Toast.makeText(this, "FAIL", Toast.LENGTH_LONG).show();
    }
}
