package member;

import java.util.List;

import org.apache.struts.chain.commands.servlet.CreateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.dao.MemberDAO;
import database.dao.TemporaryMemberDAO;
import database.vo.MemberVO;
import database.vo.TemporaryMemberVO;

@Service
public class MemberService {
	@Autowired
	MemberDAO memberDAO;
	@Autowired
	TemporaryMemberDAO temporaryMemberDAO;

	public void registMember(String email) {
		List<TemporaryMemberVO> temporaryMemberVOList = temporaryMemberDAO.selectByEmailInExpiration(email);
		if(temporaryMemberVOList.isEmpty()) {
			return;
		}
		TemporaryMemberVO temporaryMemberVO = temporaryMemberVOList.get(0);
		memberDAO.insert(temporaryMemberVO);
		temporaryMemberDAO.deleteByEmailInExpiration(temporaryMemberVO.getEmail());
	}
	
	public boolean emailExists(String email) {
		MemberVO member =  memberDAO.selectByEmail(email);
		if(member == null) {
			return false;
		}else {
			return true;
		}
	}
}
