package com.bocobi2.rencontre.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.TypeMeeting;

public interface TypeMeetingRepository extends MongoRepository<TypeMeeting, String>  {
	 
	//public List<TypeMeeting> findByMeetingName(String meetingName);
	public TypeMeeting findByMeetingName(String meetingName);

}
