package com.example.kozlovskiy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static com.example.kozlovskiy.UserStaticInfo.POSITION;
import static com.example.kozlovskiy.UserStaticInfo.users;

public class UserActivity extends AppCompatActivity {

    //активный пользователь
    private User activeUser;
    //Поля праметров пользователя
    private EditText NameTextView,StateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        int position = getIntent().getIntExtra(POSITION, 0);
        activeUser = users.get(position);
        Init();
        setUserInfo();
    }

    private void Init() {
        NameTextView = findViewById(R.id.NameTextView);
        StateTextView = findViewById(R.id.StateTextView);
    }

    private void setUserInfo() {
        NameTextView.setText(activeUser.getName());
        StateTextView.setText(activeUser.getState());
    }

    public void Back(View view) {
        onBackPressed();
    }

    public void Save(View view) {
        //изменяем параметры пользователя
        activeUser.setName(NameTextView.getText().toString());
        activeUser.setState(StateTextView.getText().toString());
        //обновляем список
        MainActivity.UpdateList();
        //закрываем активити
        finish();
    }
}