package de.hska.vislab.webshop.ui.controller;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import de.hska.vislab.webshop.ui.manager.ProductManager;
import de.hska.vislab.webshop.ui.manager.impl.ProductManagerImpl;
import de.hska.vislab.webshop.ui.model.User;

public class DeleteProductAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3666796923937616729L;

	private Long id;

	public String execute() throws Exception {

		String res = "input";

		Map<String, Object> session = ActionContext.getContext().getSession();
		User user = (User) session.get("webshop_user");

		if (user != null && (user.role.typ.equals("admin"))) {
			ProductManager productManager = new ProductManagerImpl();
			productManager.deleteProductById(id);
			res = "success";
		}

		return res;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
