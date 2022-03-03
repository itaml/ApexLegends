package com.example.kotlinhandbook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    //имя файла
    private static String DB_NAME = "db.sqlite3";
    //контекст бд
    final Context context;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, 3);
        this.context = context;
    }

    @Override //перегруженный метод когда бд создается
    public void onCreate(SQLiteDatabase db) {
        //создаем таблицы
        db.execSQL("CREATE TABLE 'Перечень' (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "'наименование' text NOT NULL," +
                "'файл' text NOT NULL);");

        db.execSQL("INSERT INTO Перечень('наименование','файл') values('Введение в язык Kotlin', 'introduction.html')," +
               "('Первая программа на Kotlin в IntelliJ IDEA','first_prg.html')," +
                "('Структура программы','program_struct.html')," +
                "('Переменные','variables.html')," +
                "('Типы данных','types.html')," +
                "('Консольный ввод и вывод','console_io.html')," +
                "('Операции с числами','arith.html')," +
                "('Условные выражения','сonditional_expressions.html')," +
                "('Условные конструкции','сonditional_constructs.html')," +
                "('Циклы','cycles.html')," +
                "('Диапазоны','ranges.html')," +
                "('Массивы','arrays.html')," +
                "('Функции и их параметры','functions.html')," +
                "('Переменное количество параметров. Vararg','vararg.html')," +
                "('Перегрузка функций','function_overloading.html')," +
                "('Функции высокого порядка','high_order_functions.html')," +
                "('Анонимные функции','anonymus_functions.html')," +
                "('Лямбда-выражения','lambda_expression.html')");

    }
    //перегруженный метод при обновлении бд
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //удаляем все таблицы
        db.execSQL("DROP TABLE IF EXISTS 'Перечень'");
        //вызываем создание бд
        this.onCreate(db);
    }

    //метод для открытия курсора по выборке
    public Cursor query(String query)
    {
        Cursor cr=null;
        try //пытаемся открыть курсор
        { cr=this.getWritableDatabase().rawQuery(query, null);}
        catch (Exception e) //ловим исключения
        {   //собщаем пользователю ошибку во всплываем сообщении, если она случилась
            Toast toast = Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
            //позиционрование сообщения
            toast.setGravity(Gravity.CENTER, 0, 0);
            //отображаем
            toast.show();
            return null;
        }
        return cr;
    }
}