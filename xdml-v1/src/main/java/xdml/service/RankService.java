package xdml.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xdml.dao.RankDao;
import xdml.dao.UserMapper;
import xdml.entity.RankItem;

import java.util.List;

@Service
public class RankService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RankDao rankDao;


	public List<RankItem> getRank () {
		return rankDao.getRank();
	}
}
