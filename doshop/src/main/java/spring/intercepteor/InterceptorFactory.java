package spring.intercepteor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.intercepteor.admin.ConditionForAdminEntry;
import spring.intercepteor.admin.EnterAdminPage;
import spring.intercepteor.all.AutoSignin;
import spring.intercepteor.all.SigninSessionUpdate;

@Component
public class InterceptorFactory {
	//실험
	@Autowired
	SigninSessionUpdate signinSessionUpdate;
	@Autowired
	AutoSignin autoSignin;

	public SigninSessionUpdate getSigninSessionUpdate() {
		return signinSessionUpdate;
	}
	
	public AutoSignin getAutoSignin() {
		return autoSignin;
	}

	public EnterAdminPage getEnterAdminPage(ConditionForAdminEntry conditionForAdminEntry) {
		EnterAdminPage enterAdminPage = new EnterAdminPage(conditionForAdminEntry);
		return enterAdminPage;
	}
}
