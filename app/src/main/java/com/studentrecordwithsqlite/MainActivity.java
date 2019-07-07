package com.studentrecordwithsqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    EditText editTextId,editName,editEmail,editCC;
    Button buttonAdd,buttonGetData,buttonUpdate,buttonViewAll,buttonDelete;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(this);
        editTextId = findViewById(R.id.editText_id);
        editName = findViewById(R.id.editText_name);
        editEmail = findViewById(R.id.editText_email);
        editCC = findViewById(R.id.editText_CC);

        buttonAdd = findViewById(R.id.button_add);
        buttonDelete = findViewById(R.id.button_delete);
        buttonGetData = findViewById(R.id.button_view);
        buttonViewAll = findViewById(R.id.button_viewAll);
        buttonUpdate = findViewById(R.id.button_update);
          AddData();
          getData();
          getAll();
          upDate();
          del();
    }

    public void AddData(){
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              boolean isInserted = myDB.insertData(editName.getText().toString(),editEmail.getText().toString(),
                      editCC.getText().toString());

              if(isInserted){

                  Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_SHORT).show();

              }
              else {
                  Toast.makeText(MainActivity.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();


              }

            }
        });
    }

public void getData(){
        buttonGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = editTextId.getText().toString();
                if (id.equals(String.valueOf("")))
                { editTextId.setError("Enter ID");
                return;
            }

            Cursor cursor = myDB.getData(id);
            String data = null;
                if(cursor.getCount()==0){
                    showMessage("Error","Nothing to Display");
                }
            if(cursor.moveToNext())

            {
                data = "ID: "+ cursor.getString(0) + "\n"+
                        "Name: "+  cursor.getString(1) + "\n"+
                        "Email: "+ cursor.getString(2) + "\n"+
                        "Course count: "+ cursor.getString(3) + "\n";

            }
            showMessage("Data", data);

            }
        });

}
public void getAll(){
        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = myDB.getallData();

                if(cursor.getCount()==0){
                    showMessage("Error","Nothing to Display");
                }
                StringBuffer buffer = new StringBuffer();

                while (cursor.moveToNext()){
                    buffer.append("ID "+ cursor.getString(0)+"\n");
                    buffer.append("Name "+ cursor.getString(1)+"\n");
                    buffer.append("Email "+ cursor.getString(2)+"\n");
                    buffer.append("CC "+ cursor.getString(3)+"\n\n");

                }
                showMessage("All data",buffer.toString());

            }
        });
}
public void upDate(){
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isup = myDB.updateData(editTextId.getText().toString()
                ,editName.getText().toString(),editEmail.getText().toString(),editCC.getText().toString());

            if(isup){
                Toast.makeText(MainActivity.this,"Updated successfully",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this,"Update Unsuccessful",Toast.LENGTH_SHORT).show();

            }

            }
        });
}
public void del(){

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer del = myDB.deleteData(editTextId.getText().toString());
                if(del>0){
                    Toast.makeText(MainActivity.this,"Delete Success",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Delete UnSuccessful",Toast.LENGTH_SHORT).show();

                }
            }
        });
}
    private void  showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();


    }


    public void clear(View view) {

        editTextId.setText(null);
        editName.setText(null);
        editEmail.setText(null);
        editCC.setText(null);

    }




}
