package org.tigersndragons.salonbooks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.ObjectUtils;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@MappedSuperclass
public abstract class SalonObject implements Entity, Serializable {

	private static final long serialVersionUID = 1L;
	protected Long id;
	protected DateTime createDate;
	protected DateTime updateDate;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SalonObject 
				&& obj !=null){
			return ObjectUtils.equals(this.id, ((SalonObject) obj).getId())	;
		}
		return false;
	}

	@Column(name="CREATE_DATE")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(DateTime dateTime) {
		this.createDate = dateTime;
	}

	@Column(name="UPDATE_DATE")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(DateTime updateDate) {
		this.updateDate = updateDate;
	}
//	protected List<SalonMatchable> getMatchValues() throws NotMatchableException {
//		return null;
//	}
	public boolean matches(Entity entity) {
		if(entity == null){
			//A null here would be odd but this is a safety measure
			return false;
		}
	
		return false;
	}
}
