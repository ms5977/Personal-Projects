package in.sp.main.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "customer_enquiry")
public class CustEnquiry
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "phoneno")
	private String phoneno ;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "interested_course")
	private String interestedcourse;
	
	@Column(name = "discussion")
	private String discussion;
	
	@Column(name = "enquiry_type")
	private String enquirytype;
	
	@Column(name = "status")
	private String status;
	
	
	@Column(name = "callto")
	private String callto;
	
	@Column(name = "enquiry_date")
	private String enquirydate;
	
	@Column(name = "enquirytime")
	private String enquirytime;
	
	@Column(name = "emp_email")
	private String empemail;

	public void setId(int id) {
		this.id = id;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setInterestedcourse(String interestedcourse) {
		this.interestedcourse = interestedcourse;
	}

	public void setDiscussion(String discussion) {
		this.discussion = discussion;
	}

	public void setEnquirytype(String enquirytype) {
		this.enquirytype = enquirytype;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCallto(String callto) {
		this.callto = callto;
	}

	public void setEnquirydate(String enquirydate) {
		this.enquirydate = enquirydate;
	}

	public void setEnquirytime(String enquirytime) {
		this.enquirytime = enquirytime;
	}

	public void setEmpemail(String empemail) {
		this.empemail = empemail;
	}
//------------Getter-----------------------
	public int getId() {
		return id;
	}

	public String getPhoneno() {
		return phoneno;
	}

	public String getName() {
		return name;
	}

	public String getInterestedcourse() {
		return interestedcourse;
	}

	public String getDiscussion() {
		return discussion;
	}

	public String getEnquirytype() {
		return enquirytype;
	}

	public String getStatus() {
		return status;
	}

	public String getCallto() {
		return callto;
	}

	public String getEnquirydate() {
		return enquirydate;
	}

	public String getEnquirytime() {
		return enquirytime;
	}

	public String getEmpemail() {
		return empemail;
	}
	
	
	
}
