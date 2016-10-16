package zan.delivery_services.db;

import java.io.Reader;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Db {
	public static SqlSession getSession(){
		try{
			Reader reader = org.apache.ibatis.io.Resources.getResourceAsReader("mybatis-config.xml");
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			return sqlSessionFactory.openSession();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
