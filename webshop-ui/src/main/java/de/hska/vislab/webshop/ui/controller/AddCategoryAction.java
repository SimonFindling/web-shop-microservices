package de.hska.vislab.webshop.ui.controller;

import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import de.hska.vislab.webshop.ui.manager.CategoryManager;
import de.hska.vislab.webshop.ui.manager.impl.CategoryManagerImpl;
import de.hska.vislab.webshop.ui.model.Category;
import de.hska.vislab.webshop.ui.model.User;

public class AddCategoryAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6704600867133294378L;
	
	private String newCatName = null;
	
	private List<Category> categories;
	
	User user;

	public String execute() throws Exception {

		String res = "input";

		Map<String, Object> session = ActionContext.getContext().getSession();
		user = (User) session.get("webshop_user");
		if(user != null && (user.role.typ.equals("admin"))) {
			CategoryManager categoryManager = new CategoryManagerImpl();
			// Add category
			categoryManager.addCategory(newCatName);
			
			// Go and get new Category list
			this.setCategories(categoryManager.getCategories());
			
			res = "success";
		}
		
		return res;
	
	}
	
	@Override
	public void validate(){
		if (getNewCatName().length() == 0) {
			addActionError(getText("error.catname.required"));
		}
		// Go and get new Category list
		CategoryManager categoryManager = new CategoryManagerImpl();
		this.setCategories(categoryManager.getCategories());
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	public String getNewCatName() {
		return newCatName;
	}

	public void setNewCatName(String newCatName) {
		this.newCatName = newCatName;
	}
}
