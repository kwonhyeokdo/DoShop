package member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.dao.MemberDAO;
import database.vo.MemberVO;
import database.vo.TemporaryMemberVO;

@Service
public class MemberService {
	MemberDAO memberDAO;
	
	@Autowired
	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	public void registMember(TemporaryMemberVO temporaryMemberVO) {
		memberDAO.insert(temporaryMemberVO);
	}
	
	public boolean checkDuplicateEmail(String email) {
		MemberVO member =  memberDAO.selectByEmail(email);
		if(member == null) {
			return false;
		}else {
			return true;
		}
	}
	
	public boolean matchEmail(String inputEmail) {
		return (memberDAO.selectByEmail(inputEmail) == null) ? false : true;
	}
}
