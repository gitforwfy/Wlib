package com.wfy.test.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "table_student")
public class Student {

    @DatabaseField(id = true, uniqueCombo = true, columnName = "id")
    public int id;// 学号

    @DatabaseField(columnName = "name", canBeNull = false)
    public String name;// 姓名

    @DatabaseField(columnName = "profession")
    public String profession;// 专业

    public Student() {

    }

    public Student(int id, String name, String profession) {
        this.id = id;
        this.name = name;
        this.profession = profession;
    }

}
