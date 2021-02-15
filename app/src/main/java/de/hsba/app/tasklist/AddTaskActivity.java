package de.hsba.app.tasklist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_LABEL = "de.hsba.app.tasklist.REPLY_LABEL";
    public static final String EXTRA_REPLY_DATE = "de.hsba.app.tasklist.REPLY_DATE";

    private EditText labelEditText;
    private EditText dateEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        labelEditText = findViewById(R.id.edit_label);
        dateEditText = findViewById(R.id.edit_date);
        saveButton = findViewById(R.id.button_save);

        dateEditText.addTextChangedListener(new DateValidator(dateEditText) {
            @Override
            public void validate(EditText editText, String text) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                sdf.setLenient(false);
                try {
                    sdf.parse(text);
                    saveButton.setEnabled(true);
                }catch (Exception e){
                    dateEditText.setError("Date Format dd.MM.yyyy");
                    saveButton.setEnabled(false);
                }
            }
        });
    }

    public void saveTask(View view) {
        Intent reply = new Intent();
        if (labelEditText.getText().toString().isEmpty()){
            setResult(RESULT_CANCELED, reply);
        }else {
            reply.putExtra(EXTRA_REPLY_LABEL,labelEditText.getText().toString());
            reply.putExtra(EXTRA_REPLY_DATE,dateEditText.getText().toString());
            setResult(RESULT_OK,reply);
        }
        finish();
    }

    public void cancel(View view) {
        Intent reply = new Intent();
        setResult(RESULT_CANCELED, reply);
        finish();
    }

    abstract class DateValidator implements TextWatcher{

        private final EditText editText;

        public DateValidator(EditText editText){
            this.editText = editText;
        }

        public abstract void validate(EditText editText, String text);

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editText.getText().toString();
            validate(editText,text);
        }
    }
}