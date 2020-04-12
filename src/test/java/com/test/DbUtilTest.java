package com.test;

import com.test.util.DbUtil;
import org.junit.Test;

import java.io.File;

public class DbUtilTest {
    @Test
    public void testBackup() {
        File file = new File("test.sql");
        DbUtil.backup(file, "root", "root", "wmh");
    }

//    这个测试用例会重置Litecommunity数据库，所以比较危险，请开发者注意
//    @Test
    public void testLoad() {
        File file = new File("test.sql");
        DbUtil.load(file, "root", "root", "wmh");
    }
}
