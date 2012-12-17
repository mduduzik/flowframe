/*
 * Copyright 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.conx.logistics.kernel.bpm.impl.jbpm.core;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.drools.SystemEventListenerFactory;
import org.jbpm.task.TaskService;
import org.jbpm.task.service.SyncTaskServiceWrapper;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.local.LocalTaskService;
import org.jbpm.task.service.mina.MinaTaskClientConnector;
import org.jbpm.task.service.mina.MinaTaskClientHandler;

public class TaskClientFactory {
    
    public static final String DEFAULT_TASK_SERVICE_STRATEGY = "Mina";
    public static final String DEFAULT_IP_ADDRESS = "127.0.0.1";
    public static final int DEFAULT_PORT = 9123;

    /**
    * Produces new instance of TaskClient based on given properties and assignes as connector identifier given connectorId.
    * <br/>
    * Main property that drives type of a client (mina, hornetq, jms) is <code>jbpm.console.task.service.strategy</code>. If not given
    * it will apply <code>DEFAULT_TASK_SERVICE_STRATEGY</code> which is mina.<br/>
    *
    * Other transport specific properties must be given as part of the properties.
    * <br/>
    * Common properties are:
    * <ul>
    * <li><code>jbpm.console.task.service.host</code></li>
    * <li><code>jbpm.console.task.service.port</code></li>
    * </ul>
    * @param properties properties required to create task client
    * @param connectorId identifier that will be assigned to the connector
    * @return new instance of task client that is already connected
    * @throws IllegalArgumentException in case unknown type of a task client is given as <code>jbpm.console.task.service.strategy</code> or connection to the task server failed.
    */
        public static TaskService newInstance(Properties properties, String connectorId) {
            TaskService service = null;
            String strategy = properties.getProperty("jbpm.console.task.service.strategy", DEFAULT_TASK_SERVICE_STRATEGY);
            if ("Local".equalsIgnoreCase(strategy)) {
                //org.jbpm.task.service.TaskService taskService = HumanTaskService.getService();
                //service = new LocalTaskService(taskService);

                
            } else {
                service = new SyncTaskServiceWrapper(newAsyncInstance(properties, connectorId));
            }
            
            return service;
        }
        
        public static TaskClient newAsyncInstance(Properties properties, String connectorId) {
            TaskClient client = null;
            String defaultPort = null;
            String strategy = properties.getProperty("jbpm.console.task.service.strategy", DEFAULT_TASK_SERVICE_STRATEGY);
            if ("Mina".equalsIgnoreCase(strategy)) {
                if (client == null) {
                    client = new TaskClient(new MinaTaskClientConnector(connectorId,
                                            new MinaTaskClientHandler(SystemEventListenerFactory.getSystemEventListener())));
                    defaultPort = "9123";
                }
            }
            else {
                throw new IllegalArgumentException("Unknown TaskClient type was specified: " + strategy);
            }
            
            String host = properties.getProperty("jbpm.console.task.service.host", DEFAULT_IP_ADDRESS);
            String port = properties.getProperty("jbpm.console.task.service.port", defaultPort);
            boolean connected = client.connect(host, Integer.parseInt(port));
            if (!connected) {
                throw new IllegalArgumentException("Could not connect task client " + strategy + "(" + host + ":" + port + ")");
            }
            
            return client;
        }
}
