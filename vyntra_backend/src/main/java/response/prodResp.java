package response;

import java.util.List;

import Model.Product;

public class prodResp {
	private String status;
	private String message;
	private String AUTH_TOKEN;
	private List<Product> oblist;
	private Product ob;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAUTH_TOKEN() {
		return AUTH_TOKEN;
	}

	public void setAUTH_TOKEN(String aUTH_TOKEN) {
		AUTH_TOKEN = aUTH_TOKEN;
	}

	public List<Product> getOblist() {
		return oblist;
	}

	public void setOblist(List<Product> oblist) {
		this.oblist = oblist;
	}
	
	public void setOb(Product ob) {
		this.ob = ob;
	}
}