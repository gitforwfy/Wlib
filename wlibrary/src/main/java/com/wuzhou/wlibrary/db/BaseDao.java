package com.wuzhou.wlibrary.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.PreparedUpdate;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.DatabaseConnection;

import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 数据库CRUD操作的Dao，子类继承实现抽象方法
 * Created by wfy 2017/7/5 11:25.
 */
public abstract class BaseDao<T, Integer> {

    protected Context mContext;
    protected DatabaseHelper mDatabaseHelper;

    public BaseDao(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context can't be null!");
        }
        mContext = context.getApplicationContext();
        mDatabaseHelper = DatabaseHelper.getHelper(mContext);
    }

    /**
     * public Dao<T, Integer> getDao() throws SQLException
     *
     * @return
     * @throws SQLException
     */
    public abstract Dao<T, Integer> getDao() throws SQLException;

    /**************************************** 保存 ******************************************************/

    /**
     * 插入一条记录
     *
     * @param t
     */
    public void insert(T t) {
        try {
            Dao<T, Integer> dao = getDao();
            dao.create(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入一组记录，使用事务处理
     *
     * @param list
     */
    public void insertList(List<T> list) {
        if (list.size() <= 0) return;
        try {
            Dao<T, Integer> dao = getDao();
            DatabaseConnection databaseConnection = null;
            try {
                databaseConnection = dao.startThreadConnection();
                dao.setAutoCommit(databaseConnection, false);
                for (T t : list) {
                    dao.create(t);
//                    dao.createIfNotExists(t);
                }
                dao.commit(databaseConnection);
            } catch (SQLException e) {
                dao.rollBack(databaseConnection);
                e.printStackTrace();
            } finally {
                dao.endThreadConnection(databaseConnection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**************************************** 更新 ******************************************************/

    /**
     * 更新一条记录
     *
     * @param t
     */
    public void update(T t) {
        try {
            Dao<T, Integer> dao = getDao();
            dao.update(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据指定条件和指定字段更新记录
     *
     * @param key
     * @param value
     * @param columnNames
     * @param columnValues
     */
    public void update(String key, Object value, String[] columnNames, Object[] columnValues) {
        if (columnNames.length != columnNames.length) {
            throw new InvalidParameterException("params size is not equal");
        }
        try {
            Dao<T, Integer> dao = getDao();
            UpdateBuilder<T, Integer> updateBuilder = dao.updateBuilder();
            updateBuilder.where().eq(key, value);
            for (int i = 0; i < columnNames.length; i++) {
                updateBuilder.updateColumnValue(columnNames[i], columnValues[i]);
            }
            PreparedUpdate<T> prepareUpdate = updateBuilder.prepare();
            dao.update(prepareUpdate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据指定条件和指定字段更新记录
     *
     * @param key
     * @param value
     * @param columnNames
     * @param columnValues
     */
    public void update(String[] key, Object[] value, String[] columnNames, Object[] columnValues) {
        if (columnNames.length != columnNames.length) {
            throw new InvalidParameterException("params size is not equal");
        }
        try {
            Dao<T, Integer> dao = getDao();
            UpdateBuilder<T, Integer> updateBuilder = dao.updateBuilder();
            Where<T, Integer> where = updateBuilder.where();
            for (int i = 0; i < key.length; i++) {
                if (i == 0) {
                    where.eq(key[i], value[i]);
                } else {
                    where.and().eq(key[i], value[i]);
                }
            }
            for (int i = 0; i < columnNames.length; i++) {
                updateBuilder.updateColumnValue(columnNames[i], columnValues[i]);
            }
            PreparedUpdate<T> prepareUpdate = updateBuilder.prepare();
            dao.update(prepareUpdate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据PreparedUpdate更新记录
     *
     * @param preparedUpdate
     */
    public void update(PreparedUpdate<T> preparedUpdate) {
        try {
            Dao<T, Integer> dao = getDao();
            dao.update(preparedUpdate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**************************************** 保存或更新  ******************************************************/

    /**
     * 插入或更新一条记录，不存在则插入，否则更新
     *
     * @param t
     */
    public void insertOrUpdate(T t) {
        try {
            Dao<T, Integer> dao = getDao();
            dao.createOrUpdate(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入或更新一组数据，使用事务处理
     *
     * @param list
     */
    public void insertOrUpdate(List<T> list) {
        if (list.size() <= 0) return;
        try {
            Dao<T, Integer> dao = getDao();
            DatabaseConnection databaseConnection = null;
            try {
                databaseConnection = dao.startThreadConnection();
                dao.setAutoCommit(databaseConnection, false);
                for (T t : list) {
                    dao.createOrUpdate(t);
                }
                dao.commit(databaseConnection);
            } catch (SQLException e) {
                dao.rollBack(databaseConnection);
                e.printStackTrace();
            } finally {
                dao.endThreadConnection(databaseConnection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**************************************** 删除操作 ******************************************************/

    /**
     * 删除一条记录
     *
     * @param t
     */
    public void delete(T t) {
        try {
            Dao<T, Integer> dao = getDao();
            dao.delete(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据单个条件删除一条记录,如果有多个记录满足该条件，删除查询到的第一条记录
     *
     * @param columnName
     * @param columnValue
     */
    public void delete(String columnName, Object columnValue) {
        try {
            Dao<T, Integer> dao = getDao();
            QueryBuilder<T, Integer> queryBuilder = dao.queryBuilder();
            queryBuilder.where().eq(columnName, columnValue);
            PreparedQuery<T> preparedQuery = queryBuilder.prepare();
            T t = dao.queryForFirst(preparedQuery);
            dao.delete(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据条件组合删除一条记录,如果有多个记录满足该条件，删除查询到的第一条记录
     *
     * @param columnNames
     * @param columnValues
     */
    public void delete(String[] columnNames, Object[] columnValues) {
        if (columnNames.length != columnNames.length) {
            throw new InvalidParameterException("params size is not equal");
        }
        try {
            QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
            Where<T, Integer> wheres = queryBuilder.where();
            for (int i = 0; i < columnNames.length; i++) {
                if (i == 0) {
                    wheres.eq(columnNames[i], columnValues[i]);
                } else {
                    wheres.and().eq(columnNames[i], columnValues[i]);
                }
            }
            PreparedQuery<T> preparedQuery = queryBuilder.prepare();
            Dao<T, Integer> dao = getDao();
            T t = dao.queryForFirst(preparedQuery);
            delete(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除一组记录，使用事务处理
     * @param list
     */
    /**
     * 注意：Dao.delete(Collection<T> datas)方法删除，最多只能删除999条记录
     **/
    public void deleteList(List<T> list) {
        if (list.size() <= 0) return;
        try {
            Dao<T, Integer> dao = getDao();
            DatabaseConnection databaseConnection = null;
            try {
                databaseConnection = dao.startThreadConnection();
                dao.setAutoCommit(databaseConnection, false);
                for (T t : list) {
                    dao.delete(t);
                }
                dao.commit(databaseConnection);
            } catch (SQLException e) {
                dao.rollBack(databaseConnection);
                e.printStackTrace();
            } finally {
                dao.endThreadConnection(databaseConnection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        try {
//            Dao<T, Integer> dao = getDao();
//            dao.delete(list);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 根据条件删除满足条件的所有记录
     *
     * @param columnName
     * @param columnValue
     */
    public void deleteList(String columnName, Object columnValue) {
        try {
            Dao<T, Integer> dao = getDao();
            DeleteBuilder<T, Integer> deleteBuilder = dao.deleteBuilder();
            deleteBuilder.where().eq(columnName, columnValue);
            PreparedDelete<T> preparedDelete = deleteBuilder.prepare();
            dao.delete(preparedDelete);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据条件组合删除所有满足条件的记录
     *
     * @param columnNames
     * @param columnValues
     */
    public void deleteList(String[] columnNames, Object[] columnValues) {
        if (columnNames.length != columnNames.length) {
            throw new InvalidParameterException("params size is not equal");
        }
        try {
            Dao<T, Integer> dao = getDao();
            DeleteBuilder<T, Integer> deleteBuilder = dao.deleteBuilder();
            Where<T, Integer> wheres = deleteBuilder.where();
            for (int i = 0; i < columnNames.length; i++) {
                if (i == 0) {
                    wheres.eq(columnNames[i], columnValues[i]);
                } else {
                    wheres.and().eq(columnNames[i], columnValues[i]);
                }
            }
            PreparedDelete<T> preparedDelete = deleteBuilder.prepare();
            dao.delete(preparedDelete);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据id删除一条记录
     *
     * @param id
     */
    public void deleteById(Integer id) {
        try {
            Dao<T, Integer> dao = getDao();
            dao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据id数组删除一组记录
     *
     * @param ids
     */
    public void deleteByIds(List<Integer> ids) {
        try {
            Dao<T, Integer> dao = getDao();
            dao.deleteIds(ids);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据PreparedDelete删除记录
     *
     * @param preparedDelete
     */
    public void delete(PreparedDelete<T> preparedDelete) {
        try {
            Dao<T, Integer> dao = getDao();
            dao.delete(preparedDelete);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除表中的所有记录
     */
    public void deleteAll() {
        try {
            Dao<T, Integer> dao = getDao();
            DeleteBuilder<T, Integer> deleteBuilder = dao.deleteBuilder();
            PreparedDelete<T> preparedDelete = deleteBuilder.prepare();
            dao.delete(preparedDelete);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清空一张表中的数据
     *
     * @param table 表名
     */
    public void clearTable(String table) {
        try {
            Dao<T, Integer> dao = getDao();
            String delete = String.format("delete from %s", table);
            dao.queryRaw(delete);//清空数据
            String updateSeq = String.format("update sqlite_sequence SET seq = 0 where name ='%s'", table);
            dao.queryRaw(updateSeq);//自增长ID为0
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**************************************** 查询操作 ******************************************************/

    /**
     * 根据单个条件查询一条记录
     *
     * @param columnName
     * @param columnValue
     * @return
     */
    public T query(String columnName, Object columnValue) {
        try {
            Dao<T, Integer> dao = getDao();
            QueryBuilder<T, Integer> queryBuilder = dao.queryBuilder();
            queryBuilder.where().eq(columnName, columnValue);
            PreparedQuery<T> preparedQuery = queryBuilder.prepare();
            T t = dao.queryForFirst(preparedQuery);
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据条件组合查询一条记录
     *
     * @param columnNames
     * @param columnValues
     * @return
     */
    public T query(String[] columnNames, Object[] columnValues) {
        if (columnNames.length != columnNames.length) {
            throw new InvalidParameterException("params size is not equal");
        }
        try {
            QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
            Where<T, Integer> wheres = queryBuilder.where();
            for (int i = 0; i < columnNames.length; i++) {
                if (i == 0) {
                    wheres.eq(columnNames[i], columnValues[i]);
                } else {
                    wheres.and().eq(columnNames[i], columnValues[i]);
                }
            }
            PreparedQuery<T> preparedQuery = queryBuilder.prepare();
            Dao<T, Integer> dao = getDao();
            T t = dao.queryForFirst(preparedQuery);
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据PreparedQuery查询所有记录
     *
     * @param preparedQuery
     * @return
     */
    public List<T> queryList(PreparedQuery<T> preparedQuery) {
        try {
            Dao<T, Integer> dao = getDao();
            DatabaseConnection databaseConnection = null;
            try {
                databaseConnection = dao.startThreadConnection();
                dao.setAutoCommit(databaseConnection, false);
                List<T> query = dao.query(preparedQuery);
                dao.commit(databaseConnection);
                return query;
            } catch (SQLException e) {
                dao.rollBack(databaseConnection);
                e.printStackTrace();
            } finally {
                dao.endThreadConnection(databaseConnection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据单个条件查询所有满足条件的记录
     *
     * @param columnName
     * @param columnValue
     * @return
     */
    public List<T> queryList(String columnName, Object columnValue) {
        try {
            Dao<T, Integer> dao = getDao();
            QueryBuilder<T, Integer> queryBuilder = dao.queryBuilder();
            queryBuilder.where().eq(columnName, columnValue);
            PreparedQuery<T> preparedQuery = queryBuilder.prepare();
            List<T> query = dao.query(preparedQuery);
            return query;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据条件组合查询所有满足条件的记录
     *
     * @param columnNames
     * @param columnValues
     * @return
     */
    public List<T> queryList(String[] columnNames, Object[] columnValues) {
        if (columnNames.length != columnNames.length) {
            throw new InvalidParameterException("params size is not equal");
        }
        try {
            Dao<T, Integer> dao = getDao();
            QueryBuilder<T, Integer> queryBuilder = dao.queryBuilder();
            Where<T, Integer> wheres = queryBuilder.where();
            for (int i = 0; i < columnNames.length; i++) {
                if (i == 0) {
                    wheres.eq(columnNames[i], columnValues[i]);
                } else {
                    wheres.and().eq(columnNames[i], columnValues[i]);
                }
            }
            PreparedQuery<T> preparedQuery = queryBuilder.prepare();
            List<T> query = dao.query(preparedQuery);
            return query;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据键值对查询所有满足条件的记录
     *
     * @param map
     * @return
     */
    public List<T> queryList(Map<String, Object> map) {
        try {
            Dao<T, Integer> dao = getDao();
            QueryBuilder<T, Integer> queryBuilder = dao.queryBuilder();
            if (!map.isEmpty()) {
                Where<T, Integer> wheres = queryBuilder.where();
                Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
                String key = null;
                Object value = null;
                for (int i = 0; iterator.hasNext(); i++) {
                    Map.Entry<String, Object> next = iterator.next();
                    key = next.getKey();
                    value = next.getValue();
                    if (i == 0) {
                        wheres.eq(key, value);
                    } else {
                        wheres.and().eq(key, value);
                    }
                }
            }
            PreparedQuery<T> preparedQuery = queryBuilder.prepare();
            List<T> query = dao.query(preparedQuery);
            return query;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    public T queryById(Integer id) {
        try {
            Dao<T, Integer> dao = getDao();
            T t = dao.queryForId(id);
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询所有记录
     *
     * @return
     */
    public List<T> queryAll() {
        try {
            Dao<T, Integer> dao = getDao();
            List<T> query = dao.queryForAll();
            return query;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 条件查询
     *
     * @param offset      偏移量
     * @param limit       查询数量
     * @param columnName
     * @param columnValue
     * @return
     */
    public List<T> queryAll(long offset, long limit, String columnName, Object columnValue) {
        try {
            Dao<T, Integer> dao = getDao();
            QueryBuilder<T, Integer> queryBuilder = dao.queryBuilder();
            queryBuilder.offset(offset)
                    .limit(limit)
                    .where().eq(columnName, columnValue);
            PreparedQuery<T> preparedQuery = queryBuilder.prepare();
            List<T> query = dao.query(preparedQuery);
            return query;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 条件查询
     *
     * @param offset 偏移量
     * @param limit  查询数量
     * @return
     */
    public List<T> queryAll(long offset, long limit) {
        try {
            Dao<T, Integer> dao = getDao();
            QueryBuilder<T, Integer> queryBuilder = dao.queryBuilder();
            queryBuilder.offset(offset)
                    .limit(limit);
            PreparedQuery<T> preparedQuery = queryBuilder.prepare();
            List<T> query = dao.query(preparedQuery);
            return query;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 条件查询
     *
     * @param offset    偏移量
     * @param limit     查询数量
     * @param order     排序字段
     * @param ascending true--升序 false--降序
     * @return
     */
    public List<T> queryAll(long offset, long limit, String order, boolean ascending) {
        try {
            Dao<T, Integer> dao = getDao();
            QueryBuilder<T, Integer> queryBuilder = dao.queryBuilder();
            queryBuilder.orderBy(order, ascending)
                    .offset(offset)
                    .limit(limit);
            PreparedQuery<T> preparedQuery = queryBuilder.prepare();
            List<T> query = dao.query(preparedQuery);
            return query;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**************************************** 其他操作 ******************************************************/

    /**
     * 表是否存在
     *
     * @return true--存在 false-- 不存在
     */
    public boolean isTableExists() {
        try {
            return getDao().isTableExists();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 获得记录数
     *
     * @return 记录数
     */
    public long count() {
        try {
            Dao<T, Integer> dao = getDao();
            long count = dao.countOf();
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获得记录数
     *
     * @param preparedQuery
     * @return 记录数
     */
    public long count(PreparedQuery<T> preparedQuery) {
        try {
            Dao<T, Integer> dao = getDao();
            long count = dao.countOf(preparedQuery);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
