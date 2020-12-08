package com.jipsoft.trabalho_final.domain.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.jipsoft.trabalho_final.core.DBConnection;
import com.jipsoft.trabalho_final.domain.entity.Center;
import com.jipsoft.trabalho_final.domain.entity.Cost;
import com.jipsoft.trabalho_final.domain.entity.User;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CostDAO {

    private static SQLiteDatabase database = DBConnection.getInstace();

    public static void createTable() {
        String sql = new StringBuilder()
                .append(" CREATE TABLE IF NOT EXISTS costs (               ")
                .append("    id INTEGER PRIMARY KEY,                       ")
                .append("    name VARCHAR(50),                             ")
                .append("    price double,                                 ")
                .append("    center_id INTEGER,                            ")
                .append("    FOREIGN KEY(center_id) REFERENCES centers(id) ")
                .append(" )                                                ")
                .toString();
        database.execSQL(sql);
    }

    public static void create(Cost cost) {
        String sql = " INSERT INTO costs (name, price, center_id) VALUES (\"$1\", \"$2\", \"$3\") "
                .replace("$1", cost.getName())
                .replace("$2", String.valueOf(cost.getPrice()))
                .replace("$3", String.valueOf(cost.getCenter().getId()));
        database.execSQL(sql);
    }

    public static List<Cost> find(int idCenter) {
        return findQuery(idCenter, null);
    }

    public static Cost find(int idCenter, int idCost) {
        List<Cost> costs = findQuery(idCenter, idCost);
        if (costs.size() > 0) {
            return costs.get(0);
        }

        return null;
    }

    private static List<Cost> findQuery(Integer ID_CENTER, Integer ID) {
        StringBuilder sqlBuilder = new StringBuilder(" SELECT id, name, price FROM costs ");

        List<String> conditions = new ArrayList<>();
        if (ID != null) {
            conditions.add("id = " + ID);
        }

        if (ID_CENTER != null) {
            conditions.add("center_id = " + ID_CENTER);
        }

        if (conditions.size() > 0) {
            sqlBuilder.append(" WHERE " + String.join(" AND ", conditions));
        }

        Cursor cursor = database.rawQuery(sqlBuilder.toString(), null);
        List<Cost> costs = new ArrayList<>();

        if (cursor != null && cursor.getCount() > 0) {
            int iId = cursor.getColumnIndex("id");
            int iName = cursor.getColumnIndex("name");
            int iPrice = cursor.getColumnIndex("price");
            int iCenter = cursor.getColumnIndex("center_id");

            while (cursor.moveToNext()) {
                int id = cursor.getInt(iId);
                int centerId = cursor.getInt(iCenter);
                String name = cursor.getString(iName);
                Double price = cursor.getDouble(iPrice);
                Center center = CenterDAO.findById(centerId);
                Cost cost = new Cost(id, name, price, center);
                costs.add(cost);
            }
        }

        return costs;
    }

    public static void update(Cost cost) {
        String sql = new StringBuilder()
                .append(" UPDATE costs ")
                .append(" SET name = " + cost.getName())
                .append(" SET price = " + cost.getPrice())
                .append(" WHERE id = " + cost.getId())
                .toString();
        database.execSQL(sql);
    }

    public static void remove(int id) {
        database.execSQL("DELETE FROM costs WHERE id = " + id);
    }
}
