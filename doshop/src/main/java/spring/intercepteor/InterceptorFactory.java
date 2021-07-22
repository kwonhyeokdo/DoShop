package spring.intercepteor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.intercepteor.admin.ConditionForAdminEntry;
import spring.intercepteor.admin.EnterAdminPage;
import spring.intercepteor.all.AuthenticationSessionCreator;
import spring.intercepteor.all.AutoSignin;
import spring.intercepteor.all.RequiredSignin;
import spring.intercepteor.all.SigninSessionUpdate;

@Component
public class InterceptorFactory {
	@Autowired
	SigninSessionUpdate signinSessionUpdate;
	@Autowired
	AutoSignin autoSignin;
	@Autowired
	RequiredSignin requiredSignin;
	@Autowired
	AuthenticationSessionCreator authenticationSessionCreator;

	public SigninSessionUpdate getSigninSessionUpdate() {
		return signinSessionUpdate;
	}
	
	public AutoSignin getAutoSignin() {
		return autoSignin;
	}

	public RequiredSignin getRequiredSignin() {
		return requiredSignin;
	}

	public AuthenticationSessionCreator getAuthenticationSessionCreator() {
		return authenticationSessionCreator;
	}

	public EnterAdminPage getEnterAdminPage(ConditionForAdminEntry conditionForAdminEntry) {
		EnterAdminPage enterAdminPage = new EnterAdminPage(conditionForAdminEntry);
		return enterAdminPage;
	}
}
