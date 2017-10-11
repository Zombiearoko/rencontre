package com.bocobi2.rencontre.repositories;

import java.io.InputStream;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bocobi2.rencontre.model.Country;
import com.bocobi2.rencontre.model.FileStorage;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;

public interface FileStorageRepository  {
//extends MongoRepository<FileStorage, String>

	public String store(InputStream inputStream, String fileName, String contentType, DBObject metaData);

	public GridFSDBFile retrive(String fileName);

	public GridFSDBFile getById(String id);

	public GridFSDBFile getByFilename(String filename);

	public List findAll();
}
