package spring.intercepteor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.intercepteor.admin.ConditionForAdminEntry;
import spring.intercepteor.admin.EnterAdminPage;
import spring.intercepteor.all.SigninSessionUpdate;

@Component
public class InterceptorFactory {
	@Autowired
	SigninSessionUpdate signinSessionUpdate;
	
	public SigninSessionUpdate getSigninSessionUpdate() {
		return signinSessionUpdate;
	}
	
	public EnterAdminPage getEnterAdminPage(ConditionForAdminEntry conditionForAdminEntry) {
		EnterAdminPage enterAdminPage = new EnterAdminPage(conditionForAdminEntry);
		return enterAdminPage;
	}
}
