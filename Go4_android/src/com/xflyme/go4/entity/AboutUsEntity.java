package com.xflyme.go4.entity;

public class AboutUsEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AboutUsItem data;

	public AboutUsItem getData() {
		return data;
	}

	public void setData(AboutUsItem data) {
		this.data = data;
	}

	public class AboutUsItem {

		private String name;
		private String logoUrl;
		private String address;
		private String enquiryPhone;
		private String securityPhone;
		private String email;
		private String introduction;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getLogoUrl() {
			return logoUrl;
		}
		public void setLogoUrl(String logoUrl) {
			this.logoUrl = logoUrl;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getEnquiryPhone() {
			return enquiryPhone;
		}
		public void setEnquiryPhone(String enquiryPhone) {
			this.enquiryPhone = enquiryPhone;
		}
		public String getSecurityPhone() {
			return securityPhone;
		}
		public void setSecurityPhone(String securityPhone) {
			this.securityPhone = securityPhone;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getIntroduction() {
			return introduction;
		}
		public void setIntroduction(String introduction) {
			this.introduction = introduction;
		}
		
		
	}
}
