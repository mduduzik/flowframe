package org.flowframe.bpm.jbpm.bpmserver.core;

/**
 * Initializes singleton session so all classes that somehow rely on session being active
 * should extend this class. For instance <code>ProcessManagement</code> and <code>TaskManagement</code>. 
 */
public class SessionInitializer {

    public SessionInitializer() {
        StatefulKnowledgeSessionUtil.getStatefulKnowledgeSession();
    }
}
