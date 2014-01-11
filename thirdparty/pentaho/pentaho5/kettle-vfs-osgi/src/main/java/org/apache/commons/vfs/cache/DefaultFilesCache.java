/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.vfs.cache;

import org.apache.commons.vfs.FileName;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystem;

import java.util.HashMap;
import java.util.Map;

/**
 * A {@link org.apache.commons.vfs.FilesCache} implementation.<br>
 * This implementation caches every file for the complete lifetime of the used
 * {@link org.apache.commons.vfs.FileSystemManager}.
 *
 * @author <a href="mailto:imario@apache.org">Mario Ivankovits</a>
 * @version $Revision: 764356 $ $Date: 2009-04-13 00:06:01 -0400 (Mon, 13 Apr 2009) $
 */
public class DefaultFilesCache extends AbstractFilesCache
{

    /** The FileSystem cache */
    private final Map filesystemCache = new HashMap(10);

    public void putFile(final FileObject file)
    {
        Map files = getOrCreateFilesystemCache(file.getFileSystem());
        files.put(file.getName(), file);
    }

    public FileObject getFile(final FileSystem filesystem, final FileName name)
    {
        Map files = getOrCreateFilesystemCache(filesystem);
        return (FileObject) files.get(name);
    }

    public void clear(FileSystem filesystem)
    {
        Map files = getOrCreateFilesystemCache(filesystem);
        files.clear();
    }

    protected Map getOrCreateFilesystemCache(FileSystem filesystem)
    {
        Map files = (Map) filesystemCache.get(filesystem);
        if (files == null)
        {
            files = new HashMap();
            filesystemCache.put(filesystem, files);
        }

        return files;
    }

    public void close()
    {
        super.close();

        filesystemCache.clear();
    }

    public void removeFile(FileSystem filesystem, FileName name)
    {
        Map files = getOrCreateFilesystemCache(filesystem);
        files.remove(name);
    }

    public void touchFile(FileObject file)
    {
    }
}
