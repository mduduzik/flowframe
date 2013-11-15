/***************************************
 * Copyright (c) 2008
 * Philipp Berger 2009
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 ****************************************/

package org.oryxeditor.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.FileCopyUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ETLEditorHandler extends HttpServlet {

    /**
     *
     */
    private static final String defaultSS="stencilsets/etl/etl1.0.json";
    private static final long serialVersionUID = 1L;
    private Collection<String> availableProfiles;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        availableProfiles=getAvailableProfileNames();
//		if(availableProfiles.size()==0)
//			 defaultHandlerBehaviour();
        List<String> profiles= Arrays.asList("etl");
/*        String[] urlSplitted=request.getRequestURI().split(";");
        ArrayList<String> profiles= new ArrayList<String>();
        if (urlSplitted.length>1){
            for(int i=1;i<urlSplitted.length;i++){
                profiles.add(urlSplitted[i]);
            }
        }else{
            profiles.add("default");
        }
        if(!availableProfiles.containsAll(profiles)){
            //Some profiles not available
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Profile not found!");
            profiles.retainAll(availableProfiles);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }*/
        String defaultProfilePrefix = "etl";
        String sset=null;
        JSONObject conf= new JSONObject();
        try {
/*            conf = new JSONObject(FileCopyUtils.copyToString(new FileReader(this.getServletContext().
                    getRealPath("/profiles") + File.separator + profiles.get(0)
                    + ".conf")));*/
            conf = new JSONObject(FileCopyUtils.copyToString(new InputStreamReader(this.getServletContext().
                    getResourceAsStream("/profiles"+ File.separator + defaultProfilePrefix
                    + ".conf"))));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        sset=conf.optString("stencilset");
        if(sset==null || "".equals(sset))
            sset=defaultSS;
        boolean isTemplate = "true".equals(request.getParameter("template"));
        String userId = request.getParameter("userId");
        String extString="";
        JSONArray exts= conf.optJSONArray("stencilsetextension");
        if(exts==null)
            exts=new JSONArray();
        extString=exts.toString();
        String content =
                "<script type='text/javascript'>" +
                        "if(!ORYX) var ORYX = {};" +
                        "if(!ORYX.CONFIG) ORYX.CONFIG = {};" +
                        "ORYX.CONFIG.PLUGINS_CONFIG  =			ORYX.CONFIG.PROFILE_PATH + '"+defaultProfilePrefix+".xml';" +
                        "ORYX.CONFIG.SSET='" + sset +"';" +
                        "ORYX.CONFIG.SSEXTS=" + extString + ";"+
                        ((isTemplate) ? "ORYX.CONFIG[\"IS_TEMPLATE\"] = true;" : "ORYX.CONFIG[\"IS_TEMPLATE\"] = false;") +
                        ((userId != null) ? "ORYX.CONFIG[\"USER_ID\"] = \"" + userId + "\";" : "ORYX.CONFIG[\"USER_ID\"] = \"test\";") +
                        "if ('undefined' == typeof(window.onOryxResourcesLoaded)) { " +
                        "ORYX.Log.warn('No adapter to repository specified, default used. You need a function window.onOryxResourcesLoaded that obtains model-JSON from your repository');" +
                        "window.onOryxResourcesLoaded = function() {" +
                        "if (location.hash.slice(1).length == 0 || location.hash.slice(1).indexOf('new')!=-1){" +
                        "var stencilset=ORYX.Utils.getParamFromUrl('stencilset')?ORYX.Utils.getParamFromUrl('stencilset'):'"+sset+"';"+
                        "new ORYX.Editor({"+
                        "id: 'oryx-canvas123',"+
                        "stencilset: {"+
                        "url: '"+getRootPath(request)+"'+stencilset" +
                        "}" +
                        "})}"+
                        "else{" +
                        "ORYX.Editor.createByUrl('" + getRelativeServerPath(request) + "'+location.hash.slice(1)+'/json', {"+
                        "id: 'oryx-canvas123'" +
                        "});" +
                        "};" +
                        "}}" +
                        "</script>";
        response.setContentType("application/xhtml+xml");

        response.getWriter().println(this.getOryxModel(request,"ConXBI ETL Editor",
                content, this.getLanguageCode(request),
                this.getCountryCode(request), profiles));
        response.setStatus(200);
    }
    protected String getOryxModel(HttpServletRequest request, String title, String content,
                                  String languageCode, String countryCode, List<String> profiles) {

        return getOryxModel(request, title, content, languageCode, countryCode, "", profiles);
    }

    protected String getOryxModel(HttpServletRequest request, String title, String content,
                                  String languageCode, String countryCode, String headExtentions, List<String> profiles) {

        String languageFiles = "";
        String profileFiles="";

        languageCode = languageCode == null ? "en_us" : languageCode;

        final InputStream resourceAsStream = this.getResourceAsStream("/i18n/translation_" + languageCode + ".js");
        if (resourceAsStream != null) {
            languageFiles += "<script src=\"" + getRootPath(request)
                    + "i18n/translation_"+languageCode+".js\" type=\"text/javascript\" />\n";
        }

        if (new File(this.getRootPath(request)+"translation_" + languageCode+"_" + countryCode + ".js").exists()) {
            languageFiles += "<script src=\"" + getRootPath(request)
                    + "i18n/translation_" + languageCode+"_" + countryCode
                    + ".js\" type=\"text/javascript\" />\n";
        }
        for(String profile: profiles){
            profileFiles=profileFiles+ "<script src=\""+getRootPath(request)+"profiles/" + profile+".js\" type=\"text/javascript\" />\n";

        }

        String analytics = getServletContext().getInitParameter("ANALYTICS_SNIPPET");
        if (null == analytics) {
            analytics = "";
        }



        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\"\n"
                + "xmlns:b3mn=\"http://b3mn.org/2007/b3mn\"\n"
                + "xmlns:ext=\"http://b3mn.org/2007/ext\"\n"
                + "xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
                + "xmlns:atom=\"http://b3mn.org/2007/atom+xhtml\">\n"
                + "<head profile=\"http://purl.org/NET/erdf/profile\">\n"
                + "<title>" + title + "</title>\n"
                + "<!-- libraries -->\n"
                + "<script src=\"" + getSharedRootPath() + "lib/LABjs-2.0.3/LAB.min.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getSharedRootPath() + "lib/prototype-1.5.1.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getSharedRootPath() + "lib/path_parser.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getSharedRootPath() + "lib/ext-2.0.2/adapter/ext/ext-base.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getSharedRootPath() + "lib/ext-2.0.2/ext-all-debug.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getSharedRootPath() + "lib/ext-2.0.2/color-field.js\" type=\"text/javascript\" />\n"
                //--- Wiz
                + "<script src=\"" + getRootPath(request) + "lib/ext-2.0.2/ux/wiz/wiz-all-debug.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "lib/ext-2.0.2/ux/searchfield/js/Ext.form.SearchField.js\" type=\"text/javascript\" />\n"
                //-- DynaGrid -->
                + "<script src=\"" + getRootPath(request) + "lib/ext-2.0.2/ux/grid/js/Ext.ux.dynagrid.DynaGrid.js\" type=\"text/javascript\" />\n"
                //-- recordform -->
                + "<script src=\"" + getRootPath(request) + "lib/ext-2.0.2/ux/recordform/js/Ext.ux.util.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "lib/ext-2.0.2/ux/recordform/js/Ext.ux.grid.Search.js\" type=\"text/javascript\" />\n"
                //-- FileUploadField
                + "<script type=\"text/javascript\" src=\"" + getRootPath(request) + "lib/ext-2.0.2/ux/fileuploadfield/js/Ext.form.FileUploadField.js\"></script>\n"
                //-- FileUploader
                + "<script type=\"text/javascript\" src=\"" + getRootPath(request) + "lib/ext-2.0.2/ux/fileuploader/js/WebPage.js\"></script>\n"
                + "<script type=\"text/javascript\" src=\"" + getRootPath(request) + "lib/ext-2.0.2/ux/fileuploader/js/Ext.ux.form.BrowseButton.js\"></script>\n"
                + "<script type=\"text/javascript\" src=\"" + getRootPath(request) + "lib/ext-2.0.2/ux/fileuploader/js/Ext.ux.FileUploader.js\"></script>\n"
                + "<script type=\"text/javascript\" src=\"" + getRootPath(request) + "lib/ext-2.0.2/ux/fileuploader/js/Ext.ux.UploadPanel.js\"></script>\n"
                + "<script type=\"text/javascript\" src=\"" + getRootPath(request) + "lib/ext-2.0.2/ux/fileuploader/js/Ext.ux.FileTreeMenu.js\"></script>\n"
                + "<script type=\"text/javascript\" src=\"" + getRootPath(request) + "lib/ext-2.0.2/ux/fileuploader/js/Ext.ux.FileTreePanel.js\"></script>\n"
                + "<script type=\"text/javascript\" src=\"" + getRootPath(request) + "lib/ext-2.0.2/ux/fileuploader/js/Ext.ux.form.ThemeCombo.js\"></script>\n"
                + "<script type=\"text/javascript\" src=\"" + getRootPath(request) + "lib/ext-2.0.2/ux/fileuploader/js/Ext.ux.form.IconCombo.js\"></script>\n"
                + "<script type=\"text/javascript\" src=\"" + getRootPath(request) + "lib/ext-2.0.2/ux/fileuploader/js/Ext.ux.form.LangSelectCombo.js\"></script>\n"
                //-- Portal
                + "<script type=\"text/javascript\" src=\"" + getRootPath(request) + "lib/ext-2.0.2/ux/portal/js/Portal.js\"></script>\n"
                + "<script type=\"text/javascript\" src=\"" + getRootPath(request) + "lib/ext-2.0.2/ux/portal/js/PortalColumn.js\"></script>\n"
                + "<script type=\"text/javascript\" src=\"" + getRootPath(request) + "lib/ext-2.0.2/ux/portal/js/Portlet.js\"></script>\n"
                //-- RowActions
                + "<script type=\"text/javascript\" src=\"" + getRootPath(request) + "lib/ext-2.0.2/ux/rowactions/js/Ext.ux.Toast.js\"></script>\n"
                + "<script type=\"text/javascript\" src=\"" + getRootPath(request) + "lib/ext-2.0.2/ux/rowactions/js/Ext.ux.grid.RowActions.js\"></script>\n"
                // VrTabPanel extension
                + "<script type=\"text/javascript\" src=\"" + getRootPath(request) + "lib/ext-2.0.2/ux/verticaltab/js/ux_VerticalTabPanel.js\"></script>\n"
                // XCheckbox extension
                + "<script type=\"text/javascript\" src=\"" + getRootPath(request) + "lib/ext-2.0.2/ux/xcheckbox/js/Ext.ux.form.XCheckbox.js\"></script>\n"

                ///-- Inline style for resource imports
                + "<style>\n"
                + "@import url(\"" + getSharedRootPath() + "lib/ext-2.0.2/resources/css/ext-all.css\");\n"
                + "@import url(\"" + getSharedRootPath() + "lib/ext-2.0.2/resources/css/xtheme-gray.css\");\n"
                + "@import url(\"" + getRootPath(request) + "css/icons.css\");\n"
                //-- Wiz
                + "@import url(\"" + getRootPath(request) + "lib/ext-2.0.2/ux/wiz/resources/wiz.css\");\n"
                //-- FileuploadField
                + "@import url(\"" + getSharedRootPath() + "lib/ext-2.0.2/ux/fileuploadfield/css/fileuploadfield.css\");\n"
                //-- Fileuploader
                + "@import url(\"" + getSharedRootPath() + "lib/ext-2.0.2/ux/fileuploader/css/icons.css\");\n"
                + "@import url(\"" + getSharedRootPath() + "lib/ext-2.0.2/ux/fileuploader/css/webpage.css\");\n"
                + "@import url(\"" + getSharedRootPath() + "lib/ext-2.0.2/ux/fileuploader/css/filetree.css\");\n"
                + "@import url(\"" + getSharedRootPath() + "lib/ext-2.0.2/ux/fileuploader/css/filetype.css\");\n"
                + "@import url(\"" + getSharedRootPath() + "lib/ext-2.0.2/ux/fileuploader/css/famflag.css\");\n"
                + "@import url(\"" + getSharedRootPath() + "lib/ext-2.0.2/ux/fileuploader/css/Ext.ux.IconCombo.css\");\n"
                + "@import url(\"" + getSharedRootPath() + "lib/ext-2.0.2/ux/fileuploader/css/empty.css\");\n"
                + "@import url(\"" + getSharedRootPath() + "lib/ext-2.0.2/ux/fileuploader/img/extjs.ico\");\n"
                //-- Portal
                + "@import url(\"" + getRootPath(request) + "lib/ext-2.0.2/ux/portal/css/portal.css\");\n"
                // Rowactions extension
                + "@import url(\"" + getRootPath(request) + "lib/ext-2.0.2/ux/rowactions/css/Ext.ux.grid.RowActions.css\");\n"
                // VrTabPanel extension
                + "@import url(\"" + getRootPath(request) + "lib/ext-2.0.2/ux/verticaltab/css/ux_VerticalTabPanel.css\");\n"
                + "</style>\n"

                + "<!-- oryx editor -->\n"
                // EN_US is default an base language
                + "<!-- language files -->\n"
                + "<script src=\"" + getSharedRootPath() + "oryx/i18n/translation_en_us.js\" type=\"text/javascript\" />\n"
                + languageFiles
                // Handle different profiles
                //+ "<script src=\"" + oryx_path + "profiles/oryx.core.uncompressed.js\" type=\"text/javascript\" />\n"
                + generateUncompressedJSFileList(request)
                + profileFiles
                + headExtentions

                + "<link rel=\"Stylesheet\" media=\"screen\" href=\"" + getSharedRootPath() + "oryx/css/theme_norm.css\" type=\"text/css\" />\n"

                + "<!-- erdf schemas -->\n"
                + "<link rel=\"schema.dc\" href=\"http://purl.org/dc/elements/1.1/\" />\n"
                + "<link rel=\"schema.dcTerms\" href=\"http://purl.org/dc/terms/\" />\n"
                + "<link rel=\"schema.b3mn\" href=\"http://b3mn.org\" />\n"
                + "<link rel=\"schema.oryx\" href=\"http://oryx-editor.org/\" />\n"
                + "<link rel=\"schema.raziel\" href=\"http://raziel.org/\" />\n"

                + content

                + "</head>\n"

                + "<body style=\"overflow:hidden;\"><div class='processdata' style='display:none'>\n"

                + "\n"
                + "</div>\n"

                + analytics

                + "</body>\n"
                + "</html>";
    }

    protected InputStream getResourceAsStream(String path) {
        return this.getServletContext().
                getResourceAsStream(path);
    }

    protected String getRootPath(HttpServletRequest request) {
        return "";//getSharedRootPath() + SnapUtil.determineSnapContextPath(request);
    }

    protected String getSharedRootPath() {
        return "/etl/";
    }

    protected String getOryxRootDirectory() {
        String realPath = this.getServletContext().getRealPath("");
        File backendDir = new File(realPath);
        return backendDir.getParent();
    }
    protected String getCountryCode(HttpServletRequest req) {
        return (String) req.getSession().getAttribute("countrycode");
    }
    protected String getLanguageCode(HttpServletRequest req) {
        return (String) req.getSession().getAttribute("languagecode");
    }
    protected String getRelativeServerPath(HttpServletRequest req){
        return "/backend/poem"; //+ req.getServletPath();
    }
    public Collection<String> getAvailableProfileNames() {
        Collection<String> profilNames = new ArrayList<String>();

        File handlerDir=null;
        try {
            handlerDir = new File(this.getServletContext().
                    getRealPath("/profiles"));
        } catch (NullPointerException e) {
            return profilNames;
        }
        if(handlerDir==null)
            return profilNames;

        String[] children = handlerDir.list();
        for (File source : handlerDir.listFiles()) {
            if (source.getName().endsWith(".js")) {
                profilNames.add(source.getName().substring(0, source.getName().lastIndexOf(".")));
            }
        }
        return profilNames;
    }

    private String generateUncompressedJSFileList(HttpServletRequest request) {

        return "<script src=\"" + getRootPath(request) + "scripts/utils.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/kickstart.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/erdfparser.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/datamanager.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/clazz.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/config.js\" type=\"text/javascript\" />\n"
                //{{FF/ETL
                + "<script src=\"" + getRootPath(request) + "lib/ext-2.0.2/ux/etl/js/ETLUIExtensions.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/ffdi.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/ETL/repository/docreponavigation.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/ETL/repository/etlreponavigation.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/ETL/editor.js\" type=\"text/javascript\" />\n"
                //}}
                + "<script src=\"" + getRootPath(request) + "scripts/Core/SVG/editpathhandler.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/Core/SVG/minmaxpathhandler.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/Core/SVG/pointspathhandler.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/Core/SVG/svgmarker.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/Core/SVG/svgshape.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/Core/SVG/label.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/Core/Math/math.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/Core/StencilSet/stencil.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/Core/StencilSet/property.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/Core/StencilSet/propertyitem.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/Core/StencilSet/complexpropertyitem.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/Core/StencilSet/rules.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/Core/StencilSet/stencilset.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/Core/StencilSet/stencilsets.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/Core/command.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/Core/bounds.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/Core/uiobject.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/Core/abstractshape.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/Core/canvas.js\" type=\"text/javascript\" />\n"
                /*+ "<script src=\"" + getRootPath(request) + "scripts/Core/apiHandler.js\" type=\"text/javascript\" />\n"*/
                + "<script src=\"" + getRootPath(request) + "scripts/Core/svgDrag.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/Core/shape.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/Core/Controls/control.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/Core/Controls/docker.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/Core/Controls/magnet.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/Core/node.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/Core/edge.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/Core/abstractPlugin.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/Core/abstractLayouter.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/Core/Event/eventmanager.js\" type=\"text/javascript\" />\n"

                //{{
                // Plugin-dependent FF/ETL
                //}}
                + "<script src=\"" + getRootPath(request) + "scripts/ETL/presenter/datapresenter.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/ETL/pluginmanager.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/ETL/baseetlpropertywindow.js\" type=\"text/javascript\" />\n"
                + "<script src=\"" + getRootPath(request) + "scripts/ETL/wizard/basewizardeditor.js\" type=\"text/javascript\" />\n";

    }
}
