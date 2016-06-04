package org.tigersndragons.salonbooks.model.factory;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.tigersndragons.salonbooks.model.Contact;
import org.tigersndragons.salonbooks.model.type.ContactType;

public class ContactFactory {
	
	public static Contact createContact(String label, ContactType type){
		return createContactForType(type, label);
	}
	public static Contact defaultContact(){
		return createContactForType(null, null);
	}
	
	private static Contact defaultEmailContact(String label){	
		ContactType ct = new ContactType();
		ct.setId(6L);
		ct.setName("EMAIL");		
		Contact newOne =   defaultContact();
		if (StringUtils.isEmpty(label))
		newOne.setLabel("mailto:handle@default.com");
		else 
			newOne.setLabel(label);
		newOne.setFormat("mailto:"+label);
		newOne.setContactType(ct);
		newOne.setIsURL("Y");
		return  newOne;
	}
	
	private static Contact defaultTwitterContact(String label){

		ContactType ct = new ContactType();
		ct.setId(4L);
		ct.setName("TWITTER");
		Contact newOne =   defaultContact();
		if (StringUtils.isEmpty(label)){
			newOne.setLabel("@default");
		}else if (label.charAt(0)!='@'){
			newOne.setLabel("@"+ label);
		}else		{
			newOne.setLabel(label);
		}
		newOne.setFormat("http://twitter.com/"+label.substring(0));

		newOne.setContactType(ct);
		newOne.setIsURL("Y");
		return newOne;
	}
	
	private static Contact defaultHomePhoneContact(String label){

		ContactType ct = new ContactType();
		ct.setId(1L);
		ct.setName("HOME_PHONE");
		Contact newOne =   defaultContact();	
		if (StringUtils.isEmpty(label))
			newOne.setLabel("0001114444");
		else 
			newOne.setLabel(label);		
		newOne.setContactType(ct);
		newOne.setIsURL("N");
		newOne.setFormat("PHONE:" + label);
		return newOne;
	}
	
	private static Contact createContactForType(ContactType type, String label) {
		if (type ==null 
				|| type.getId()==null)		
			return defaultMobileContact("");
		
		//Contact defaultContact = null  ;
		if  (type.getId().intValue()==0){
			return defaultMobileContact(label);
		}else if (type.getId().intValue()==1){
			return defaultHomePhoneContact( label);
		}else if (type.getId().intValue()==4){
			return defaultTwitterContact( label);
		}else if (type.getId().intValue()==6){
			return defaultEmailContact( label);
		}
		
		return new Contact();
	}
	
	private static Contact defaultMobileContact( String label) {
		Contact newOne = new Contact();
		newOne.setCreateDate(new DateTime());
		newOne.setUpdateDate(new DateTime());

		ContactType ct = new ContactType();
		ct.setId(0L);
		ct.setName("MOBILE_PHONE");
		
		newOne.setContactType(ct);
		if (StringUtils.isEmpty(label) ){
			newOne.setLabel("3196210000");
		}else{
			newOne.setLabel(label);
		}
		newOne.setIsActive("Y");
		newOne.setIsURL("N");

		newOne.setFormat("PHONE:" + label);
		return newOne;
	}
	
}
