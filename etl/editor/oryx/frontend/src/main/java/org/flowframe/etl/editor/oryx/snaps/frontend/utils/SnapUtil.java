package org.flowframe.etl.editor.oryx.snaps.frontend.utils;


import org.eclipse.virgo.util.osgi.manifest.BundleManifest;
import org.osgi.framework.Bundle;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Mduduzi on 9/9/13.
 */
public class SnapUtil {

    public static final String HEADER_SNAP_HOST = "Snap-Host";

    public static final String HEADER_SNAP_CONTEXT_PATH = "Snap-ContextPath";

    private static final String PATH_ELEMENT_SEPARATOR = "/";

    private SnapUtil() {
    }

    public static String determineSnapContextPath(HttpServletRequest request) {
        // TODO Move to HostUtils, or similar
        String result;
        String includeServletPath = (String)request.getAttribute("javax.servlet.include.servlet_path");
        if (includeServletPath != null) {
            result = includeServletPath;
        } else {
            result = request.getServletPath();
        }
        int index = result.indexOf(PATH_ELEMENT_SEPARATOR, 1);
        if (index > -1) {
            result = result.substring(0, index);
        }
        return result;
    }

    public static boolean hasSnapHostHeader(BundleManifest manifest) {
        return manifest.getHeader(HEADER_SNAP_HOST) != null;
    }

/*    public static SnapHostDefinition getSnapHostHeader(BundleManifest manifest) {
        String header = manifest.getHeader(HEADER_SNAP_HOST);
        return (header == null ? null : SnapHostDefinition.parse(header));
    }*/

    public static String getSnapContextPath(Bundle bundle) {
        String contextPath = (String) bundle.getHeaders().get(HEADER_SNAP_CONTEXT_PATH);
        if(contextPath == null) {
            contextPath = generateDefaultContextPath(bundle);
        }
        return contextPath;
    }

    /**
     * Catenate the host and snap context paths, <i>unless</i> host context path ends with a path separator.<br/>
     * <code>null</code> {@link String}s are converted to the empty string <code>""</code>.
     * @param hostContextPath the host context path
     * @param snapContextPath the snap context path
     * @return the concatenated host and snap context path (with removal of extra path separator)
     */
    public static String boundContextPath(String hostContextPath, String snapContextPath) {
        if (null==hostContextPath) {
            return (null==snapContextPath ? "" : snapContextPath);
        }
        if (null==snapContextPath) {
            return hostContextPath;
        }
        if (hostContextPath.endsWith(PATH_ELEMENT_SEPARATOR)) {
            return hostContextPath.substring(0,hostContextPath.length()-1) + snapContextPath;
        } else {
            return hostContextPath + snapContextPath;
        }
    }

    private static String generateDefaultContextPath(Bundle bundle) {
        return getBaseName(bundle.getLocation());
    }

    static String getBaseName(String path) {
        String base = path;
        if(base.endsWith("/")) {
            base = base.substring(0, base.length() - 1);
        }
        base = stripScheme(base);
        base = stripQuery(base);
        base = stripLeadingPathElements(base);
        base = stripExtension(base);
        return base;
    }

    private static String stripExtension(String base) {
        int index;
        index = base.lastIndexOf(".");
        if (index > -1) {
            base = base.substring(0, index);
        }
        return base;
    }

    private static String stripLeadingPathElements(String base) {
        int index = base.lastIndexOf("/");
        if (index > -1) {
            base = base.substring(index + 1);
        }
        return base;
    }

    private static String stripQuery(String path) {
        String result = path;
        int index = result.lastIndexOf("?");
        if(index > -1) {
            result = result.substring(0, index);
        }
        return result;
    }

    private static String stripScheme(String path) {
        String result = path;
        int index = result.indexOf(":");
        if(index > - 1 && index < result.length()) {
            result = result.substring(index + 1);
        }
        return result;
    }
}
