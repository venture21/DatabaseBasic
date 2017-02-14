package com.venture.android.databasebasic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.venture.android.databasebasic.domain.Bbs;

import java.sql.SQLException;

/**
 * Created by parkheejin on 2017. 2. 14..
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {

    public static final String DB_NAME = "database.db";
    public static final int DB_VERSION = 1;  //데이터베이스의 필드가 추가(수정)될 경우 버전을 올려주는 업데이트 된다.

    //
    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    // databaseName, factory, databaseVersion 안씀. super에 직접 입력
    public DBHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * 생성자에서 호출되는 super(context...  에서 database.db파일이 생성되어 있지 않으면 호출된다.
     * @param database
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            // Bbs.class 파일에 정의된 테이블을 생성한다.
            TableUtils.createTable(connectionSource, Bbs.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 생성자에서 호출되는 super(context...  에서 database.db파일이 존재하지만 DB_VERSION이 증가되면 호출된다.
     * @param database
     * @param connectionSource
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            //  Bbs.class에 정의된 테이블 삭제
            TableUtils.dropTable(connectionSource, Bbs.class, false);
            // 데이터를 보존해야 될 필요성이 있으면 중간처리를 해줘야만 한다.
            // TODO : 임시테이블을 생성한 후 데이터를 먼저 저장하고 onCreate 이후에 데이터를 입력해 준다.
            // onCreate를 호출해서 테이블을 생성해 준다.
            onCreate(database, connectionSource);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public Dao<Bbs, Long> getBbsDao() throws SQLException {
        return getDao(Bbs.class);
    }
}
