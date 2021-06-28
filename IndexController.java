package jp.co.internous.origami.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.internous.origami.model.domain.MstCategory;
import jp.co.internous.origami.model.domain.MstProduct;
import jp.co.internous.origami.model.form.SearchForm;
import jp.co.internous.origami.model.mapper.MstCategoryMapper;
import jp.co.internous.origami.model.mapper.MstProductMapper;
import jp.co.internous.origami.model.session.LoginSession;

@Controller
@RequestMapping("/origami")
public class IndexController {
	
	@Autowired
	private MstCategoryMapper categoryMapper;
	
	@Autowired
	private MstProductMapper productMapper;
	
	@Autowired
	private LoginSession loginSession;
	
	@RequestMapping("/")
	public String index(Model m) {
		
		if (!loginSession.getLogined() && loginSession.getTmpUserId() == 0) {
			int tmpUserId = (int)(Math.random() * 1000000000 * -1);
			// 仮ユーザーIDが9桁になるまで10倍する
			while (tmpUserId > -100000000) {
				tmpUserId *= 10;
			}
			loginSession.setTmpUserId(tmpUserId);
		}
		
		// カテゴリ情報
		List<MstCategory> categories = categoryMapper.find();
		m.addAttribute("categories", categories);
		
		// 商品情報
		List<MstProduct> products = productMapper.find();
		m.addAttribute("products", products);
		
		m.addAttribute("selected", 0);
		m.addAttribute("loginSession",loginSession);
		
		return "index";
	}
	
	@RequestMapping("/searchItem")
	public String index(SearchForm f, Model m) {
		
		List<MstProduct> products = null;
		
		String keywords = f.getKeywords().replaceAll("　", " ").replaceAll("\\s{2,}", " ").trim();
		if (f.getCategory() == 0) {
			//カテゴリー選択なし
			products = productMapper.findByProductName(keywords.split(" "));
		} else {
			//カテゴリー選択あり
			products = productMapper.findByCategoryAndProductName(f.getCategory(), keywords.split(" "));
		}
		
		List<MstCategory> categories = categoryMapper.find();
		m.addAttribute("products", products);
		m.addAttribute("categories", categories); 
		
		m.addAttribute("selected", f.getCategory()); 
		m.addAttribute("loginSession",loginSession);
		
		//検索フォーム
		m.addAttribute("keywords", keywords);
		
		return "index";
	}
	
}
