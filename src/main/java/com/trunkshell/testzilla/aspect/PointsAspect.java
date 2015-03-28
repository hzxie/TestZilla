package com.trunkshell.testzilla.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.trunkshell.testzilla.model.PointsRule;
import com.trunkshell.testzilla.model.User;
import com.trunkshell.testzilla.service.PointsService;
import com.trunkshell.testzilla.util.HttpSessionParser;

/**
 * 积分操作的切片.
 * 在触发某些操作时完成对应的积分操作.
 * @author Xie Haozhe
 */
@Aspect
public class PointsAspect {
	/**
	 * 在用户验证电子邮件后根据规则予以积分奖励.
	 * @return 包含验证电子邮件页面信息的ModelAndView对象
	 */
	@Around(value = "execution(* com.trunkshell.testzilla.controller.AccountsController.verifyEmailView(..)) && args(.., request)")
	public ModelAndView getCreditAfterVerifyEmail(ProceedingJoinPoint proceedingJoinPoint, HttpServletRequest request) 
			throws Throwable {
		HttpSession session = request.getSession();
		User originalUser = HttpSessionParser.getCurrentUser(session);
    	ModelAndView view = (ModelAndView) proceedingJoinPoint.proceed();
    	User currentUser = HttpSessionParser.getCurrentUser(session);
    	
    	if ( !originalUser.isEmailVerified() && currentUser.isEmailVerified() && 
    		 !isVerifyEmailPointsLogExists(currentUser) ) {
    		PointsRule rule = pointsService.getPointsRuleUsingSlug("create-account");
    		String meta = currentUser.getEmail();
    		appendPointsLogs(currentUser, rule, meta);
    	}
    	return view;
	}
	
	/**
	 * @param user
	 * @return
	 */
	private boolean isVerifyEmailPointsLogExists(User user) {
		PointsRule rule = pointsService.getPointsRuleUsingSlug("create-account");
		return pointsService.isPointsLogExists(user, rule);
	}
	
    /**
     * 追加积分日志.
     * @param user - 被追加的用户
     * @param rule - 匹配的积分规则对象
     */
    private void appendPointsLogs(User user, PointsRule rule, String meta) {
    	boolean isSuccessful = true;
    	if ( user == null || rule == null ) {
    		isSuccessful = false;
    	}
    	isSuccessful = pointsService.appendPointsLogs(user, rule, meta);
    	
    	if ( !isSuccessful ) {
    		logger.error(String.format("Failed to log PointsRule{%s} for User{%s}.", new Object[] {rule, user}));
    	}
    }
    
	/**
     * 自动注入的PointsService对象.
     */
    @Autowired
    private PointsService pointsService;
    
    /**
     * 日志记录器.
     */
    private Logger logger = LogManager.getLogger(PointsAspect.class);
}
