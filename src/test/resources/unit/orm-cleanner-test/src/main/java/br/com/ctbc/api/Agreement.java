package br.com.ctbc.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.ctbc.maestro.vantive.domain.agreementType.AgreementType;
import br.com.ctbc.maestro.vantive.domain.customer.Customer;

@Entity
@Table(name = "SW_AGREEMENT")
public class Agreement implements br.com.ctbc.maestro.vantive.api.customer.Agreement {

	private static final long serialVersionUID = 4593352081661455384L;

	@Id
	@Column(name="SWAGREEMENTID")
	private long id;
	
	@Column(name="SWCUSTOMERID")
	private Long customerId;

	@Column(name="CTBCNUMTELEFONE")
	private String numPhone;

	@Column(name="CTBCDDDTELEFONE")
	private String dddPhone;
	
	@ManyToOne
	@JoinColumn(name = "SWCUSTOMERID", insertable=false, updatable=false)
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "CTBCTIPOAGREEMENTID", insertable=false, updatable=false )
	private AgreementType agreementType;
	
	@Column(name="SWSTATUS")
	private String status;
	
	@Column(name="CTBCNOMECARTAOLIGACAO")
	private String phoneCardName;
	
	@Column(name="CTBCFORMAPAGTO")
	private String payOption;	
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getNumPhone() {
		return this.numPhone;
	}

	public void setNumPhone(String numPhone) {
		this.numPhone = numPhone;
	}

	public String getDddPhone() {
		return this.dddPhone;
	}

	public void setDddPhone(String dddPhone) {
		this.dddPhone = dddPhone;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public AgreementType getAgreementType() {
		return agreementType;
	}

	public void setAgreementType(AgreementType agreementType) {
		this.agreementType = agreementType;
	}

	public String getPhoneCardName() {
		return phoneCardName;
	}

	public void setPhoneCardName(String phoneCardName) {
		this.phoneCardName = phoneCardName;
	}

	public String getPayOption() {
		return payOption;
	}

	public void setPayOption(String payOption) {
		this.payOption = payOption;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
