package org.flowframe.bpm.jbpm.ui.pageflow.services.impl.path;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.definition.process.Node;
import org.flowframe.bpm.jbpm.services.IBPMService;
import org.flowframe.ui.pageflow.services.IPageFlowPage;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.workflow.core.node.HumanTaskNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageFlowPathAssessor {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private String name;
	private List<Node> nodePath;
	private Map<String, List<Node>> possibleNextPaths;

	private List<IPageFlowPage> currentOrderedPageList;

	private Map<String, IPageFlowPage> pageCache;
	private IPageFlowPage currentPage;
	private long currentTaskId;
	private boolean pagesChanged;

	private String currentTaskName;

	private IBPMService bpmService;

	private String processInstanceId;

	public PageFlowPathAssessor() {
	}

	public PageFlowPathAssessor(String processInstanceId, IBPMService bpmService, String name, List<Node> nodePath, Map<String, List<Node>> possibleNextPaths, Map<String, IPageFlowPage> pageCache) {
		super();
		this.processInstanceId = processInstanceId;
		this.bpmService = bpmService;
		this.name = name;
		this.nodePath = nodePath;
		this.possibleNextPaths = possibleNextPaths;
		
		if (pageCache == null) {
			this.pageCache = new HashMap<String, IPageFlowPage>();
		} else {
			this.pageCache = pageCache;
		}

		createOrderedPageList();// Use nodeList for TS
		updateCurrentPageInfo(null);
	}

	public boolean restActivePages(TaskSummary ts) {
		this.currentTaskId = ts.getId();
		this.currentTaskName = ts.getName();
		String name_ = null;
		List<Node> nodePath_ = null;

		try {
			// Find next path
			for (String pathKey : possibleNextPaths.keySet()) {
				if (pathKey.startsWith(name + "-->" + ts.getName()))// Partial
																	// path
				{
					name_ = pathKey;
					nodePath_ = possibleNextPaths.get(pathKey);
					break;
				} else if (pathKey.equalsIgnoreCase(name) && pathKey.endsWith("End"))// Complete
																						// path
				{
					name_ = pathKey;
					nodePath_ = possibleNextPaths.get(pathKey);
					break;
				}
			}

			if (name != name_)
				this.pagesChanged = true;
			else
				this.pagesChanged = false;

			name = name_;
			nodePath = nodePath_;

			if (this.pagesChanged) {
				createOrderedPageList();
			}

			updateCurrentPageInfo(ts.getName());
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
			throw new RuntimeException("restActivePages() failed.", e);
		}

		return this.pagesChanged;
	}

	public boolean isOnLastPage() {
		return this.name.endsWith(this.currentTaskName + "-->End") || this.name.endsWith(this.currentTaskName + "-->Join-->End");
	}

	private void createOrderedPageList() {
		// Create currentOrderedPageList
		this.currentOrderedPageList = new ArrayList<IPageFlowPage>();
		for (Node node : nodePath) {
			if (node instanceof HumanTaskNode) {
				this.currentOrderedPageList.add(pageCache.get(node.getName()));
			}
		}
	}

	private void updateCurrentPageInfo(String currentTaskName) {
		// Create currentOrderedPageList
		this.currentOrderedPageList = new ArrayList<IPageFlowPage>();
		String firstTaskName = null;
		for (Node node : nodePath) {
			if (node instanceof HumanTaskNode) {
				this.currentOrderedPageList.add(pageCache.get(node.getName()));
				if (firstTaskName == null) {
					firstTaskName = node.getName();
				}
				if (currentTaskName == null) {
					TaskSummary ts = this.bpmService.getTaskSummaryByNameAndInstanceId(firstTaskName, Long.valueOf(processInstanceId));
					this.currentTaskId = ts.getId();// ((HumanTaskNode)node).getId();
					this.currentTaskName = firstTaskName;
					currentPage = pageCache.get(node.getName());
				}
				if (currentTaskName != null && firstTaskName != null) {
					if (currentTaskName.equalsIgnoreCase(node.getName())) {
						currentPage = pageCache.get(currentTaskName);
					}
				}
			}
		}
	}

	public String getName() {
		return name;
	}

	public List<Node> getNodePath() {
		return nodePath;
	}

	public Map<String, List<Node>> getPossibleNextPaths() {
		return possibleNextPaths;
	}

	public List<IPageFlowPage> getCurrentOrderedPageList() {
		return currentOrderedPageList;
	}

	public Map<String, IPageFlowPage> getPageCache() {
		return pageCache;
	}

	public boolean isPagesChanged() {
		return pagesChanged;
	}

	public IPageFlowPage getCurrentPage() {
		return currentPage;
	}

	public long getCurrentTaskId() {
		return currentTaskId;
	}
}
