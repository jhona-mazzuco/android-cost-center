package com.jipsoft.trabalho_final.domain.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jipsoft.trabalho_final.core.DBConnection;
import com.jipsoft.trabalho_final.domain.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static SQLiteDatabase database = DBConnection.getInstace();

    public static void createTable() {
        String sql = new StringBuilder()
                .append(" CREATE TABLE IF NOT EXISTS users ( ")
                .append("     id INTEGER PRIMARY KEY,        ")
                .append("     login VARCHAR(50),             ")
                .append("     password VARCHAR(25)           ")
                .append(" )                                  ")
                .toString();
        database.execSQL(sql);
    }

    public static void create(User user) {
        String sql = " INSERT INTO users (login, password) VALUES (\"$1\", \"$2\") "
                .replace("$1", user.getLogin())
                .replace("$2", user.getPassword());
        database.execSQL(sql);
    }

    public static List<User> find() {
        return findQuery(null);
    }

    public static User find(int id) {
        List<User> users = findQuery(id);
        if (users.size() > 0) {
            return users.get(0);
        }

        return null;
    }

    private static List<User> findQuery(Integer ID) {
        StringBuilder sqlBuilder = new StringBuilder(" SELECT id, login, password ");
        if (ID != null) {
            sqlBuilder.append(" WHERE id = " + ID);
        }

        sqlBuilder.append(" FROM users ");
        Cursor cursor = database.rawQuery(sqlBuilder.toString(), null);
        List<User> users = new ArrayList<>();

        if(cursor != null && cursor.getCount() > 0) {
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

    public static void remove(int id) {
        String sql = "DELETE FROM users WHERE id = " + id;
        database.execSQL(sql);
    }
}
