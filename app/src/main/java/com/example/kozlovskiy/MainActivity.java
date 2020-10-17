package com.example.kozlovskiy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.kozlovskiy.UserStaticInfo.POSITION;
import static com.example.kozlovskiy.UserStaticInfo.users;

public class MainActivity extends AppCompatActivity {
    //ListView
    ListView listView;
    //контекст
    Context context;
    LayoutInflater layoutInflater;
    //адаптер для отображения
    static UserListAdapter userListAdapter;


    public static void UpdateList() {
        //обновление ListView
        userListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        AddUsersInList();
        new UserStaticInfo();
        Init();
    }

    private void Init() {
        //получаем ListView
        listView = findViewById(R.id.listView);
        //инициализируем контекст
        context = this;
        //инициализируем LayoutInflater
        layoutInflater = LayoutInflater.from(context);
        //инициализируем адаптер
        userListAdapter = new UserListAdapter();
        //устанавливаем адаптер
        listView.setAdapter(userListAdapter);
    }

    public void GoToUserProfile(int position)
    {
        //Намерение открытия UserActivity
        Intent intent = new Intent(context,UserActivity.class);
        //Передаём в активити позицию элемента списка
        intent.putExtra(POSITION, position);
        startActivity(intent);
    }

    private class UserListAdapter  extends BaseAdapter {
        /**
         * Возвращает длину списка пользователей
         * @return длина списка пользователей
         */
        @Override
        public int getCount() {
            return users.size();
        }

        /**
         * Возвращает объект из списка пользователей
         * @return объект из списка пользователей
         */
        @Override
        public User getItem(int position) {
            return users.get(position);
        }

        /**
         * Возвращает позицию объекта в списке пользователей
         * @return позиция объекта в списке пользователей
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * Создаёт отображаетмый элемент списка
         * @param position      позиция
         * @param currentView   view которое будем возвращать
         * @param parent        родитель - ViewGroup
         * @return      отображаетмый элемент списка
         */
        @SuppressLint("ViewHolder")
        @Override
        public View getView(final int position, View currentView, ViewGroup parent) {
            //пользователь из списка
            User currentUser = getItem(position);
            //"наполняем" view разметкой "item_user"
            currentView = layoutInflater.inflate(R.layout.item_user,parent,false);

            //получем NameTextView из  currentView(который содержит R.layout.item_user)
            TextView nameView = currentView.findViewById(R.id.NameTextView);
            //получем StateTextView из  currentView(который содержит R.layout.item_user)
            TextView stateView = currentView.findViewById(R.id.StateTextView);

            //устанавливаем нужный текст
            nameView.setText(currentUser.getName());
            stateView.setText(currentUser.getState());

            //назначаем событие при клике
            currentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GoToUserProfile(position);
                }
            });

            //возвращаем отображаетмый элемент списка
            return currentView;
        }
    }

}