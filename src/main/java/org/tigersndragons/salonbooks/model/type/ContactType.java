package org.tigersndragons.salonbooks.model.type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema="SALONBOOKS",name="CONTACT_TYPE")
@AttributeOverrides({
@AttributeOverride(name="id", column=@Column(name="ID")),
@AttributeOverride(name="name", column=@Column(name="NAME"))
})
public class ContactType extends BaseLookup {
	
//	public ContactType (Long id, String name){
//		this.setId(id);
//		this.setName(name);
//	}
	/*EMAIL,
	MOBILE_PHONE,
	HOME_PHONE,
	WORK_PHONE,
	FACEBOOK,
	TWITTER,
	WEB;
*/
}
