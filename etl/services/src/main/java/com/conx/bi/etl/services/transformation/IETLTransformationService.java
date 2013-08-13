package com.conx.bi.etl.services.transformation;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public interface IETLTransformationService {
	public Map<String,Object> transform(Map<String,Object> params) throws Exception;
	
	public OutputStream transform(String graphName, InputStream inputStream) throws Exception;
	
	public void transform(String graphName, File inputFile, File outputFile) throws Exception;
}
