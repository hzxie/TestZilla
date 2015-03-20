package com.trunkshell.testzilla.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.trunkshell.testzilla.exception.TimeRangeException;
import com.trunkshell.testzilla.model.PointsLog;

/**
 * LeaderBoardDao的测试类
 * @author Zhou Yihao
 *
 */
@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration({"classpath:test-spring-context.xml"})
public class LeaderBoardDaoTest {

	/**
	 * 测试用例：测试getReputationRanking()方法
	 * 测试数据：时间段使用一周(七天)
	 * 预期结果：返回大小为0的List 
	 */
	@Test
	public void testGetReputationRankingInAWeek() throws TimeRangeException {
		List<PointsLog> users = leaderBoardDao.getReputationRanking(LeaderBoardDao.DAYS_OF_WEEK, 0, 10);
		Assert.assertEquals(0, users.size());
	}
	
	/**
	 * 测试用例：测试getReputationRanking()方法
	 * 测试数据：时间段使用一月(三十天)
	 * 预期结果：返回大小为0的List
	 */
	@Test
	public void testGetReputationRankingInAMonth() throws TimeRangeException {
		List<PointsLog> users = leaderBoardDao.getReputationRanking(LeaderBoardDao.DAYS_OF_MONTH, 0, 10);
		Assert.assertEquals(0, users.size());
	}
	
	/**
	 * 测试用例：测试getReputationRankingAllTime()方法
	 * 测试数据：N/A
	 * 预期结果：返回大小为2的List,排名第一的用户Reputation比第二的Reputation高. 
	 */
	@Test
	public void testGetReputationRankingAllTime() {
		List<PointsLog> users = leaderBoardDao.getReputationRankingAllTime(0, 10);
		Assert.assertEquals(2, users.size());
		Assert.assertTrue(users.get(0).getTotalReputation() >= users.get(1).getTotalReputation());
	}
	
	/**
	 * 测试用例：测试getReputationRanking()方法
	 * 测试数据：使用不合法的时间段值
	 * 预期结果：抛出TimeRangeException异常
	 * @throws TimeRangeException 
	 */
	@Test(expected = com.trunkshell.testzilla.exception.TimeRangeException.class)
	public void testGetReputationRankingUsingIllegalTimeRange() throws TimeRangeException {
		List<PointsLog> users = leaderBoardDao.getReputationRanking(0, 0, 10);
	}
	
	/**
	 * 测试用例：测试getRankingUsersAllTime()方法
	 * 测试数据：N/A
	 * 预期结果：返回到目前为止上榜用户数量(2)
	 */
	@Test
	public void testGetRankingUsersAllTime() {
		long rankingUsers = leaderBoardDao.getRankingUsersAllTime();
		Assert.assertEquals(2, rankingUsers);
	}
	
	/**
	 * 测试用例：测试getRankingUsers()方法
	 * 测试数据：时间段值为一周(七天)
	 * 预期结果：返回一周内上榜用户数量(0)
	 * @throws TimeRangeException 
	 */
	@Test
	public void testGetRankingUsersInAWeek() throws TimeRangeException {
		long rankingUsers = leaderBoardDao.getRankingUsers(LeaderBoardDao.DAYS_OF_WEEK);
		Assert.assertEquals(0, rankingUsers);
	}
	
	/**
	 * 测试用例：测试getRankingUsers()方法
	 * 测试数据：时间段值为一月(三十天)
	 * 预期结果：返回一月内上榜用户数量(0)
	 * @throws TimeRangeException 
	 */
	@Test
	public void testGetRankingUsersInAMonth() throws TimeRangeException {
		long rankingUsers = leaderBoardDao.getRankingUsers(LeaderBoardDao.DAYS_OF_MONTH);
		Assert.assertEquals(0, rankingUsers);
	}
	
	/**
	 * 测试用例：测试getRankingUsers()方法
	 * 测试数据：使用不合法的时间段值
	 * 预期结果：抛出TimeRangeException异常
	 * @throws TimeRangeException
	 */
	@Test(expected = com.trunkshell.testzilla.exception.TimeRangeException.class)
	public void testGetRankingUsersUsingIllegalTimeRange() throws TimeRangeException {
		long rankingUsers = leaderBoardDao.getRankingUsers(0);
	}
	
	/**
	 * 待测试的LeaderBoardDao对象
	 */
	@Autowired
	private LeaderBoardDao leaderBoardDao;
}
