package com.photo.album.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName = "table_profession")
public class Profession {

    @DatabaseField(id = true, uniqueCombo = true, columnName = "id")
    public int id; // 职业编号
    @DatabaseField(columnName = "name", canBeNull = false)
    public String name; // 职业
    @DatabaseField(columnName = "salary")
    public float salary; // 薪水

    public Profession() {

    }

    public Profession(int id, String name, float salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

}
