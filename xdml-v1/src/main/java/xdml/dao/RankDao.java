package xdml.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import xdml.entity.RankItem;

import java.util.List;

/** 数据访问层用 @Repository；与 @Service 一样都会注册成 Bean，语义不同 */
@Repository
public class RankDao {

	@Autowired
	private SqlSession sqlSession;

	public List<RankItem> getRank() {
		return sqlSession.selectList("MyMapper.selectRank");
	}
}
