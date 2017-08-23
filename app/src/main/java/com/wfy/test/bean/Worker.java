package com.wfy.test.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "table_worker")
public class Worker {

    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(columnName = "name")
    public String name;

    @DatabaseField(columnName = "jobNum")
    public long jobNum;

    @DatabaseField(columnName = "profession", foreign = true, foreignAutoRefresh = true)
    public Profession profession;

    public Worker() {

    }

    public Worker(String name, long jobNum) {
        this.name = name;
        this.jobNum = jobNum;
    }

    public Worker(String name, long jobNum, Profession profession) {
        this.name = name;
        this.jobNum = jobNum;
        this.profession = profession;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Worker worker = (Worker) o;

        return id == worker.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
