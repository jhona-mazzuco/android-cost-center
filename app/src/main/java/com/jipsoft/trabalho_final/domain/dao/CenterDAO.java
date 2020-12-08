package com.jipsoft.trabalho_final.domain.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.jipsoft.trabalho_final.core.DBConnection;
import com.jipsoft.trabalho_final.domain.entity.Center;
import com.jipsoft.trabalho_final.domain.entity.User;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CenterDAO {

    private static SQLiteDatabase database = DBConnection.getInstace();

    public static void createTable() {
        String sql = new StringBuilder()
                .append(" CREATE TABLE IF NOT EXISTS centers (         ")
                .append("    id INTEGER PRIMARY KEY,                   ")
                .append("    name VARCHAR(50),                         ")
                .append("    user_id INTEGER,                          ")
                .append("    FOREIGN KEY(user_id) REFERENCES users(id) ")
                .append(" )                                            ")
                .toString();
        database.execSQL(sql);
    }

    public static void create(Center center) {
        String sql = " INSERT INTO centers (name, user_id) VALUES (\"$1\", \"$2\") "
                .replace("$1", center.getName())
                .replace("$2", String.valueOf(center.getUser().getId()));
        database.execSQL(sql);
    }

    public static List<Center> find(int idUser) {
        return findQuery(idUser, null);
    }

    public static Center findById(int idCenter) {
        List<Center> centers = findQuery(null, idCenter);
        if (centers.size() > 0) {
            return centers.get(0);
        }

        return null;
    }

    private static List<Center> findQuery(Integer ID_USER, Integer ID) {
        StringBuilder sqlBuilder = new StringBuilder(" SELECT id, name, user_id FROM centers ");

        List<String> conditions = new ArrayList<>();
        if (ID != null) {
            conditions.add("id = " + ID);
        }

        if (ID_USER != null) {
            conditions.add( "user_id = " + ID_USER);
        }

        if (conditions.size() > 0) {
            sqlBuilder.append(" WHERE " + String.join(" AND ", conditions));
        }

        Cursor cursor = database.rawQuery(sqlBuilder.toString(), null);
        List<Center> centers = new ArrayList<>();

        if(cursor != null && cursor.getCount() > 0) {
            int iId = cursor.getColumnIndex("id");
            int iName = cursor.getColumnIndex("name");
            int iUser = cursor.getColumnIndex("user_id");

            while (cursor.moveToNext()) {
                int id = cursor.getInt(iId);
                int userId = cursor.getInt(iUser);
                String name = cursor.getString(iName);
                User user = UserDAO.find(userId);
                Center center = new Center(id, name, user);
                centers.add(center);
            }
        }

        return centers;
    }

    public static void update(Center center) {
        String sql = new StringBuilder()
                .append(" UPDATE centers ")
                .append(" SET name = '" + center.getName() + "'")
                .append(" WHERE id = " + center.getId())
                .toString();
        database.execSQL(sql);
    }

    public static void remove(int id) {
        String sql = "DELETE FROM centers WHERE id = " + id;
        database.execSQL(sql);
    }
}
