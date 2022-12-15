package com.world_cup_2022.projectW.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.world_cup_2022.projectW.model.MemberVO;
import com.world_cup_2022.projectW.service.MyPageService;

@Controller
public class MyPageController {
	@Autowired
	 private MyPageService service;
	
	/* ----------------------------------*/
	/* 회원정보보기 */
	
	// http://localhost:8080/myPage/detailViewMyPage/{memId}
	@RequestMapping("/myPage/main")
	public String index() {

		return "myPage/main";
	}
	
	@RequestMapping("/myPage/detailViewMyPage/{memId}")
    public String detailViewProduct(@PathVariable String memId, Model model) {
    MemberVO mem= service.detailViewMyPage(memId);
    model.addAttribute("mem", mem);
    	
    	return "myPage/info";
    }
	
	// 회원 탈퇴 get
		@RequestMapping("/myPage/myPageDeleteView")
		public String memberDeleteView() throws Exception{
			return "myPage/myPageDeleteView";
		}
		
		// 회원 탈퇴 post
		@RequestMapping("/myPage/myPageDelete")
		public String myPageDelete(MemberVO vo, HttpSession session, RedirectAttributes rttr) throws Exception{
			
			// 세션에 있는 member를 가져와 member변수에 넣어줍니다.
			MemberVO mem = (MemberVO) session.getAttribute("mem");
			// 세션에있는 비밀번호
			String sessionPass = mem.getMemPwd();
			// vo로 들어오는 비밀번호
			String voPass = vo.getMemPwd();
			
			if(!(sessionPass.equals(voPass))) {
				rttr.addFlashAttribute("msg", false);
				return "redirect:/myPage/myPageDeleteView";
			}
			service.memberDelete(vo);
			session.invalidate();
			return "redirect:/";
		}
}