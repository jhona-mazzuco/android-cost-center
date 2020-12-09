package com.jipsoft.trabalho_final.domain.dao;

import android.database.Cursor;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.jipsoft.trabalho_final.domain.entity.Center;
import com.jipsoft.trabalho_final.domain.entity.Cost;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CostDAO extends BaseDAO<CenterDAO> implements BasicCrudMethod<Cost, Integer, Integer> {

    public CostDAO() {
        super(new CenterDAO());
    }

    @Override
    public void createTable() {
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

    @Override
    public void create(Cost entity) {
        String sql = " INSERT INTO costs (name, price, center_id) VALUES (\"$1\", \"$2\", \"$3\") "
                .replace("$1", entity.getName())
                .replace("$2", String.valueOf(entity.getPrice()))
                .replace("$3", String.valueOf(entity.getCenter().getId()));
        database.execSQL(sql);
    }

    @Override
    public List<Cost> find(Integer id) {
        return findQuery(id, null);
    }

    @Override
    public Cost findById(Integer id) {
        List<Cost> costs = findQuery(null, id);
        if (costs.size() > 0) {
            return costs.get(0);
        }

        return null;
    }

    @Override
    public List<Cost> findQuery(Integer ID_CENTER, Integer ID) {
        StringBuilder sqlBuilder = new StringBuilder(" SELECT id, name, price, center_id FROM costs ");

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
                Center center = relationshipRepository.findById(centerId);
                Cost cost = new Cost(id, name, price, center);
                costs.add(cost);
            }
        }

        return costs;
    }

    @Override
    public void update(Cost entity) {
        String sql = new StringBuilder()
                .append(" UPDATE costs ")
                .append(" SET name = '" + entity.getName() + "',")
                .append(" price = " + entity.getPrice())
                .append(" WHERE id = " + entity.getId())
                .toString();
        database.execSQL(sql);
    }

    @Override
    public void remove(Integer id) {
        database.execSQL("DELETE FROM costs WHERE id = " + id);
    }
}
