package com.dingdang.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class) 
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Log {
	private long id;
	private String name;
	private String desc;
	private Date createdTime;
	private Type type;
	private List<Child> children;
	//@JsonCreator
	/*public Log(@JsonProperty("full_name") String name,@JsonProperty("desc") String desc,long timestamp){
		this.createdTime=new Date(timestamp);
		this.name=name;
		this.desc=desc;
	}*/
}
