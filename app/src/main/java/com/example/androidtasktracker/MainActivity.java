package com.example.androidtasktracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editText;
    private CheckBox firstTask;
    private CheckBox secondTask;
    private CheckBox thirdTask;
    private CheckBox fourthTask;
    private CheckBox fifthTask;
    private Button addButton;
    private Button clearButton;
    private Button shareButton;
    private TextView congrats;

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

        editText = findViewById(R.id.editTextText2);
        addButton = findViewById(R.id.button3);
        clearButton = findViewById(R.id.button2);
        shareButton = findViewById(R.id.button);
        firstTask = findViewById(R.id.checkBox);
        secondTask = findViewById(R.id.checkBox9);
        thirdTask = findViewById(R.id.checkBox8);
        fourthTask = findViewById(R.id.checkBox7);
        fifthTask = findViewById(R.id.checkBox6);
        congrats = findViewById(R.id.textView6);

        addButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // List of all tasks (TextView, Button, etc.)
        CheckBox[] tasks = {firstTask, secondTask, thirdTask, fourthTask, fifthTask};

        // Get the input from the EditText
        String inputText = editText.getText().toString();

        // Iterate over the tasks and update the first one with non-black text
        for (CheckBox task : tasks) {
            if (task.getCurrentTextColor() != Color.BLACK) {
                task.setText(inputText);
                task.setTextColor(Color.BLACK);
                editText.setText("");
                break;  // Exit the loop once the first task is updated
            }
        }

        for (CheckBox task : tasks) {
            task.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAllTasksCompleted(tasks);
                }
            });
        }

        if (firstTask.getCurrentTextColor() == Color.BLACK) {
            clearButton.setVisibility(View.VISIBLE);
        }
    }

    public void clearTasks(View v) {
        CheckBox[] tasks = {firstTask, secondTask, thirdTask, fourthTask, fifthTask};
        int i = 1;

        for (CheckBox task : tasks) {
            String resourceName = "checkbox_" + i;
            int resId = getResources().getIdentifier(resourceName, "string", getPackageName());

            if (resId != 0) {
                task.setChecked(false);
                task.setText(getResources().getString(resId));
                task.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.material_dynamic_neutral60));
            }

            i++;
        }
    }

    private void checkAllTasksCompleted(CheckBox[] tasks) {
        boolean allChecked = true;
        for (CheckBox task : tasks) {
            if (!task.isChecked()) {
                allChecked = false;
                break;
            }
        }

        // Show the button if all checkboxes are checked
        if (allChecked) {
            shareButton.setVisibility(View.VISIBLE);
            congrats.setVisibility((View.VISIBLE));
        } else {
            shareButton.setVisibility(View.GONE);
            congrats.setVisibility((View.GONE));
        }
    }

    public void shareToEmail(View v) {
        CheckBox[] tasks = {firstTask, secondTask, thirdTask, fourthTask, fifthTask};
        Intent sendEmail = new Intent(Intent.ACTION_SEND);
        sendEmail.setType("text/plain");
        sendEmail.putExtra(Intent.EXTRA_SUBJECT, "My Tasks");

        StringBuilder tasksText = new StringBuilder();
        tasksText.append("Hey there! Look at all the tasks I completed today:\n");
        for (CheckBox task : tasks) {
            if (task.isChecked()) {
                tasksText.append("- ").append(task.getText().toString()).append("\n");
            }
        }

        sendEmail.putExtra(Intent.EXTRA_TEXT, tasksText.toString().trim());

        startActivity(Intent.createChooser(sendEmail, "Send email using:"));
    }





}