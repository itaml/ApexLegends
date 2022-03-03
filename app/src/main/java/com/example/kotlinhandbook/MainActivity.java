package com.example.kotlinhandbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DbHelper db;
    Cursor cr; //курсор на данные
    SimpleCursorAdapter sCr; //адаптер этих курсоров для ListView
    ListView lvList; //список для вывода данных
    EditText edFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edFind=(EditText) findViewById(R.id.edInput);
        edFind.addTextChangedListener ( new TextWatcher() {

            public void afterTextChanged ( Editable s ) {
                //что-то делаем после изменения
            }

            public void beforeTextChanged ( CharSequence s, int start, int count, int after ) {
                //что-то делаем до изменения
            }

            //пока текст меняется - загружаем данные
            public void onTextChanged ( CharSequence s, int start, int before, int count ) {
                loadData(s.toString());
            }
        });

        //ищем наши компоненты - списки вывода
        lvList = (ListView) findViewById(R.id.lvList);
        lvList.setAdapter(null);

        //создаем обьект нашего класса для работы с бд
        db = new DbHelper(this);

        loadData(null);
    }

    void loadData(String strLike)
    {
        try {
            //получим курсор на выборку
            if (strLike==null || strLike.equals(""))
                cr = db.query("select * from 'Перечень'");
            else
                cr = db.query("SELECT * FROM Перечень where наименование LIKE '%"+strLike+"%'");

            //если курсор открыт
            if (cr != null) { //и есть в нем данные
                if (cr.getCount() > 0) {
                    cr.moveToFirst(); //передвинем указатель в начало, так как он в конце
                    //создадим адаптер данных из заголока
                    sCr = new SimpleCursorAdapter(this,
                            android.R.layout.simple_list_item_1,
                            cr, new String[]{"наименование"},
                            new int[]{android.R.id.text1}, 1);
                    lvList.setAdapter(sCr); //зададим этот адаптер для списка
                    //установим события при клике на пункт списка
                    lvList.setOnItemClickListener(new ListView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapter_view, View v, int i, long l) {
                            cr.moveToPosition(i); //передвинем курсор на данные которые выбраны в списке
                            //создадим намерение, будем переходить с этой активности на активность
                            Intent intent = new Intent(MainActivity.this, ViewSection.class);
                            //получаем необходимые данные из курсора
                            String fileName = cr.getString(cr.getColumnIndexOrThrow("файл"));
                            //добавляем данные в экстраДанные намерения, что бы передать их другой активности
                            intent.putExtra("fileName", fileName);
                            //вызываем активность с параметрами
                            startActivity(intent);
                        }
                    });
                } //if cr1
                else
                    lvList.setAdapter(null); //если данных в курсоре нет, то очистим список
            }
        }
        catch(Exception e)
        {
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setText(e.getMessage());
            toast.show();

        }
    }
}