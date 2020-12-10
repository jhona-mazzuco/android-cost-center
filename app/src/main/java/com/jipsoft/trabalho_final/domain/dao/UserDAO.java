package com.jipsoft.trabalho_final.domain.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jipsoft.trabalho_final.core.DBConnection;
import com.jipsoft.trabalho_final.domain.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static SQLiteDatabase database = DBConnection.getInstace();

    public static long create(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("login", user.getLogin());
        contentValues.put("password", user.getPassword());
        return database.insert("users", null, contentValues);
    }

    public static User find(int id) {
        List<User> users = findQuery(id);
        if (users.size() > 0) {
            return users.get(0);
        }

        return null;
    }

    private static List<User> findQuery(Integer ID) {
        StringBuilder sqlBuilder = new StringBuilder(" SELECT id, login, password FROM users ");
        if (ID != null) {
            sqlBuilder.append(" WHERE id = " + ID);
        }

        Cursor cursor = database.rawQuery(sqlBuilder.toString(), null);
        List<User> users = new ArrayList<>();

        if (cursor != null && cursor.getCount() > 0) {
            int iId = cursor.getColumnIndex("id");
            int iLogin = cursor.getColumnIndex("login");
            int iPassword = cursor.getColumnIndex("password");

            while (cursor.moveToNext()) {
                int id = cursor.getInt(iId);
                String login = cursor.getString(iLogin);
                String password = cursor.getString(iPassword);

                User user = new User(id, login, password);
                users.add(user);
            }
        }
        return users;
    }

    public static User findByLogin(String LOGIN) {
        Cursor cursor = database.rawQuery("SELECT id, login, password FROM users WHERE login = '" + LOGIN + "'", null);
        User user = null;
        if (cursor != null && cursor.getCount() > 0) {
            int iId = cursor.getColumnIndex("id");
            int iLogin = cursor.getColumnIndex("login");
            int iPassword = cursor.getColumnIndex("password");

            while (cursor.moveToNext()) {
                int id = cursor.getInt(iId);
                String login = cursor.getString(iLogin);
                String password = cursor.getString(iPassword);
                user = new User(id, login, password);
            }
        }

        return user;
    }
}
