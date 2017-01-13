package de.hska.vislab.webshop.ui.controller;

import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import de.hska.vislab.webshop.ui.manager.CategoryManager;
import de.hska.vislab.webshop.ui.manager.impl.CategoryManagerImpl;
import de.hska.vislab.webshop.ui.model.Category;
import de.hska.vislab.webshop.ui.model.User;

public class DeleteCategoryAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1254575994729199914L;
	
	private Long catId;
	private List<Category> categories;

	public String execute() throws Exception {
		
		String res = "input";
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		User user = (User) session.get("webshop_user");
		
		if(user != null && (user.role.typ.equals("admin"))) {

			// Helper inserts new Category in DB:
			CategoryManager categoryManager = new CategoryManagerImpl();
		
			categoryManager.delCategoryById(catId);

			categories = categoryManager.getCategories();
				
			res = "success";

		}
		
		return res;
		
	}

	public Long getCatId() {
		return catId;
	}

	public void setCatId(Long catId) {
		this.catId = catId;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
}
