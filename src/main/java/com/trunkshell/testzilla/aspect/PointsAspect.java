package com.trunkshell.testzilla.aspect;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trunkshell.testzilla.model.Bug;
import com.trunkshell.testzilla.model.BugStatus;
import com.trunkshell.testzilla.model.PointsRule;
import com.trunkshell.testzilla.model.User;
import com.trunkshell.testzilla.service.BugService;
import com.trunkshell.testzilla.service.PointsService;
import com.trunkshell.testzilla.util.HttpSessionParser;

/**
 * 积分操作的切片. 在触发某些操作时完成对应的积分操作.
 * 
 * @author Xie Haozhe
 */
@Aspect
public class PointsAspect {
	/**
	 * 在用户验证电子邮件后根据规则予以积分奖励.
	 * 
	 * @param proceedingJoinPoint
	 *            - ProceedingJoinPoint对象
	 * @param HttpServletRequest
	 *            - HttpRequest对象
	 * @return 包含验证电子邮件页面信息的ModelAndView对象
	 */
	@Around(value = "execution(* com.trunkshell.testzilla.controller.AccountsController.verifyEmailView(..)) && args(.., request)")
	public ModelAndView getCreditAfterVerifyEmail(
			ProceedingJoinPoint proceedingJoinPoint, HttpServletRequest request)
			throws Throwable {
		HttpSession session = request.getSession();
		User originalUser = HttpSessionParser.getCurrentUser(session);
		ModelAndView view = (ModelAndView) proceedingJoinPoint.proceed();
		User currentUser = HttpSessionParser.getCurrentUser(session);

		if (originalUser != null && currentUser != null
				&& !originalUser.isEmailVerified()
				&& currentUser.isEmailVerified()
				&& !isVerifyEmailPointsLogExists(currentUser)) {
			PointsRule rule = pointsService
					.getPointsRuleUsingSlug("create-account");
			String meta = currentUser.getEmail();
			appendPointsLogs(currentUser, rule, meta);
		}
		return view;
	}

	/**
	 * 检查用户验证Email获得积分记录是否已存在 (一个用户只能获得1次该项积分)
	 * 
	 * @param user
	 *            - 待检查的用户
	 * @return 用户验证Email获得积分记录是否已存在
	 */
	private boolean isVerifyEmailPointsLogExists(User user) {
		PointsRule rule = pointsService
				.getPointsRuleUsingSlug("create-account");
		return pointsService.isPointsLogExists(user, rule);
	}

	/**
	 * 在创建产品之后扣除响应积分.
	 * 
	 * @param proceedingJoinPoint
	 *            - ProceedingJoinPoint对象
	 * @param request
	 *            - HttpRequest对象
	 * @return 一个包含若干标志位的JSON对象
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	@Around(value = "execution(* com.trunkshell.testzilla.controller.AccountsController.createProductAction(..)) && args(.., request)")
	public @ResponseBody HashMap<String, Boolean> getReputationAfterCreatingProduct(
			ProceedingJoinPoint proceedingJoinPoint, HttpServletRequest request)
			throws Throwable {
		HashMap<String, Boolean> result = new HashMap<String, Boolean>();
		PointsRule rule = pointsService
				.getPointsRuleUsingSlug("create-product");

		HttpSession session = request.getSession();
		User user = HttpSessionParser.getCurrentUser(session);
		long credits = pointsService.getCreditsUsingUser(user);

		if (credits + rule.getCredit() < 0) {
			// Didn't have enough credits to create products
			result.put("isSuccessful", false);
			result.put("hasEnoughCredits", false);
		} else {
			result = (HashMap<String, Boolean>) proceedingJoinPoint.proceed();
			if (result.get("isSuccessful")) {
				appendPointsLogs(user, rule, null);
			}
		}
		return result;
	}

	/**
	 * 在Bug状态改变时授予用户相应积分和威望.
	 * 
	 * @param proceedingJoinPoint
	 *            - ProceedingJoinPoint对象
	 * @param bugId
	 *            - Bug的唯一标识符
	 * @return 一个包含若干标志位的JSON对象
	 * @throws Throwable
	 */
	@Around(value = "execution(* com.trunkshell.testzilla.controller.AccountsController.editBugAction(..)) && args(bugId, ..)")
	public @ResponseBody HashMap<String, Boolean> getCreditForBugReporter(
			ProceedingJoinPoint proceedingJoinPoint, long bugId)
			throws Throwable {
		Bug bug = bugService.getBugUsingBugId(bugId);
		@SuppressWarnings("unchecked")
		HashMap<String, Boolean> result = (HashMap<String, Boolean>) proceedingJoinPoint
				.proceed();

		if (result.get("isSuccessful")) {
			BugStatus originalBugStatus = bug.getBugStatus();
			BugStatus currentBugStatus = bugService.getBugUsingBugId(bugId)
					.getBugStatus();
			String pointsRuleSlug = String.format("edit-bug-status-%s-%s",
					new Object[] { originalBugStatus.getBugStatusSlug(),
							currentBugStatus.getBugStatusSlug() });
			PointsRule rule = pointsService
					.getPointsRuleUsingSlug(pointsRuleSlug);
			User hunter = bug.getHunter();

			if (!originalBugStatus.equals(currentBugStatus) && rule != null) {
				appendPointsLogs(hunter, rule, Long.toString(bugId));
			}
		}
		return result;
	}

	/**
	 * 追加积分日志.
	 * 
	 * @param user
	 *            - 被追加的用户
	 * @param rule
	 *            - 匹配的积分规则对象
	 */
	private void appendPointsLogs(User user, PointsRule rule, String meta) {
		boolean isSuccessful = true;
		if (user == null || rule == null) {
			isSuccessful = false;
		}
		isSuccessful = pointsService.appendPointsLogs(user, rule, meta);

		if (!isSuccessful) {
			logger.error(String.format(
					"Failed to log PointsRule{%s} for User{%s}.", new Object[] {
							rule, user }));
		}
	}

	/**
	 * 自动注入的PointsService对象.
	 */
	@Autowired
	private PointsService pointsService;

	/**
	 * 自动注入的BugService对象.
	 */
	@Autowired
	private BugService bugService;

	/**
	 * 日志记录器.
	 */
	private Logger logger = LogManager.getLogger(PointsAspect.class);
}
