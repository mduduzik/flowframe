package org.apache.commons.test;

import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.impl.StandardFileSystemManager;

public class TestStandardFileSystemManager {
	private StandardFileSystemManager fsm = null;
	
	public void init() throws FileSystemException {
        // get a full blown, fully functional manager
        fsm = new StandardFileSystemManager();
        fsm.init();		
	}
}
